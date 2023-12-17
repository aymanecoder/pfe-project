package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.FileModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    FileModel uploadVideo(String path, MultipartFile file) throws IOException;
    InputStream getVideoFile(String path,String fileName,int id) throws FileNotFoundException;
}
