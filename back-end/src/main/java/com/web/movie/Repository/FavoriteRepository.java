package com.web.movie.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.movie.Entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, String> {
    public List<Favorite> findByUserId(String userId);

    @Query(value = "SELECT MOVIE_ID FROM Favorite WHERE USER_ID = :userId AND MOVIE_ID IN :movieIds", nativeQuery = true)
    public List<Integer> filterFavoritedMovieIds(String userId, List<Integer> movieIds);

    public boolean existsByUserIdAndMovieId(String userId, Integer movieId);
}
