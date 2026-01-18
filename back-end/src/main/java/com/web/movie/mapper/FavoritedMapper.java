package com.web.movie.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.web.movie.Dto.MovieDto;
import com.web.movie.Dto.response.FavoriteResponseDto;
import com.web.movie.Entity.Favorite;
import com.web.movie.Entity.Movie;

@Mapper(componentModel="spring")
public interface FavoritedMapper {
    FavoriteResponseDto toFavoriteResponseDto(Favorite favorite);
    List<FavoriteResponseDto> toFavoriteResponseDtos(List<Favorite> favorites);
    
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "isFavorited", constant = "true")
    @Mapping(target = "image_url", ignore = true)
    @Mapping(target = "video_url", ignore = true)
    MovieDto movieToMovieDto(Movie movie);
}
