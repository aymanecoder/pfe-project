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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/video")
public class VideoController {
    private final VideoService videoService;
    private final FileService fileService;
    private final VideoRepository videoRepository;

    @Value("${project.video}")
    private String path;
    @Value("${server.port}")
    private String port;

    @PostMapping
    public ResponseEntity<VideoDto> createVideo(@RequestBody VideoDto videoDto) {
        VideoDto createdVideo = videoService.createVideo(videoDto);
        return new ResponseEntity<>(createdVideo, HttpStatus.CREATED);
    }

    @GetMapping("video/{id}")
    public ResponseEntity<VideoDto> getVideoById(@PathVariable Integer id) {
        VideoDto video = videoService.getVideoById(id);
        return new ResponseEntity<>(video, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoDto> updateVideo(@PathVariable Integer id, @RequestBody VideoDto videoDto) {
        VideoDto updatedVideo = videoService.updateVideo(id, videoDto);
        return new ResponseEntity<>(updatedVideo, HttpStatus.OK);
    }

    @GetMapping("videos")
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
    public VideoDto uploadVideo(@RequestParam("video") MultipartFile video,@PathVariable Integer id) throws IOException {
        VideoDto v = videoService.getVideoById(id);
        FileModel fileModel = fileService.uploadVideo(path,video);
        v.setVideoName(fileModel.getVideoFileName());
        VideoDto finalUpload = videoService.updateVideo(id, v);
        return finalUpload;
    }
    @GetMapping(value = "/play/{id}", produces = MediaType.ALL_VALUE)
    public void playVideo(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        Optional<Video> video = videoRepository.findById(id);

        if (video.isPresent()) {
            String videoName = video.get().getVideoName();
            try {
                InputStream resource = fileService.getVideoFile(path, videoName, id);
                response.setContentType(MediaType.ALL_VALUE);
                StreamUtils.copy(resource, response.getOutputStream());
            } catch (FileNotFoundException e) {
                // Handle file not found error
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.getWriter().write("Video file not found.");
            } catch (AccessDeniedException e) {
                // Handle access denied error
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write("Access to video file is denied.");
            } catch (IOException e) {
                // Handle other IO errors
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.getWriter().write("Error occurred while streaming video.");
            }
        } else {
            // Handle video not found error
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.getWriter().write("Video not found.");
        }
    }
    @PostMapping("/videos")
    public ResponseEntity<VideoDto> createVideo(@RequestParam("video") MultipartFile video,
                                                @ModelAttribute VideoDto videoDetails) {
        try {
            if (!video.isEmpty()) {
                // Upload the video file and get the file details
                FileModel fileModel = fileService.uploadVideo(path, video);
                String videoUrl = "http://localhost:" + port + "/api/v1/video/" + fileModel.getVideoFileName();

                // Set the video URL in the DTO
                videoDetails.setUrlVideo(videoUrl);

                // You may want to add logic to delete the old video file if needed
                // fileService.deleteOldVideo(videoDetails.getOldVideoPath());
            } else {
                // Handle case where no video file is provided
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // Create the video with video details
            VideoDto createdVideo = videoService.createVideo(videoDetails);

            // Return the created video DTO along with the HTTP status
            return new ResponseEntity<>(createdVideo, HttpStatus.CREATED);
        } catch (IOException e) {
            // Handle file processing error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
