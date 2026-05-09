# Netflix News System

A Spring Boot and MySQL management system for a Netflix-style content database. The app includes a static HTML/CSS/JavaScript frontend served by Spring Boot and REST APIs for authentication, content, schedules, contracts, feedback, accounts, and dashboard statistics.

## Features

- Customer and employee roles.
- JWT login, registration, and password change.
- Web series browsing, search, detail pages, and feedback.
- Employee CRUD for production houses, producers, contracts, schedules, countries, accounts, and users.
- ECharts dashboard visualizations.
- MyBatis mappers with prepared statements.

## Tech Stack

- Java 17
- Spring Boot 2.6.13
- Spring Security
- MyBatis
- MySQL 8
- Maven
- Static HTML/CSS/JavaScript frontend

## Local Setup

```bash
cp .env.example .env
mysql -u root -p < database/netflix_news.sql
mvn spring-boot:run
```

The app runs at `http://localhost:8080`.

Default local configuration:

| Variable | Default |
| --- | --- |
| `PORT` | `8080` |
| `DB_URL` | `jdbc:mysql://localhost:3306/netflix_news?...` |
| `DB_USERNAME` | `root` |
| `DB_PASSWORD` | `root` |
| `JWT_SECRET` | local development placeholder |
| `JWT_EXPIRATION_MS` | `86400000` |

## Build

```bash
mvn clean package -DskipTests
docker build -t netflix-news-system:local .
```

## Database

The SQL schema and seed data are in `database/netflix_news.sql`. The project report includes the ER model, DDL/DML, query examples, and screenshots.

## API Areas

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/auth/me`
- `GET /api/web-series`
- `GET /api/schedules`
- `GET /api/feedback`
- `GET /api/statistics/dashboard`
- `GET /api/production-houses`
- `GET /api/producers`
- `GET /api/contracts`
- `GET /api/accounts`

See [Postman collection](postman/Netflix News System.postman_collection.json) for request examples.

## Deployment Notes

Cloud Run can host the Spring Boot container, but this project needs MySQL. For Google Cloud, use Cloud SQL MySQL or another managed MySQL instance and pass `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, and `JWT_SECRET` as Cloud Run environment variables or secrets.

Low-cost portfolio mode should reuse one shared Cloud SQL instance for all database-backed projects. A dedicated Cloud SQL instance per project will be much more expensive.

## Documentation

- [Runtime configuration](docs/configuration.md)
- [Project report](docs/Project_Report.md)
- [Record count queries](docs/get_record_counts.sql)
