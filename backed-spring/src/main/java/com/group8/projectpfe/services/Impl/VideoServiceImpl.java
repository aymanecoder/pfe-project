package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.domain.dto.VideoDto;
import com.group8.projectpfe.entities.Video;
import com.group8.projectpfe.exception.ResourceNotFound;
import com.group8.projectpfe.mappers.impl.VideoMapperImpl;
import com.group8.projectpfe.repositories.VideoRepository;
import com.group8.projectpfe.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    private final VideoMapperImpl videoMapper;
    @Override
    public VideoDto createVideo(VideoDto videoDto) {
        String titre = videoDto.getTitre();
        if (titre == null || titre.isEmpty()) {
            throw new ResourceNotFound(false, "Video title cannot be null or empty");
        }

        try {
            Video video = videoMapper.mapFrom(videoDto);
            Video savedVideo = videoRepository.save(video);
            return videoMapper.mapTo(savedVideo);
        } catch (Exception e) {
            // Log the exception details for debugging purposes
            e.printStackTrace();
            throw new ResourceNotFound(false, "Something went wrong while creating the video");
        }
    }
    @Override
    public VideoDto getVideoById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Video ID must not be null");
        }

        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound(false, "Video not found with id: " + id));

        return videoMapper.mapTo(video);
    }

    @Override
    public VideoDto updateVideo(Integer id, VideoDto videoDto) {
        if (videoDto.getTitre().isEmpty()) {
            throw new ResourceNotFound(false, "Video title can not be null");
        }

        try {
            Video existingVideo = videoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFound(false, "Video not found with id: " + id));

            existingVideo.setTitre(videoDto.getTitre());
            existingVideo.setDescription(videoDto.getDescription());

            Video updatedVideo = videoRepository.save(existingVideo);
            return videoMapper.mapTo(updatedVideo);
        } catch (Exception e) {
            throw new ResourceNotFound(false, "Something went wrong while updating the video");
        }
    }
    @Override
    public List<VideoDto> getAllVideos() {
        List<Video> videos = videoRepository.findAll();

        return videos.stream()
                .map(videoMapper::mapTo)
                .collect(Collectors.toList());
    }
    @Override
    public void deleteVideo(Integer id) {
        if (!videoRepository.existsById(id)) {
            throw new ResourceNotFound(false, "Video not found with id: " + id);
        }
        videoRepository.deleteById(id);
    }
}
