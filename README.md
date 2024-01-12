### 개발 환경으로 실행
- IntelliJ IDEA 설치(IntelliJ IDEA Community Edition)
  - https://www.jetbrains.com/idea/download

- ServerApplication.kt 이동 후 해당 스타트 버튼 클릭
  > ![project run](./screenshots/project%20run.png)

- 터미널에서 실행
  > $ ./gradlew bootrun


### Postgresql

**설치 확인**
![postsqlVersion.png](screenshots%2FpostsqlVersion.png)
- Virsion = 14.10

**서비스 시작**
> $ brew services start postgresql
> 
> ![postgreStart.png](screenshots%2FpostgreStart.png)

**PostgreSQL 콘솔 접속**

> $ psql postgres
> 
> ![postgreConsole.png](screenshots%2FpostgreConsole.png)

**DB 계정과 권환, Role 확인**
> postgres=# \du
> 
> ![postgreDB.png](screenshots%2FpostgreDB.png)

###  JWT + Security 설정
- **JWT 관련**
    - TokenProvider: 유저 정보로 JWT 토큰을 만들거나 토큰을 바탕으로 유저 정보를 가져옴
    - JwtFilter: Spring Request 앞단에 붙일 Custom Filter
- **Spring Security 관련** 
  - JwtSecurityConfig: JWT Filter를 추가
  - JwtAccessDeniedHandler: 접근 권한 없을 때 403 에러
  - JwtAuthenticationEntryPoint: 인증 정보 없을 때 401 에러
  - SecurityConfig: 스프링 시큐리티에 필요한 설정
  - SecurityUtil: SecurityContext에서 전역으로 유저 정보를 제공하는 유틸 클래스
  - CorsConfig:  서로 다른 Server 환경에서 자원을 공유에 필요한 설정