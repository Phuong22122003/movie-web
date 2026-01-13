package com.web.movie.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import com.web.movie.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.MediaType;
@RestController
@RequestMapping("/api/v1/resource")
@Tag(name = "ResourceController")
public class ResourceRestController {
    @Autowired
    FileService fileService;
    @Value("${file.upload-dir}")
    private String uploadDir;
    @GetMapping("/image/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename, HttpRequest request) throws Exception {

        Resource resource = fileService.getResource(filename, request);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .contentLength(resource.contentLength())
                .body(resource);

    }
    @GetMapping("/video/{filename}")
    public ResponseEntity<Resource> getVideo(@PathVariable String filename, HttpRequest request) throws IOException {
            Resource resource = fileService.getResource(filename, request);
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf("video/mp4"))
                    .contentLength(resource.contentLength())
                    .body(resource);

    }
}
