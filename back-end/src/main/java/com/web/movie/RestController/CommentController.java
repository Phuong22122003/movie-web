package com.web.movie.RestController;

import java.util.List;

import com.web.movie.Dto.response.CommentResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.movie.Dto.request.CommentRequestDto;
import com.web.movie.Service.CommentService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/comments")
@Tag(name = "CommentController")
public class CommentController {
    private CommentService commentService;
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }
    @GetMapping("/{movie_id}")
    public ResponseEntity<List<CommentResponseDto> >getCommentsOfMovie(@PathVariable(name = "movie_id") int movieId){
        return ResponseEntity.ok().body(commentService.getCommentsOfMovie(movieId));
    }
    @PostMapping("")
    public ResponseEntity<?> comment(@RequestBody CommentRequestDto comment){
        CommentResponseDto res = commentService.addComment(comment);
        return ResponseEntity.ok().body(res);
    }
}
