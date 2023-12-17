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
    public VideoDto createVideo(VideoDto videoDto) {
        if (videoDto.getTitre().isEmpty()) {
            throw new ResourceNotFound(false, "Video title can not be null");
        }

        try {
            Video video = videoMapper.mapFrom(videoDto);
            Video savedVideo = videoRepository.save(video);
            return videoMapper.mapTo(savedVideo);
        } catch (Exception e) {
            throw new ResourceNotFound(false, "Something went wrong while creating the video");
        }
    }

    public VideoDto getVideoById(Integer id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound(false, "Video not found with id: " + id));
        return videoMapper.mapTo(video);
    }

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

    public List<VideoDto> getAllVideos() {
        List<Video> videos = videoRepository.findAll();

        return videos.stream()
                .map(videoMapper::mapTo)
                .collect(Collectors.toList());
    }

    public void deleteVideo(Integer id) {
        if (!videoRepository.existsById(id)) {
            throw new ResourceNotFound(false, "Video not found with id: " + id);
        }
        videoRepository.deleteById(id);
    }
}
