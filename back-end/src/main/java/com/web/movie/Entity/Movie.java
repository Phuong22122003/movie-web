package com.web.movie.Entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Movie")
@Data
public class Movie {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME",nullable = false)
    private String name;

    @Column(name = "IMAGE_FILE_NAME",nullable = false)
    private String imageFileName;

    @Column(name = "LENGTH")
    private Integer length;

    @Column(name = "VIDEO_FILE_NAME",nullable = false)
    private String videoFileName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "VIEW_COUNT")
    private Integer viewCount;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToMany
    @JoinTable(
        name = "Movie_Genre",
        joinColumns = @JoinColumn(name="MOVIE_ID"),
        inverseJoinColumns = @JoinColumn(name="GENRE_ID")
    )
    private List<Genre> genres;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
}
