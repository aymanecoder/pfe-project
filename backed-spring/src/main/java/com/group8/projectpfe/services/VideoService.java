package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.VideoDto;


import java.util.List;

public interface VideoService {
    VideoDto createVideo(VideoDto videoDto);
    VideoDto getVideoById(Integer id);
    VideoDto updateVideo(Integer id,VideoDto videoDto);
    List<VideoDto> getAllVideos();
    void deleteVideo(Integer id);
}
