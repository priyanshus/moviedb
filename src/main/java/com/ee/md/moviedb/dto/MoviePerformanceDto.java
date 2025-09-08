package com.ee.md.moviedb.dto;

import lombok.Getter;

@Getter
public class MoviePerformanceDto {
    private Long movieId;
    private String title;
    private double averageRating;
    private int reviewCount;
    private String classification;

    public MoviePerformanceDto() {
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}
