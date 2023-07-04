package com.ticketing.testFixture;

import com.ticketing.domain.member.admin.dto.request.AdminCreateReq;
import com.ticketing.domain.member.admin.dto.request.AdminLoginReq;
import com.ticketing.domain.member.admin.entity.Admin;
import com.ticketing.domain.member.customer.dto.request.CustomerCreateReq;
import com.ticketing.domain.member.customer.dto.request.CustomerLoginReq;
import com.ticketing.domain.member.customer.entity.Customer;
import com.ticketing.domain.member.data.Email;
import com.ticketing.domain.member.data.MemberInfo;
import com.ticketing.domain.member.data.Role;
import com.ticketing.domain.member.token.dto.response.TokensRes;
import com.ticketing.domain.performance.dto.request.PerformanceCreateReq;
import com.ticketing.domain.performance.dto.response.PerformanceSimpleRes;
import com.ticketing.domain.performance.entity.Performance;
import com.ticketing.domain.performance.entity.Schedule;
import com.ticketing.domain.performance.entity.Seat;
import com.ticketing.domain.reservation.entity.Reservation;
import com.ticketing.domain.venue.dto.request.VenueCreateReq;
import com.ticketing.domain.venue.entity.Venue;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.util.ReflectionUtils;

public class TestFixture {

  private static final String name = "유저";
  private static final String email = "user1@gmail.com";
  private static final String customerEmail = "customer1@gmail.com";
  private static final String adminEmail = "admin1@gmail.com";
  private static final String password = "user1234!";
  private static final int birthYear = 2000;
  private static final String businessLicense = "L2-1112-1234";
  private static final Role roleCustomer = Role.ROLE_CUSTOMER;
  private static final Role rolePerformanceManager = Role.ROLE_PERFORMANCE_MANAGER;
  private static final Role roleVenueManager = Role.ROLE_VENUE_MANAGER;
  private static final LocalDate startDate = LocalDate.of(2023, 8, 1);
  private static final LocalTime startTime = LocalTime.of(12, 10);

  public static AdminCreateReq createPerformanceManagerCreateReq() {
    return new AdminCreateReq(
        name, email, password, birthYear, businessLicense, rolePerformanceManager
    );
  }

  public static AdminCreateReq createVenueManagerCreateReq() {
    return new AdminCreateReq(
        name, email, password, birthYear, businessLicense, roleVenueManager
    );
  }

  public static CustomerCreateReq createCustomerCreateReq() {
    return new CustomerCreateReq(
        name, email, password, birthYear
    );
  }

  public static VenueCreateReq createVenueCreateReq() {
    return new VenueCreateReq(
        "포은아트홀", "경기도 용인시 어쩌구 저쩌구", "0101112222", 100
    );
  }

  public static PerformanceCreateReq createPerformanceCreateReq(Long venueId) {
    return new PerformanceCreateReq(
        "맘마미아",
        "askdfaksfjewfjalskf",
        new Schedule(startDate, startTime),
        14,
        15000,
        venueId
    );
  }

  public static Performance createPerformance(Venue venue, Admin admin) {
    return new Performance(
        "맘마미아",
        "askdfaksfjewfjalskf",
        new Schedule(startDate, startTime),
        14,
        new Seat(15000, 100),
        venue,
        admin
    );
  }

  public static Venue createVenue(Admin admin) {
    Venue venue = new Venue(
        "포은아트홀",
        "경기도 용인시 어쩌구 저쩌구",
        "0101112222",
        100,
        admin
    );

    Field venueField = ReflectionUtils.findField(Venue.class, "id");
    venueField.setAccessible(true);
    ReflectionUtils.setField(venueField, venue, 1L);
    return venue;
  }

  public static Admin createPerformanceManager() {
    Admin admin = new Admin(
        new MemberInfo(
            name,
            new Email("admin1@gmail.com"),
            password,
            birthYear
        ),
        businessLicense,
        rolePerformanceManager
    );

    Field adminField = ReflectionUtils.findField(Admin.class, "id");
    adminField.setAccessible(true);
    ReflectionUtils.setField(adminField, admin, 1L);

    return admin;
  }

  public static Admin createVenueManager() {
    Admin admin = new Admin(
        new MemberInfo(
            name,
            new Email("admin2@gmail.com"),
            password,
            birthYear
        ),
        businessLicense,
        roleVenueManager
    );

    Field adminField = ReflectionUtils.findField(Admin.class, "id");
    adminField.setAccessible(true);
    ReflectionUtils.setField(adminField, admin, 1L);

    return admin;
  }

  public static Performance createPerformance(Admin admin, Venue venue) {
    return new Performance(
        "짱구",
        "asfdasfdafs",
        new Schedule(startDate, startTime),
        12,
        new Seat(5000, 150),
        venue,
        admin
    );
  }

  public static Performance createPerformanceWithId(Admin admin, Venue venue) {
    Performance performance = new Performance(
        "짱구",
        "asfdasfdafs",
        new Schedule(startDate, startTime),
        12,
        new Seat(5000, 150),
        venue,
        admin
    );

    Field field = ReflectionUtils.findField(Performance.class, "id");
    field.setAccessible(true);
    ReflectionUtils.setField(field, performance, 1L);

    return performance;
  }

  public static Performance createPerformance(PerformanceCreateReq createReq, Venue venue,
      Admin admin) {
    Performance performance = createReq.toEntity(venue, admin);
    Field field = ReflectionUtils.findField(Performance.class, "id");
    field.setAccessible(true);
    ReflectionUtils.setField(field, performance, 1L);
    return performance;
  }

  public static PerformanceSimpleRes createPerformanceSimpleRes() {
    return new PerformanceSimpleRes("짱구",
        "asfdasfdafs",
        startDate,
        startTime,
        100,
        "아트홀"
    );
  }

  public static Customer createCustomer() {
    Customer customer = new Customer(
        new MemberInfo(
            name,
            new Email(customerEmail),
            password,
            birthYear
        )
    );

    Field field = ReflectionUtils.findField(Customer.class, "id");
    field.setAccessible(true);
    ReflectionUtils.setField(field, customer, 1L);

    return customer;
  }

  public static CustomerLoginReq createCustomerLoginReq() {
    return new CustomerLoginReq(
        customerEmail,
        password
    );
  }

  public static TokensRes createTokenRes() {
    return new TokensRes(
        "rkWkdprtptmxhzms",
        "rkWkflvmfptnlxhzms"
    );
  }

  public static AdminLoginReq createAdminLoginReq() {
    return new AdminLoginReq(
        adminEmail,
        password
    );
  }

  public static Reservation createReservation(Performance performance, Customer customer,
      int ticketCount, int totalCost) {
    Reservation reservation = new Reservation(
        performance,
        customer,
        ticketCount,
        totalCost
    );

    Field field = ReflectionUtils.findField(Reservation.class, "id");
    field.setAccessible(true);
    ReflectionUtils.setField(field, reservation, 1L);
    return reservation;
  }
}
