package com.ee.md.moviedb.service;

import com.ee.md.moviedb.dto.ReviewDto;
import com.ee.md.moviedb.model.Movie;
import com.ee.md.moviedb.model.Review;
import com.ee.md.moviedb.repository.MovieRepository;
import com.ee.md.moviedb.repository.ReviewRepository;
import com.ee.md.moviedb.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceImplTest {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private MovieServiceImpl movieService;
    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testAddReviewToMovie_InvalidRating() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(new Movie()));
        ReviewDto dto = new ReviewDto();
        dto.setReviewer("user");
        dto.setRating(6); // Invalid
        assertThrows(IllegalArgumentException.class, () -> movieService.addReviewToMovie(1L, dto));
    }
}
