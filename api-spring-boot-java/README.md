# Todo App API (Spring Boot with Java)

> **Java Version:** 21

[Swagger UI](http://localhost:8080/api/swagger-ui/index.html)

## Environment Variables

| Name                         | Description                                              |
|------------------------------|----------------------------------------------------------|
| POSTGRES_DB_URL              | Postgres db url                                          |
| POSTGRES_DB_USER             | Postgres db username (Optional)                          |
| POSTGRES_DB_PASSWORD         | Postgres db password (Optional)                          |
| FIREBASE_PROJECT_ID          | Firebase project id                                      |
| CORS_ALLOWED_ORIGINS         | Cors allowed origins (Optional)                          |
| CORS_ALLOWED_ORIGIN_PATTERNS | Cors allowed origin patterns (Optional, default: *)      |
| CORS_ALLOWED_METHODS         | Cors allowed methods (Optional)                          |
| CORS_ALLOWED_HEADERS         | Cors allowed headers (Optional)                          |
| CORS_ALLOW_CREDENTIALS       | Whether allow Cors credentials (Optional, default: true) |
| S3_BUCKET_NAME               | S3 bucket name                                           |
| S3_REGION                    | S3 region (Optional, default: us-west-2)                 |
| S3_URL                       | S3 url                                                   |
| S3_ACCESS_KEY                | S3 access key                                            |
| S3_SECRET_KEY                | S3 secret key                                            |

## Error Codes

| Code | Description                  |
|------|------------------------------|
| 1    | "Account email not verified" |
| 20   | "User not found"             |
| 100  | "Task not found"             |

> **1 to 99** User related errors \
> **100 to 199** Task related errors
