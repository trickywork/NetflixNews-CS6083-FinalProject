# Runtime Configuration

## Environment Variables

| Name | Required | Default | Purpose |
| --- | --- | --- | --- |
| `PORT` | No | `8080` | HTTP port. Cloud Run injects this automatically. |
| `DB_URL` | Yes outside local MySQL | Local `netflix_news` JDBC URL | MySQL JDBC connection string. |
| `DB_USERNAME` | Yes | `root` | MySQL username. |
| `DB_PASSWORD` | Yes | `root` | MySQL password. Store as a secret in cloud deployments. |
| `JWT_SECRET` | Yes in production | local placeholder | JWT signing secret. Use a long random value in cloud deployments. |
| `JWT_EXPIRATION_MS` | No | `86400000` | JWT lifetime in milliseconds. |

## Local Database

```bash
mysql -u root -p < database/netflix_news.sql
mvn spring-boot:run
```

The schema creates the `netflix_news` database and seed data. If local MySQL uses another account, update `.env` or export the variables before running.

## Google Cloud Notes

For a live deployment, create or reuse a Cloud SQL MySQL instance, import `database/netflix_news.sql`, then configure Cloud Run with:

```bash
gcloud run services update netflix-news \
  --region us-central1 \
  --set-env-vars DB_URL='jdbc:mysql:///<DB_NAME>?cloudSqlInstance=<INSTANCE_CONNECTION_NAME>&socketFactory=com.google.cloud.sql.mysql.SocketFactory',DB_USERNAME='<USER>' \
  --set-secrets DB_PASSWORD=netflix-db-password:latest,JWT_SECRET=netflix-jwt-secret:latest
```

The current `pom.xml` does not include the Cloud SQL socket factory dependency. If using the socket factory URL above, add the dependency first. For the lowest-cost first deployment, connecting to a single shared MySQL instance by private/public IP is more straightforward.
