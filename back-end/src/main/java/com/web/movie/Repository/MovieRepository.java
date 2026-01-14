package com.web.movie.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.web.movie.Entity.Movie;

import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;

public interface MovieRepository extends JpaRepository<Movie,Integer>{
    Page<Movie> findByCountryName(String countryId,Pageable pageable);

    @Query(value = """
            SELECT m.*
            FROM Movie m
            JOIN (
                SELECT MOVIE_ID
                FROM Movie_Genre
                WHERE GENRE_ID = :genreId
            ) mg ON m.ID = mg.MOVIE_ID ORDER BY m.ID
            OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY
            """,
            nativeQuery = true)
    List<Movie> findMovieByGenreId(int genreId, int offset, int limit);

    @Transactional
    @Modifying
    @Query(value = "DELETE Movie WHERE id = :id")
    int deleteMovieById(int id);


    @Query("SELECT m FROM Movie m WHERE m.name LIKE CONCAT('%', :keyword, '%') OR m.description LIKE CONCAT('%', :keyword, '%')")
    Page<Movie> searchMovies(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT TOP(:limit) * FROM Movie order by CREATED_DATE", nativeQuery = true)
    List<Movie> getCurrentMovie(int limit);

    @Modifying
    @Query(value = "UPDATE Movie SET VIEW_COUNT = VIEW_COUNT + 1 WHERE VIDEO_FILE_NAME = :fileName", nativeQuery = true)
    public void updateViewCount(String fileName);
}



