package com.ee.md.moviedb.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    private Long id;
    private String reviewer;
    private String comment;
    private int rating;
}
