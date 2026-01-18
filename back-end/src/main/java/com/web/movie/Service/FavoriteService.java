package com.web.movie.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.web.movie.Config.CustomUserDetails;
import com.web.movie.Config.JwtTokenProvider;
import com.web.movie.CustomException.NotFoundException;
import com.web.movie.Dto.response.FavoriteResponseDto;
import com.web.movie.Entity.Favorite;
import com.web.movie.Repository.FavoriteRepository;
import com.web.movie.Repository.MovieRepository;
import com.web.movie.mapper.FavoritedMapper;

@Service
public class FavoriteService {
    @Autowired private FavoriteRepository favoriteRepository;
    @Autowired private MovieRepository movieRepository;
    @Autowired private FavoritedMapper favoritedMapper;
    @Autowired private JwtTokenProvider jwtTokenProvider;
    @Value("${app.url}")private String baseResource;

    public List<FavoriteResponseDto> getFavorites(){
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Favorite> favorites = favoriteRepository.findByUserId(user.getId());
        var favoriteResponseDtos = favoritedMapper.toFavoriteResponseDtos(favorites);
        for(int i = 0; i < favoriteResponseDtos.size(); i++){
            var movie = favoriteResponseDtos.get(i).getMovie();
            movie.setImage_url(baseResource + "/resource/images/" + favorites.get(i).getMovie().getImageFileName());
            movie.setVideo_url(baseResource + "/resource/videos/" + favorites.get(i).getMovie().getVideoFileName() + "?token=" + jwtTokenProvider.generateTokenForResourceAccess());
        }
        return favoriteResponseDtos;
    }
    public FavoriteResponseDto addFavorite(Integer movieId){
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Favorite favorite = new Favorite();
        favorite.setCreatedDate(LocalDateTime.now());
        favorite.setUserId(user.getId());
        favorite.setMovie(movieRepository.findById(movieId).orElseThrow(() -> new NotFoundException("Movie not found")));
        favoriteRepository.save(favorite);
        return favoritedMapper.toFavoriteResponseDto(favorite);
    }
    public void removeFavorite(String favoriteId){
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Favorite favorite = favoriteRepository.findById(favoriteId).orElseThrow(() -> new NotFoundException("Favorite not found"));
        if(!favorite.getUserId().equals(user.getId())){
            throw new NotFoundException("Favorite not found for this user");
        }
        favoriteRepository.delete(favorite);
    }
}
