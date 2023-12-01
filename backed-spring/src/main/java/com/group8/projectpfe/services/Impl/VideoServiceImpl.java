package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.entities.VideoEntity;
import com.group8.projectpfe.repositories.VideoRepository;
import com.group8.projectpfe.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    @Override
    public VideoEntity createVideo(VideoEntity videoEntity) {
        return videoRepository.save(videoEntity);
    }

    @Override
    public VideoEntity getVideoById(Integer id) {
        return videoRepository.findById(id).orElseThrow();
    }

    @Override
    public VideoEntity updateVideo(Integer id, VideoEntity videoEntity) {
        return null;
    }

    @Override
    public List<VideoEntity> getAllVideos() {
        return videoRepository.findAll();
    }

    @Override
    public void deleteVideo(Integer id) {
            videoRepository.deleteById(id);
    }
}
