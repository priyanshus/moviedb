package com.ee.md.moviedb.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class MovieDto {
    private Long id;
    private String title;
    private String synopsis;
    private String releaseDate;
    private List<String> genres;
    private int year;
    private List<ReviewDto> reviews;
    private List<StarringDto> starring;
}
