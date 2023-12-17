package com.group8.projectpfe.controllers;

import com.group8.projectpfe.services.Impl.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/profile")
public class profileController {


    private final ImageService imageService;
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> getProfile(@PathVariable String fileName) throws IOException {
        Resource resource = imageService.getProfile(fileName);

        // Determine the content type dynamically based on the file/resource
        MediaType mediaType = MediaType.IMAGE_JPEG; // Set a default media type, change accordingly

        try {
            mediaType = MediaType.parseMediaType(imageService.getContentType(fileName));
        } catch (IOException e) {
            // Handle exception if content type cannot be determined
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

}

