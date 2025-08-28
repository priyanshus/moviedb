package com.ee.md.moviedb.model;

import jakarta.persistence.*;

@Entity
public class Starring {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String actorName;
    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getActorName() { return actorName; }
    public void setActorName(String actorName) { this.actorName = actorName; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }
}
