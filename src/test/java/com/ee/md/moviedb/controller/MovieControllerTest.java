package com.ee.md.moviedb.controller;

import com.ee.md.moviedb.dto.MovieDto;
import com.ee.md.moviedb.dto.ReviewDto;
import com.ee.md.moviedb.dto.StarringDto;
import com.ee.md.moviedb.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieControllerTest {
    @Mock
    private MovieService movieService;
    @InjectMocks
    private MovieController movieController;
    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }


    @Test
    void testGetAllMovies() {
        MovieDto dto = new MovieDto();
        dto.setTitle("Inception");
        dto.setSynopsis("A mind-bending thriller");
        dto.setReleaseDate("2010-07-16");
        dto.setGenres(List.of("Sci-Fi", "Thriller"));
        dto.setYear(2010);
        Page<MovieDto> page = new PageImpl<>(List.of(dto));
        when(movieService.getAllMovies(any(PageRequest.class))).thenReturn(page);
        Page<MovieDto> result = movieController.getAllMovies(PageRequest.of(0, 10));
        assertEquals(1, result.getTotalElements());
        MovieDto movie = result.getContent().get(0);
        assertEquals("Inception", movie.getTitle());
        assertEquals("A mind-bending thriller", movie.getSynopsis());
        assertEquals("2010-07-16", movie.getReleaseDate());
        assertEquals(List.of("Sci-Fi", "Thriller"), movie.getGenres());
        assertEquals(2010, movie.getYear());
    }

    @Test
    void testCreateMovie() {
        MovieDto dto = new MovieDto();
        dto.setTitle("Interstellar");
        when(movieService.createMovie(dto)).thenReturn(dto);
        MovieDto result = movieController.createMovie(dto);
        assertEquals("Interstellar", result.getTitle());
        verify(movieService, times(1)).createMovie(dto);
    }

    @Test
    void testGetReviews() {
        when(movieService.getReviewsByMovieId(1L)).thenReturn(Collections.emptyList());
        assertTrue(movieController.getReviews(1L).isEmpty());
        verify(movieService, times(1)).getReviewsByMovieId(1L);
    }

    @Test
    void testAddReview() {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewer("user");
        when(movieService.addReviewToMovie(1L, reviewDto)).thenReturn(reviewDto);
        ReviewDto result = movieController.addReview(1L, reviewDto);
        assertEquals("user", result.getReviewer());
        verify(movieService, times(1)).addReviewToMovie(1L, reviewDto);
    }

    @Test
    void testGetStarring() {
        when(movieService.getStarringByMovieId(1L)).thenReturn(Collections.emptyList());
        assertTrue(movieController.getStarring(1L).isEmpty());
        verify(movieService, times(1)).getStarringByMovieId(1L);
    }

    @Test
    void testAddStarring() {
        StarringDto starringDto = new StarringDto();
        starringDto.setActorName("Actor");
        when(movieService.addStarringToMovie(1L, starringDto)).thenReturn(starringDto);
        StarringDto result = movieController.addStarring(1L, starringDto);
        assertEquals("Actor", result.getActorName());
        verify(movieService, times(1)).addStarringToMovie(1L, starringDto);
    }

    @Test
    void testGetMovieById_Found() {
        MovieDto dto = new MovieDto();
        dto.setId(1L);
        dto.setTitle("Inception");
        when(movieService.getMovieById(1L)).thenReturn(dto);
        ResponseEntity<MovieDto> response = movieController.getMovieById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Inception", response.getBody().getTitle());
    }

    @Test
    void testGetMovieById_NotFound() {
        when(movieService.getMovieById(2L)).thenReturn(null);
        ResponseEntity<MovieDto> response = movieController.getMovieById(2L);
        assertEquals(404, response.getStatusCodeValue());
    }
}
