package com.ee.md.moviedb.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StarringDto {
    private Long id;
    private String actorName;
    private String role;
}
