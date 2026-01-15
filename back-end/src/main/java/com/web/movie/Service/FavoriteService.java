package com.web.movie.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.web.movie.CustomException.NotFoundException;
import com.web.movie.Dto.response.FavoriteResponseDto;
import com.web.movie.Entity.Favorite;
import com.web.movie.Entity.User;
import com.web.movie.Repository.FavoriteRepository;
import com.web.movie.Repository.MovieRepository;
import com.web.movie.Repository.UserRepository;
import com.web.movie.mapper.FavoritedMapper;

@Service
public class FavoriteService {
    @Autowired private FavoriteRepository favoriteRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private MovieRepository movieRepository;
    @Autowired private FavoritedMapper favoritedMapper;
    public List<FavoriteResponseDto> getFavorites(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
        List<Favorite> favorites = favoriteRepository.findByUserId(user.getId());
        return favoritedMapper.toFavoriteResponseDtos(favorites);
    }
    public FavoriteResponseDto addFavorite(Integer movieId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
        Favorite favorite = new Favorite();
        favorite.setUserId(user.getId());
        favorite.setMovie(movieRepository.findById(movieId).orElseThrow(() -> new NotFoundException("Movie not found")));
        favoriteRepository.save(favorite);
        return favoritedMapper.toFavoriteResponseDto(favorite);
    }
    public void removeFavorite(String favoriteId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
        Favorite favorite = favoriteRepository.findById(favoriteId).orElseThrow(() -> new NotFoundException("Favorite not found"));
        if(!favorite.getUserId().equals(user.getId())){
            throw new NotFoundException("Favorite not found for this user");
        }
        favoriteRepository.delete(favorite);
    }
}
