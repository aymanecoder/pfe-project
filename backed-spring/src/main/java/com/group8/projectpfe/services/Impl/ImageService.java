package com.group8.projectpfe.services.Impl;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {
    @Value("${server.port}")
    private String port;

    private final Path root = Paths.get(new File("src/main/resources/uploads").getAbsolutePath());

    public String saveImage(MultipartFile file) throws IOException {
        if (!Files.exists(root)) {
            Files.createDirectory(root);
        }

        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path path = root.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), path);

        // Construct and return the URL
        String fileUrl = "http://localhost:" + port + "/api/v1/profile/" + uniqueFileName;
        return fileUrl;
    }

    public Resource getProfile(String filename) throws MalformedURLException {
        Path imagePath = root.resolve(filename).normalize();
        Resource resource = new UrlResource(imagePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("Failed to read the file");
        }

        return resource;
    }

    public String getContentType(String fileName) throws IOException {
        Path filePath = root.resolve(fileName).normalize();

        // Check if the file exists and is readable
        if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
            throw new RuntimeException("File cannot be read or does not exist");
        }

        return Files.probeContentType(filePath);
    }

    public void deleteProfile(String filename) throws IOException {
        String[] parts = filename.split("/");
        String fileName = parts[parts.length - 1]; // Get the last part assuming it's the file name

        Path imagePath = root.resolve(fileName).normalize();
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            Files.delete(imagePath);
        } else {

            //    throw new RuntimeException("File cannot be deleted or does not exist");
        }
    }


}


