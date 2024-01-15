package com.group8.projectpfe.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface S3FileService {
    String saveFile(MultipartFile file);
    byte[] downloadFile(String filename);
    String deleteFile(String filename);
    List<String> listAllFiles();
}
