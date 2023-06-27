# numble-ticketing-service
## 프로젝트 설명 
예매 서비스 구현 (인터파크 클론코딩) 
## ERD
![ERD](https://github.com/yewon9609/ticketing-service/assets/99070762/596d7f79-4337-4128-9d04-dfe971ca0a1b)
## api명세서
[👉🏻 api 명세서](https://ubiquitous-puppet-358.notion.site/API-91638449e6f6475faedd8be5fb109b5e?pvs=4)
## 유저 스토리 
[👉🏻 유저 스토리 & MosCow](https://ubiquitous-puppet-358.notion.site/MosCow-b3c619ef53f340cdb8366b213d6a6074?pvs=4)
## 프로젝트 실행 
1. Docker Compose 파일을 실행해주세요 
2. [👉🏻 application.yml](https://ubiquitous-puppet-358.notion.site/application-yml-e509bfa89594479b9ae5d6cc62fe1eee?pvs=4) 을 추가해주세요
## 고민했던 포인트
1. 예매 동시성 처리

   -> 대용량 트래픽처리를 가정, 분산 DB를 고려하여 분산락으로 해결 
    추후에 트랜잭션 유지 시간 설정 추가할 예정

   -> 예약 대기 순번을 메세지 큐를 활용해 순차적으로 처리할 수 있는 방법이 있지않을까? 싶은데 잘 모르겠음.. 
3. 조회 속도 향상

   -> 캐싱 처리 및 인덱스 처리 할 계획
5. 고객 entity 설계

   -> customer와 admin의 성격이 다르다고 생각해 테이블을 분리하는게 맞다고 판단. 같은 필드는 Embedded로 묶어 재사용 함

   -> customer와 admin이 분리되다 보니 securityConfig 분리를 깨끗하게 하질 못함 (중복코드가 너무 많음)
## 이부분은 고려해주세요 
- 테스트 코드를 작성하지 못했습니다🥲
  추후에 시간을 들여 작성할 예정입니다
