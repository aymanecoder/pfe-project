package com.group8.projectpfe.services.Impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.group8.projectpfe.domain.dto.VideoDto;
import com.group8.projectpfe.mappers.impl.VideoMapperImpl;
import com.group8.projectpfe.repositories.VideoRepository;
import com.group8.projectpfe.services.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3FileServiceImpl implements S3FileService {
    private final AmazonS3 s3;
    private final VideoRepository videoRepository;
    @Value("${aws.s3.bucket}")
    private String bucketName;

    private final VideoMapperImpl videoMapper;
    @Override
    public String saveFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        int count = 0;
        int maxTries = 3;
        while (true) {
            try {
                File file1 = convertMultiPartToFile(file);
                PutObjectResult putObjectResult = s3.putObject(bucketName, originalFilename, file1);
//                VideoDto videoDto = new VideoDto();
//                videoDto.setVideoName(originalFilename);
//                videoDto.setTitre(title);
//                videoDto.setDescription(description);
//                videoDto.setNumberOfTeam(numberOfTeams);
//                videoDto.setAddedDate(date.toString());
//                videoRepository.save(videoMapper.mapFrom(videoDto));
                return putObjectResult.getContentMd5();
            } catch (IOException e) {
                if (++count == maxTries) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public byte[] downloadFile(String filename) {
        S3Object object = s3.getObject(bucketName, filename);
        S3ObjectInputStream objectContent = object.getObjectContent();
        try {
            return IOUtils.toByteArray(objectContent);
        } catch (IOException e) {
            throw  new RuntimeException(e);
        }


    }

    @Override
    public String deleteFile(String filename) {

        s3.deleteObject(bucketName,filename);
        return "File deleted";
    }

    @Override
    public List<String> listAllFiles() {

        ListObjectsV2Result listObjectsV2Result = s3.listObjectsV2(bucketName);
        return  listObjectsV2Result.getObjectSummaries().stream().map(S3ObjectSummary::getKey).collect(Collectors.toList());

    }


    private File convertMultiPartToFile(MultipartFile file ) throws IOException
    {
        File convFile = new File( file.getOriginalFilename() );
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        return convFile;
    }

}
