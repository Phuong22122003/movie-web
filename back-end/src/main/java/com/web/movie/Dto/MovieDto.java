package com.web.movie.Dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.web.movie.Dto.response.UserDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieDto implements Serializable {
    private int id;
    private String image_url;
    private String name;
    private String description;
    private String video_url;
    private List<GenreDto> genres;
    private UserDto user;
    private CountryDto country;
    // @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime createdDate;
    private Integer viewCount;
}
