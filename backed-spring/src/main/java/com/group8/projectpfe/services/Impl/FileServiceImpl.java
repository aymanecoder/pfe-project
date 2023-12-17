package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.domain.dto.FileModel;
import com.group8.projectpfe.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {
    @Override
    public FileModel uploadVideo(String path, MultipartFile file) throws IOException {
        FileModel fileModel = new FileModel();
        String fileName = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String finalName = randomId.concat(fileName).substring(fileName.indexOf("."));

        String filePath = path + File.separator + fileName ;

        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }
        Files.copy(file.getInputStream(), Paths.get(filePath));
        fileModel.setVideoFileName(finalName);
        return fileModel;
    }

    @Override
    public InputStream getVideoFile(String path, String fileName, int id) throws FileNotFoundException {
        String fullPath = path + File.separator +fileName;
        InputStream inputStream = new FileInputStream(fullPath);

        return inputStream;
    }
}
