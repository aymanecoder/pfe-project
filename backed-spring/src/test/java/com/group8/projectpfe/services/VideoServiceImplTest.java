package com.group8.projectpfe.services;
import com.group8.projectpfe.domain.dto.VideoDto;
import com.group8.projectpfe.entities.Video;
import com.group8.projectpfe.mappers.impl.VideoMapperImpl;
import com.group8.projectpfe.repositories.VideoRepository;
import com.group8.projectpfe.services.Impl.VideoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

class VideoServiceImplTest {

    private VideoRepository videoRepository;
    private VideoMapperImpl videoMapper;
    private VideoServiceImpl videoService;

    @BeforeEach
    void setUp() {
        videoRepository = Mockito.mock(VideoRepository.class);
        videoMapper = Mockito.mock(VideoMapperImpl.class);
        videoService = new VideoServiceImpl(videoRepository, videoMapper);
    }

    @Test
    void createVideo_ValidVideoDto_ReturnsCreatedVideoDto() {
        // Arrange
        VideoDto videoDto = new VideoDto();
        videoDto.setTitre("Test Video");

        Video video = new Video();
        video.setTitre(videoDto.getTitre());

        Video savedVideo = new Video();
        savedVideo.setId(1);
        savedVideo.setTitre(videoDto.getTitre());

        Mockito.when(videoMapper.mapFrom(videoDto)).thenReturn(video);
        Mockito.when(videoRepository.save(video)).thenReturn(savedVideo);
        Mockito.when(videoMapper.mapTo(savedVideo)).thenReturn(videoDto);

        // Act
        VideoDto result = videoService.createVideo(videoDto);

        // Assert
        Assertions.assertNotNull(result);
//        Assertions.assertEquals(savedVideo.getId(), result.getId());
        Assertions.assertEquals(savedVideo.getTitre(), result.getTitre());
    }

    @Test
    void updateVideo_ExistingIdAndValidVideoDto_ReturnsUpdatedVideoDto() {
        // Arrange
        Integer id = 1;
        VideoDto videoDto = new VideoDto();
        videoDto.setTitre("Updated Video");

        Video existingVideo = new Video();
        existingVideo.setId(id);

        Video updatedVideo = new Video();
        updatedVideo.setId(id);
        updatedVideo.setTitre(videoDto.getTitre());
        updatedVideo.setDescription(videoDto.getDescription());

        Mockito.when(videoRepository.findById(id)).thenReturn(Optional.of(existingVideo));
        Mockito.when(videoRepository.save(existingVideo)).thenReturn(updatedVideo);
        Mockito.when(videoMapper.mapTo(updatedVideo)).thenReturn(videoDto);

        // Act
        VideoDto result = videoService.updateVideo(id, videoDto);

        // Assert
        Assertions.assertNotNull(result);
//        Assertions.assertEquals(updatedVideo.getId(), result.getId());
        Assertions.assertEquals(updatedVideo.getTitre(), result.getTitre());
    }
}