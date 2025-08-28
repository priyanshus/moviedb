# MovieDB Service Features

## Overview
MovieDB is a Spring Boot REST API for managing movies, reviews, and starring actors, with secure authentication and authorization using JWT. It uses PostgreSQL as the database and Flyway for schema migrations.

## Features

### Authentication & Authorization
- **JWT-based authentication** for all APIs (except login/register)
- **Role-based access control**: Only ADMIN can add movies, any authenticated user can add reviews
- **User registration and login** endpoints


### Movie APIs
- `POST /api/v1/movies` (ADMIN only): Add a new movie. Required fields: `title`, `releaseDate`, `genres`, `cast`, `rating`, `synopsis`.
- `GET /api/v1/movies`: List all movies. Supports pagination (`page`, `size`) and sorting (`sort`). Each movie returns: `title`, `releaseDate`, `genres`, `cast`, `rating`, `synopsis`.
- `GET /api/v1/movies/{movieId}`: Get details of a movie (same fields as above).

### Review APIs
- `GET /api/v1/movies/{movieId}/reviews`: List all reviews for a movie
- `POST /api/v1/movies/{movieId}/reviews`: Add a review to a movie (any authenticated user)

### Starring APIs
- `GET /api/v1/movies/{movieId}/starring`: List all starring actors for a movie
- `POST /api/v1/movies/{movieId}/starring`: Add an actor to a movie (ADMIN only)

### User APIs
- `POST /api/v1/auth/register`: Register a new user
- `POST /api/v1/auth/login`: Login and receive a JWT token

### Database & Migrations
- **PostgreSQL** database
- **Flyway** for schema migrations

### Error Handling
- Global exception handler for consistent error responses

### API Documentation
- Swagger UI for interactive API docs

## How to Run
1. Start PostgreSQL with Docker Compose: `docker-compose up`
2. Configure your `application.properties` to match the DB credentials
3. Run the Spring Boot application
4. Access Swagger UI at `/swagger-ui.html` (after enabling Swagger)
