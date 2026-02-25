# StudySpringBoot
StudySpringBoot - SpringBoot 학습 내용 정리 공간 
이 프로젝트는 Spring Boot를 활용한 RESTful API 서비스의 기본 구조와 테스트 주도 개발(TDD) 환경 구축을 목표로 합니다.

## 📑 목차

1. **[프로젝트 생성]()**
2. **[Docker 기반 데이터베이스 환경 구축]()**
3. **[Spring 계층 구조와 의존성 주입]()**
4. **[테스트 코드 작성 (TDD)]()**
5. **[DB 연결 설정 (VSCode)]()**

---

## 1. 프로젝트 생성

1. `VSCode` 실행 후 `Ctrl+Shift+P` → `Spring Initializr: Create a Maven Project` 선택
2. **Spring Boot Version**: 최신 정식 버전 선택
3. **Project ID**: `com.demo`, **Artifact ID**: `helloes`
4. **Dependencies (필수 4종)**:
* `Lombok`: `@Data`, `@RequiredArgsConstructor` 사용을 위해 필요
* `Spring Data JPA`: 데이터베이스 조작을 위한 표준 인터페이스
* `Rest Repositories`: 저장소를 기반으로 REST API를 자동 생성
* `HyperSQL Database` (또는 MySQL/PostgreSQL): 데이터 저장소



---

## 2. Docker 기반 데이터베이스 환경 구축

운영 환경과 유사한 DB를 사용하기 위해 Docker 컨테이너를 실행합니다.
1. `docker Desktop` 실행
2. 터미널에서 Docker 실행 

### **MySQL (9.5.0)**

```bash
# 이미지 다운로드
docker pull mysql:9.5.0

# 컨테이너 실행
# --name: 컨테이너 이름
# -p: 포트 매핑 (호스트:컨테이너)
# -e: 환경변수 설정
# -v: 볼륨 마운트 (호스트 경로:컨테이너 경로) (-v 부분은 제외해도 된다.)
# -d: 백그라운드 실행 
# 대부분 접속 되지만 접속이 안되는 경우 [allowPublicKeyRetrieval] 옵션을 true로 설정
docker run --name mydata -p 3306:3306 -e MYSQL_ROOT_PASSWORD=docker123 -v C:/Users/USER/Documents/dockerdata/mysqldata:/var/lib/mysql -d mysql:9.5.0

```

### **PostgreSQL**



```bash
# 이미지 다운로드
docker pull postgres:latest

# 컨테이너 실행
# --name: 컨테이너 이름
# -p: 포트 매핑 (호스트:컨테이너)
# -e: 환경변수 설정
# -v: 볼륨 마운트 (호스트 경로:컨테이너 경로)  (-v 부분은 제외해도 된다.)
# -d: 백그라운드 실행   
# 대부분 접속 되지만 접속이 안되는 경우 [allowPublicKeyRetrieval] 옵션을 true로 설정
docker run --name postgres-db -p 5432:5432 -e POSTGRES_PASSWORD=1234 -e POSTGRES_DB=rest -v C:/Users/USER/Documents/dockerdata/postgres:/var/lib/postgresql/data -d postgres:latest

```

**MSSQL**
```bash
# 이미지 다운로드
docker pull mcr.microsoft.com/mssql/server:2025-latest

# 컨테이너 실행 (기본 설정)
docker run --name mssql2025 -p 1433:1433 -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=Test1234!" -v C:/Users/USER/Documents/dockerdata/mssql:/var/opt/mssql/data -d mcr.microsoft.com/mssql/server:2022-latest
```

**MariaDb**
```bash
# 이미지 다운로드 
docker pull mariadb:noble

# 컨테이너 실행
docker run --name maria -p 3306:3306 -v C:/Users/USER/Documents/dockerdata/maria:/var/lib/mysql:Z -e MARIADB_ROOT_PASSWORD=Test1234! -d mariadb:latest
```
3. 터미널에서 Spring Boot 애플리케이션 실행
```bash
# test 폴더에서는 '>' 화살표 클릭과 동일함.
.\mvnw spring-boot:run
```
---
(+ 번외 추가)
## 3. Spring 계층 구조와 의존성 주입

### **3.1 계층 구조의 역할**

* **`@Data` (Domain)**: 메시지(`message`)와 이메일(`email`) 정보를 담는 객체입니다.
* **`@Repository`**: 실제 DB에 접근하는 인터페이스입니다.
* **`@Service`**: 비즈니스 로직을 수행하며 레포지토리를 호출합니다.

### **3.2 의존성 주입 (Dependency Injection)**

* **`@Autowired`**: 스프링이 관리하는 객체(Bean)를 자동으로 연결해 줍니다.
* **최신 권장 방식 (생성자 주입)**:
* 실무에서는 필드 주입보다 **생성자 주입**을 권장합니다.
* `final` 필드와 Lombok의 `@RequiredArgsConstructor`를 조합하여 코드의 안정성을 높입니다.



---
(+번외)
## 4. 테스트 코드 작성 (TDD)

테스트 코드에서는 편의상 `@Autowired`를 사용하여 필요한 빈을 주입받습니다.

### **Repository 및 웹 요청 테스트 예시**

```java
@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

    @Autowired
    private HelloRepository helloRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void CRUD_테스트() throws Exception {
        // 1. 저장(Create)
        Hello hello = new Hello();
        hello.setMessage("유비샘");
        helloRepository.save(hello);

        // 2. MockMvc를 이용한 웹 요청 테스트
        mockMvc.perform(post("/helloes")
               .content(hello)) // 요청 Body
               .andDo(print())   // 결과 출력
               .andExpect(is2xx()); // 200번대 응답 기대
    }
}

```

---

## 5. DB 연결 설정 (VSCode)

VSCode의 **Database 확장**을 사용하여 Docker 컨테이너의 DB에 접속합니다.

| DB 종류 | JDBC URL | Dialect | Driver Path | Username | Password |
| --- | --- | --- | --- |
| **MySQL** | `jdbc:mysql://127.0.0.1:3306`|  `MySQL` | `{homedir}/.dbclient/drivers/mysql-connector-java-8.0.28.jar` | `root` | `Docker Container`에서 지정한 비밀번호` |
| **PostgreSQL** | `jdbc:postgresql://127.0.0.1:5432/rest` | `PostgresSQL` | `{homedir}/.dbclient/drivers/postgresql-42.6.0.jar`| `postgres` | `Docker Container`에서 지정한 비밀번호` |
| **MariaDB** | `jdbc:mariadb://localhost:3306/rest` | `선택안해도 됨.` | `c:\Users\USER\.m2\repository\org\mariadb\jdbc\mariadb-java-client\3.5.7\mariadb-java-client-3.5.7.jar`| `root` | `Docker Container`에서 지정한 비밀번호` |
| **MSSQL** | `jdbc:sqlserver://127.0.0.1:1433;databasename=master`| `SQL Server` | `{homedir}/.dbclient/drivers/sqljdbc_12.8.1.0_enu.zip` | `지정한 username` |`Docker Container`에서 지정한 비밀번호` |

---

*MySQL에서 *JDBC 설정 버튼 클릭 시 속성 추가에  allowPublicKeyRetrieval  옵션에 대한 값으로 true 설정해주기 ( 접속이 안되었을 때)
