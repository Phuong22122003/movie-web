package com.web.movie.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Favorite")
@Data
public class Favorite {
    @Id
    @GeneratedValue(strategy =GenerationType.UUID)
    private String id;
    @Column(name = "USER_ID")
    private String userId;
    @ManyToOne
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
}
