package com.ee.md.moviedb.service;

import com.ee.md.moviedb.dto.MovieDto;
import com.ee.md.moviedb.model.Movie;
import com.ee.md.moviedb.repository.MovieRepository;
import com.ee.md.moviedb.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.Optional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceImplTest {
    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private MovieServiceImpl movieService;
    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }


    @Test
    void testGetMovieById_ReturnsMovie() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");
        movie.setSynopsis("A mind-bending thriller");
        movie.setReleaseDate("2010-07-16");
        movie.setGenres(List.of("Sci-Fi", "Thriller"));
        movie.setYear(2010);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        MovieDto dto = movieService.getMovieById(1L);
        assertNotNull(dto);
        assertEquals("Inception", dto.getTitle());
        assertEquals("A mind-bending thriller", dto.getSynopsis());
        assertEquals("2010-07-16", dto.getReleaseDate());
        assertEquals(List.of("Sci-Fi", "Thriller"), dto.getGenres());
        assertEquals(2010, dto.getYear());
    }

    @Test
    void testGetMovieById_NotFound() {
        when(movieRepository.findById(2L)).thenReturn(Optional.empty());
        MovieDto dto = movieService.getMovieById(2L);
        assertNull(dto);
    }


    @Test
    void testGetAllMovies_Empty() {
        Page<Movie> page = new PageImpl<>(Collections.emptyList());
        when(movieRepository.findAll(any(PageRequest.class))).thenReturn(page);
        Page<MovieDto> result = movieService.getAllMovies(PageRequest.of(0, 10));
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllMovies_WithData() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");
        movie.setSynopsis("A mind-bending thriller");
        movie.setReleaseDate("2010-07-16");
        movie.setGenres(List.of("Sci-Fi", "Thriller"));
        movie.setYear(2010);
        Page<Movie> page = new PageImpl<>(List.of(movie));
        when(movieRepository.findAll(any(PageRequest.class))).thenReturn(page);
        Page<MovieDto> result = movieService.getAllMovies(PageRequest.of(0, 10));
        assertEquals(1, result.getTotalElements());
        MovieDto dto = result.getContent().get(0);
        assertEquals("Inception", dto.getTitle());
        assertEquals("A mind-bending thriller", dto.getSynopsis());
        assertEquals("2010-07-16", dto.getReleaseDate());
        assertEquals(List.of("Sci-Fi", "Thriller"), dto.getGenres());
        assertEquals(2010, dto.getYear());
    }

    @Test
    void testCreateMovie_Success() {
        MovieDto dto = new MovieDto();
        dto.setTitle("Tenet");
        dto.setSynopsis("Time inversion thriller");
        dto.setReleaseDate("2020-08-26");
        dto.setGenres(List.of("Action", "Sci-Fi"));
        dto.setYear(2020);
        when(movieRepository.findAll()).thenReturn(Collections.emptyList());
        Movie saved = new Movie();
        saved.setId(2L);
        saved.setTitle("Tenet");
        saved.setSynopsis("Time inversion thriller");
        saved.setReleaseDate("2020-08-26");
        saved.setGenres(List.of("Action", "Sci-Fi"));
        saved.setYear(2020);
        when(movieRepository.save(any(Movie.class))).thenReturn(saved);
        MovieDto result = movieService.createMovie(dto);
        assertEquals("Tenet", result.getTitle());
        assertEquals("Time inversion thriller", result.getSynopsis());
        assertEquals("2020-08-26", result.getReleaseDate());
        assertEquals(List.of("Action", "Sci-Fi"), result.getGenres());
        assertEquals(2020, result.getYear());
    }
}
