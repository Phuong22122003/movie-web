package com.web.movie.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.movie.Service.FavoriteService;

@RestController
@RequestMapping("/api/v1/favorites")
public class FavoriteController {
    @Autowired private FavoriteService favoriteService;
    @RequestMapping("")
    public ResponseEntity<?> getFavorites(){
        return ResponseEntity.ok(favoriteService.getFavorites());
    }
    @PostMapping("/{movie_id}")
    public ResponseEntity<?> addFavorite(@PathVariable(name = "movie_id") Integer movieId){
        return ResponseEntity.ok(favoriteService.addFavorite(movieId));
    }
    @DeleteMapping("/{favorite_id}")
    public ResponseEntity<String> removeFavorite(@PathVariable(name = "favorite_id") String favoriteId){
        favoriteService.removeFavorite(favoriteId);
        return ResponseEntity.ok("Remove successfully");
    }
}
