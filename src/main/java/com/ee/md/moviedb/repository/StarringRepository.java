package com.ee.md.moviedb.repository;

import com.ee.md.moviedb.model.Starring;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StarringRepository extends JpaRepository<Starring, Long> {
    List<Starring> findByMovieId(Long movieId);
}
