package com.ticketing.infra.redis.aop;

import com.ticketing.domain.aop.annotation.DistributedLock;
import com.ticketing.infra.redis.config.CustomSpringELParser;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionTimedOutException;

@Aspect
@Component
public class RedissonLockAop {

  private static final String REDISSON_LOCK_PREFIX = "LOCK:";
  private static final Logger log = LoggerFactory.getLogger(RedissonLockAop.class);

  private final RedissonClient redissonClient;
  private final AopForTransaction aopForTransaction;

  public RedissonLockAop(RedissonClient redissonClient, AopForTransaction aopForTransaction) {
    this.redissonClient = redissonClient;
    this.aopForTransaction = aopForTransaction;
  }

  @Around("@annotation(com.ticketing.domain.aop.annotation.DistributedLock)")
  public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

    String key =
        REDISSON_LOCK_PREFIX + CustomSpringELParser.getDynamicValue(signature.getParameterNames(),
            joinPoint.getArgs(), distributedLock.key());

    RLock rLock = redissonClient.getLock(key);

    try {
      boolean available = rLock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(),
          distributedLock.timeUnit());
      if (!available) {
        return false;
      }

      return aopForTransaction.proceed(joinPoint);
    } catch (InterruptedException |TransactionTimedOutException e) {
      throw e;
    } finally {
      try {
        rLock.unlock();
      } catch (IllegalMonitorStateException e) {
        log.info("이미 락이 해제 된 상태입니다");
      }
    }
  }

}
