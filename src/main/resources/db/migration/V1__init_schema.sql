-- Flyway migration: initial schema
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    roles VARCHAR(255)[] NOT NULL
);

CREATE TABLE IF NOT EXISTS movie (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    synopsis TEXT,
    release_date VARCHAR(20),
    genres TEXT[],
    year INT NOT NULL
);

CREATE TABLE IF NOT EXISTS review (
    id SERIAL PRIMARY KEY,
    reviewer VARCHAR(100) NOT NULL,
    comment TEXT,
    rating INT NOT NULL,
    movie_id INT NOT NULL REFERENCES movie(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS starring (
    id SERIAL PRIMARY KEY,
    actor_name VARCHAR(255) NOT NULL,
    role VARCHAR(255),
    movie_id INT NOT NULL REFERENCES movie(id) ON DELETE CASCADE
);
