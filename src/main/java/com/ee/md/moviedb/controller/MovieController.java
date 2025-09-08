package com.ee.md.moviedb.controller;

import com.ee.md.moviedb.dto.*;
import com.ee.md.moviedb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping
    public Page<MovieDto> getAllMovies(
            @PageableDefault(size = 10, sort = "title", direction = Sort.Direction.ASC) Pageable pageable) {
        return movieService.getAllMovies(pageable);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long movieId) {
        MovieDto movie = movieService.getMovieById(movieId);
        return movie != null ? ResponseEntity.ok(movie) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public MovieDto createMovie(@RequestBody MovieDto movieDto) {
        return movieService.createMovie(movieDto);
    }

    @GetMapping("/{movieId}/reviews")
    public List<ReviewDto> getReviews(@PathVariable Long movieId) {
        return movieService.getReviewsByMovieId(movieId);
    }

    @PostMapping("/{movieId}/reviews")
    public ReviewDto addReview(@PathVariable Long movieId, @RequestBody ReviewDto reviewDto) {
        return movieService.addReviewToMovie(movieId, reviewDto);
    }

    @GetMapping("/{movieId}/starring")
    public List<StarringDto> getStarring(@PathVariable Long movieId) {
        return movieService.getStarringByMovieId(movieId);
    }

    @GetMapping("/{movieId}/performance")
    public ResponseEntity<MoviePerformanceDto> getMoviePerformance(@PathVariable Long movieId) {
        try {
            MoviePerformanceDto performance = movieService.getMoviePerformance(movieId);
            return ResponseEntity.ok(performance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{movieId}/starring")
    public StarringDto addStarring(@PathVariable Long movieId, @RequestBody StarringDto starringDto) {
        return movieService.addStarringToMovie(movieId, starringDto);
    }
}
