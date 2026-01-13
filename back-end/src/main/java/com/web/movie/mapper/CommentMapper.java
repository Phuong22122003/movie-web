package com.web.movie.mapper;

import java.util.List;

import com.web.movie.Dto.response.CommentResponseDto;
import org.mapstruct.Mapper;

import com.web.movie.Entity.Comment;

@Mapper(componentModel="spring")
public interface CommentMapper {
    public CommentResponseDto toCommentDto(Comment comment);
    public List<CommentResponseDto> toCommentDtos(List<Comment> comments);
}
