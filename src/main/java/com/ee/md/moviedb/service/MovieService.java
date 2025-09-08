package com.ee.md.moviedb.service;

import com.ee.md.moviedb.dto.MovieDto;
import com.ee.md.moviedb.dto.MoviePerformanceDto;
import com.ee.md.moviedb.dto.ReviewDto;
import com.ee.md.moviedb.dto.StarringDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    Page<MovieDto> getAllMovies(Pageable pageable);
    MovieDto getMovieById(Long id);
    MovieDto createMovie(MovieDto movieDto);
    List<ReviewDto> getReviewsByMovieId(Long movieId);
    ReviewDto addReviewToMovie(Long movieId, ReviewDto reviewDto);
    List<StarringDto> getStarringByMovieId(Long movieId);
    StarringDto addStarringToMovie(Long movieId, StarringDto starringDto);
    MoviePerformanceDto getMoviePerformance(Long movieId);
}
