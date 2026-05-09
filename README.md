# Netflix News System

[![CI](https://github.com/trickywork/netflix-news-system/actions/workflows/ci.yml/badge.svg)](https://github.com/trickywork/netflix-news-system/actions/workflows/ci.yml)

Netflix News System is a Spring Boot and MySQL management system for a Netflix-style content database. It includes a static HTML/CSS/JavaScript frontend served by Spring Boot and REST APIs for authentication, web series, schedules, contracts, feedback, accounts, and dashboard statistics.

## Current Status

- GitHub repo: `https://github.com/trickywork/netflix-news-system`
- Cloud Run service: not deployed yet
- Database requirement: MySQL

This project is ready for local development. A cloud deployment should be added after choosing the MySQL hosting option, ideally a shared low-cost Cloud SQL instance if several database-backed projects need deployment.

## Tech Stack

- Java 17
- Spring Boot 2.6
- Spring Web
- Spring Security
- JWT authentication
- MyBatis
- MySQL 8
- Druid connection pool
- Maven
- Static HTML/CSS/JavaScript frontend
- ECharts dashboard visualizations
- Docker-ready Spring Boot app
- API testing via local Postman workspace

## Project Structure

```text
netflix-news-system/
  src/main/java/
    controller/
    service/
    mapper/
    entity/
    config/
  src/main/resources/
    application.yml
    static/
  database/
    netflix_news.sql
  docs/
    configuration.md
    Project_Report.md
    get_record_counts.sql
  pom.xml
  Dockerfile
```

## Features

- User registration and login.
- JWT-based authentication.
- Customer and employee role separation.
- Web series browsing, search, and detail pages.
- Feedback creation and management.
- Schedule management.
- Production house, producer, and contract management.
- Account and user management.
- Dashboard statistics with charts.
- MyBatis mappers and SQL-backed persistence.

## Local Development

Create a local env file:

```bash
cd netflix-news-system
cp .env.example .env
```

Create the MySQL database from the checked-in schema:

```bash
mysql -u root -p < database/netflix_news.sql
```

Run the app:

```bash
mvn spring-boot:run
```

Expected local URL:

```text
http://localhost:8080
```

Expected result:

- The Spring Boot app starts successfully.
- Static pages load from the browser.
- Login/register APIs can read and write MySQL data.
- Dashboard and management pages load seeded database records.

## Environment Variables

Defaults from `application.yml`:

```env
PORT=8080
DB_URL=jdbc:mysql://localhost:3306/netflix_news?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
DB_USERNAME=root
DB_PASSWORD=root
JWT_SECRET=local-development-placeholder
JWT_EXPIRATION_MS=86400000
```

For local development, update `.env` or shell variables to match your MySQL username/password.

Do not commit real database passwords or JWT secrets.

## Database

Schema and seed data:

```text
database/netflix_news.sql
```

Useful validation queries:

```text
docs/get_record_counts.sql
```

The project report contains the ER model, DDL/DML discussion, query examples, and screenshots:

```text
docs/Project_Report.md
```

## API Areas

| Area | Example Paths |
| --- | --- |
| Auth | `POST /api/auth/register`, `POST /api/auth/login`, `GET /api/auth/me` |
| Web series | `GET /api/web-series` |
| Schedules | `GET /api/schedules` |
| Feedback | `GET /api/feedback` |
| Statistics | `GET /api/statistics/dashboard` |
| Production houses | `GET /api/production-houses` |
| Producers | `GET /api/producers` |
| Contracts | `GET /api/contracts` |
| Accounts | `GET /api/accounts` |

## Postman

Use the local Postman workspace collection:

```text
Netflix News System
```

Suggested variables:

```text
baseUrl=http://localhost:8080
token=
```

Use the login request first, then set the JWT token for protected endpoints.

The exported backup copy is kept in a private local archive outside this public repo.

## Tests And Build

```bash
mvn test
mvn clean package -DskipTests
```

Build a local Docker image:

```bash
docker build -t netflix-news-system:local .
```

## Cloud Deployment Plan

Cloud Run can host the Spring Boot container, but the app needs MySQL. Recommended low-cost setup:

1. Create or reuse one shared Cloud SQL MySQL instance.
2. Create a `netflix_news` database.
3. Import `database/netflix_news.sql`.
4. Store DB password and JWT secret in Secret Manager.
5. Deploy the Docker image to Cloud Run with Cloud SQL connection enabled.

Required Cloud Run variables:

```env
DB_URL=jdbc:mysql:///<database>?cloudSqlInstance=<project:region:instance>&socketFactory=com.google.cloud.sql.mysql.SocketFactory
DB_USERNAME=...
DB_PASSWORD=...
JWT_SECRET=...
JWT_EXPIRATION_MS=86400000
```

Cost warning:

- Cloud Run can scale to zero.
- Cloud SQL usually does not scale to zero.
- A dedicated Cloud SQL instance for one small portfolio app can cost more than the app container itself.
- If multiple projects need SQL, reuse one shared small instance and separate databases.

## Expected Portfolio Behavior

After database setup, a visitor or evaluator should be able to open the app, register/login, browse web series data, submit or view feedback, and use employee/admin management pages to inspect schedules, producers, production houses, contracts, accounts, and dashboard charts.

## Additional Notes

More setup details are in:

- `docs/configuration.md`
- `docs/Project_Report.md`
- `docs/get_record_counts.sql`
