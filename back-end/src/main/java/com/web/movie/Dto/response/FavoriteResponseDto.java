package com.web.movie.Dto.response;

import com.web.movie.Dto.MovieDto;

import lombok.Data;

@Data
public class FavoriteResponseDto {
    private String id;
    private MovieDto movie;
}
