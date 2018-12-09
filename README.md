# 회의실 예약 서버 

----
## 빌드 및 실행

1. SQL Server 2017 Express 설치 
2. SQL 서버 접속 계정 필요(application.properites 그대로 사용시에는 localhost:1433/user/1111)
3. reservation database 생성 
4. SQL 폴더 안의 *.sql 파일들 table SQL파일 부터 실행
5. JDK 8 or 그 이상의 버전 설치
6. git clone 후 폴더 안의 빌드하고실행.bat 실행 혹은 빌드하지 않고 실행.bat 실행
7. http://localhost:8080 접속 


----
## 해결방법 
### 예약 조회 
예약 조회 부분에서는 먼저 방 목록을 조회하고 
ReservationStatusService.java 에서 방목록을 조회 후
방목록에 따라서 각 회의실의 이름 별로 조회를 하게됩니다. 





### 예약
예약 에서는 다른 날짜와 겹치지 않는 부분을 신경써서 개발하게 되었습니다 날짜가 다른 날짜의 사이에 존재하게 된다면 예약을 할 수 없는 방식으로 동작합니다 
해당 내용은 

*SQL/confirm\_availability\_reservation.sql*

에서 확인 가능합니다 

실제로 예약 데이터를 넣는부분은 

*SQL/request\_reservation.sql*

에서 repeat 에 따라 반복하여 넣습니다 



### 공통

함수 구성에 있어서는 SQL 이나 클래스 내부적으로 하나로 합치는거나 하는 방향으로 가능하지만 테스트 및 중간 확인을 위해 절차를 분리했습니다. 조회 부분은 구현 시 java 클래스 위주로 예약 부분은 sql 쿼리 위주로 동작을 구성했습니다.   



