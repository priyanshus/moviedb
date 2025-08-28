package com.ee.md.moviedb.service.impl;

import com.ee.md.moviedb.dto.*;
import com.ee.md.moviedb.model.*;
import com.ee.md.moviedb.repository.*;
import com.ee.md.moviedb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private StarringRepository starringRepository;

    @Override
    public Page<MovieDto> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    public MovieDto getMovieById(Long id) {
        return movieRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public MovieDto createMovie(MovieDto movieDto) {
        // Prevent duplicate movies by title and year
        List<Movie> existing = movieRepository.findAll().stream()
            .filter(m -> m.getTitle().equalsIgnoreCase(movieDto.getTitle()) && m.getYear() == movieDto.getYear())
            .collect(Collectors.toList());
        if (!existing.isEmpty()) {
            throw new IllegalArgumentException("Movie with same title and year already exists");
        }
        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setSynopsis(movieDto.getSynopsis());
        movie.setReleaseDate(movieDto.getReleaseDate());
        movie.setGenres(movieDto.getGenres());
        movie.setYear(movieDto.getYear());
        return toDto(movieRepository.save(movie));
    }

    @Override
    public List<ReviewDto> getReviewsByMovieId(Long movieId) {
        return reviewRepository.findByMovieId(movieId).stream().map(this::toReviewDto).collect(Collectors.toList());
    }

    @Override
    public ReviewDto addReviewToMovie(Long movieId, ReviewDto reviewDto) {
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        // Validate rating (1-5)
        if (reviewDto.getRating() < 1 || reviewDto.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        // Prevent same reviewer from reviewing the same movie twice
        boolean alreadyReviewed = reviewRepository.findByMovieId(movieId).stream()
            .anyMatch(r -> r.getReviewer().equalsIgnoreCase(reviewDto.getReviewer()));
        if (alreadyReviewed) {
            throw new IllegalArgumentException("Reviewer has already reviewed this movie");
        }
        Review review = new Review();
        review.setReviewer(reviewDto.getReviewer());
        review.setComment(reviewDto.getComment());
        review.setRating(reviewDto.getRating());
        review.setMovie(movie);
        Review saved = reviewRepository.save(review);
        // Optionally, update movie's average rating (if you add such a field)
        return toReviewDto(saved);
    }

    @Override
    public List<StarringDto> getStarringByMovieId(Long movieId) {
        return starringRepository.findByMovieId(movieId).stream().map(this::toStarringDto).collect(Collectors.toList());
    }

    @Override
    public StarringDto addStarringToMovie(Long movieId, StarringDto starringDto) {
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        // Prevent duplicate actor for the same movie
        boolean alreadyStarring = starringRepository.findByMovieId(movieId).stream()
            .anyMatch(s -> s.getActorName().equalsIgnoreCase(starringDto.getActorName()));
        if (alreadyStarring) {
            throw new IllegalArgumentException("Actor already starring in this movie");
        }
        Starring starring = new Starring();
        starring.setActorName(starringDto.getActorName());
        starring.setRole(starringDto.getRole());
        starring.setMovie(movie);
        return toStarringDto(starringRepository.save(starring));
    }

    // Mapping methods
    private MovieDto toDto(Movie movie) {
        MovieDto dto = new MovieDto();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setYear(movie.getYear());
        if (movie.getReviews() != null)
            dto.setReviews(movie.getReviews().stream().map(this::toReviewDto).collect(Collectors.toList()));
        if (movie.getStarring() != null)
            dto.setStarring(movie.getStarring().stream().map(this::toStarringDto).collect(Collectors.toList()));
        return dto;
    }
    private ReviewDto toReviewDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setReviewer(review.getReviewer());
        dto.setComment(review.getComment());
        dto.setRating(review.getRating());
        return dto;
    }
    private StarringDto toStarringDto(Starring starring) {
        StarringDto dto = new StarringDto();
        dto.setId(starring.getId());
        dto.setActorName(starring.getActorName());
        dto.setRole(starring.getRole());
        return dto;
    }
}
