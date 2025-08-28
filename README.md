# MovieDB API

A Spring Boot REST API for managing movies and reviews.

## Quick Start

1. Start PostgreSQL using Docker:
```bash
docker compose up -d
```

2. Run the application:
```bash
./gradlew bootRun
```

## API Documentation

Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Key Endpoints

### Auth
- `POST /api/v1/auth/register` - Register user
- `POST /api/v1/auth/login` - Get JWT token

### Movies
- `GET /api/v1/movies` - List movies
- `POST /api/v1/movies` - Create movie (Admin)
- `GET /api/v1/movies/{id}` - Get movie
- `GET /api/v1/movies/{id}/reviews` - Get reviews
- `POST /api/v1/movies/{id}/reviews` - Add review

Protected endpoints require JWT token:
```
Authorization: Bearer <token>
```
