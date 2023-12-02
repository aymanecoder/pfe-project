package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.VideoDto;
import com.group8.projectpfe.entities.VideoEntity;
import com.group8.projectpfe.mappers.Mapper;
import com.group8.projectpfe.repositories.VideoRepository;
import com.group8.projectpfe.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class VideoController {
    private final VideoService videoService;
    private final Mapper<VideoEntity,VideoDto> videoMapper;
    @GetMapping("hello")
    public String getMessage(){
        return "hello";
    }
    @PostMapping("videos")
    public ResponseEntity<VideoDto> createVide(@RequestBody VideoDto video){
        VideoEntity videoEntity = videoMapper.mapFrom(video);
        VideoEntity savedVideo = videoService.createVideo(videoEntity);
        return new ResponseEntity<>(videoMapper.mapTo(savedVideo), HttpStatus.CREATED);
    }


}
