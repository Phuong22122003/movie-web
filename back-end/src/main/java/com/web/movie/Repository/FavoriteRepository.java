package com.web.movie.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.movie.Entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, String> {
    public List<Favorite> findByUserId(String userId);

    @Query("SELECT ID FROM Favorite WHERE USER_ID = : userId AND ID IN :ids")
    public List<String> filterFavoritedIds(String userId, List<String> ids);

    public boolean existsByUserIdAndMovieId(String userId, String movieId);
}
