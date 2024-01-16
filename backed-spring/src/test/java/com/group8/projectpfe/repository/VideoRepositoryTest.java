package com.group8.projectpfe.repository;

import com.group8.projectpfe.entities.Video;
import com.group8.projectpfe.repositories.VideoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VideoRepositoryTest {

    @Autowired
    private VideoRepository videoRepository;

    @Test
    void saveVideoAndFindById() {
        // Arrange
        Video videoToSave = new Video();
        videoToSave.setTitre("Test Video");
        videoToSave.setDescription("This is a test video description");
//        videoToSave.setTags("tag1, tag2");
        videoToSave.setVideoName("test_video.mp4");
        videoToSave.setAddedDate("2022-01-12");

        // Act
        Video savedVideo = videoRepository.save(videoToSave);
        Optional<Video> foundVideoOptional = videoRepository.findById(savedVideo.getId());

        // Assert
        assertTrue(foundVideoOptional.isPresent(), "Video should be present");
        Video foundVideo = foundVideoOptional.get();
        assertEquals(savedVideo.getId(), foundVideo.getId(), "Ids should match");
        assertEquals(savedVideo.getTitre(), foundVideo.getTitre(), "Titles should match");
        assertEquals(savedVideo.getDescription(), foundVideo.getDescription(), "Descriptions should match");
//        assertEquals(savedVideo.getTags(), foundVideo.getTags(), "Tags should match");
        assertEquals(savedVideo.getVideoName(), foundVideo.getVideoName(), "Video names should match");
        assertEquals(savedVideo.getAddedDate(), foundVideo.getAddedDate(), "Added dates should match");
    }

}
