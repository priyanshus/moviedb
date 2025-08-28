package com.ee.md.moviedb.service;

import com.ee.md.moviedb.dto.StarringDto;
import com.ee.md.moviedb.model.Movie;
import com.ee.md.moviedb.model.Starring;
import com.ee.md.moviedb.repository.MovieRepository;
import com.ee.md.moviedb.repository.StarringRepository;
import com.ee.md.moviedb.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StarringServiceImplTest {
    @Mock
    private StarringRepository starringRepository;
    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private MovieServiceImpl movieService;
    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testAddStarringToMovie_DuplicateActor() {
        Movie movie = new Movie();
        movie.setId(1L);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        StarringDto dto = new StarringDto();
        dto.setActorName("Actor");
        when(starringRepository.findByMovieId(1L)).thenReturn(java.util.List.of(new Starring() {{ setActorName("Actor"); }}));
        assertThrows(IllegalArgumentException.class, () -> movieService.addStarringToMovie(1L, dto));
    }

    @Test
    void testAddStarringToMovie_Success() {
        Movie movie = new Movie();
        movie.setId(2L);
        StarringDto dto = new StarringDto();
        dto.setActorName("New Actor");
        dto.setRole("Lead");
        when(movieRepository.findById(2L)).thenReturn(Optional.of(movie));
        when(starringRepository.findByMovieId(2L)).thenReturn(java.util.Collections.emptyList());
        Starring saved = new Starring();
        saved.setId(10L);
        saved.setActorName("New Actor");
        saved.setRole("Lead");
        when(starringRepository.save(any(Starring.class))).thenReturn(saved);
        StarringDto result = movieService.addStarringToMovie(2L, dto);
        assertEquals("New Actor", result.getActorName());
        assertEquals("Lead", result.getRole());
        verify(starringRepository, times(1)).save(any(Starring.class));
    }

    @Test
    void testGetStarringByMovieId() {
        Starring starring = new Starring();
        starring.setId(1L);
        starring.setActorName("Actor");
        starring.setRole("Role");
        when(starringRepository.findByMovieId(3L)).thenReturn(java.util.List.of(starring));
        assertEquals(1, movieService.getStarringByMovieId(3L).size());
        verify(starringRepository, times(1)).findByMovieId(3L);
    }

    @Test
    void testAddStarringToMovie_MovieNotFound() {
        when(movieRepository.findById(99L)).thenReturn(Optional.empty());
        StarringDto dto = new StarringDto();
        dto.setActorName("Someone");
        assertThrows(java.util.NoSuchElementException.class, () -> movieService.addStarringToMovie(99L, dto));
    }
}
