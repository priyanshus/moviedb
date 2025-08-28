package com.ee.md.moviedb.repository;

import com.ee.md.moviedb.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
