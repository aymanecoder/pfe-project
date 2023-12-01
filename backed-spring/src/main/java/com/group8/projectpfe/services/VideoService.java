package com.group8.projectpfe.services;

import com.group8.projectpfe.entities.VideoEntity;

import java.util.List;

public interface VideoService {
    VideoEntity createVideo(VideoEntity videoEntity);
    VideoEntity getVideoById(Integer id);
    VideoEntity updateVideo(Integer id,VideoEntity videoEntity);
    List<VideoEntity> getAllVideos();
    void deleteVideo(Integer id);
}
