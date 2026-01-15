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
    @Mapping(target = "image_url", expression = "java(buildImageUrl(movie.getImageFileName()))")
    @Mapping(target = "video_url", expression = "java(buildVideoUrl(movie.getVideoFileName()))")
    MovieDto movieToMovieDto(Movie movie);

    @Named("imageUrlBuilder")
    default String buildImageUrl(String imageFileName) {
        return "http://localhost:80/api/v1/resource/image/" + imageFileName;
    }

    @Named("videoUrlBuilder")
    default String buildVideoUrl(String videoFileName) {
        return "http://localhost:80/api/v1/resource/video/" + videoFileName;
    }
}
