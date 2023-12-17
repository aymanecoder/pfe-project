package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.FileModel;
import com.group8.projectpfe.domain.dto.VideoDto;
import com.group8.projectpfe.entities.Video;
import com.group8.projectpfe.mappers.Mapper;
import com.group8.projectpfe.repositories.VideoRepository;
import com.group8.projectpfe.services.FileService;
import com.group8.projectpfe.services.VideoService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class VideoController {
    private final VideoService videoService;
    private final FileService fileService;
    @Value("${project.video}")
    private String path;
    private VideoRepository videoRepository;
    @PostMapping
    public ResponseEntity<VideoDto> createVideo(@RequestBody VideoDto videoDto) {
        VideoDto createdVideo = videoService.createVideo(videoDto);
        return new ResponseEntity<>(createdVideo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoDto> getVideoById(@PathVariable Integer id) {
        VideoDto video = videoService.getVideoById(id);
        return new ResponseEntity<>(video, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoDto> updateVideo(@PathVariable Integer id, @RequestBody VideoDto videoDto) {
        VideoDto updatedVideo = videoService.updateVideo(id, videoDto);
        return new ResponseEntity<>(updatedVideo, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<VideoDto>> getAllVideos() {
        List<VideoDto> videos = videoService.getAllVideos();
        return new ResponseEntity<>(videos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Integer id) {
        videoService.deleteVideo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/post/{id}")
    public VideoDto uploadVideo(@RequestParam("video") MultipartFile video,Integer id) throws IOException {
        VideoDto v = videoService.getVideoById(id);
        FileModel fileModel = fileService.uploadVideo(path,video);
        v.setVideoName(fileModel.getVideoFileName());
        VideoDto finalUpload = videoService.updateVideo(id, v);
        return finalUpload;
    }
    @GetMapping(value = "/play/{id}", produces = MediaType.ALL_VALUE)
    public void playVideo(@PathVariable Integer id, HttpServletResponse response) throws IOException {
       Optional<Video> video = videoRepository.findById(id);
       InputStream resource = fileService.getVideoFile(path,video.get().getVideoName(),id);
       response.setContentType(MediaType.ALL_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }


}
