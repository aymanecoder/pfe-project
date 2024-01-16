package com.group8.projectpfe.mappers.impl;

import com.group8.projectpfe.domain.dto.VideoDto;
import com.group8.projectpfe.entities.Video;
import com.group8.projectpfe.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoMapperImpl implements Mapper<Video, VideoDto> {
    private final ModelMapper modelMapper;
    @Override
    public VideoDto mapTo(Video video) {
        return modelMapper.map(video,VideoDto.class);
    }

    @Override
    public Video mapFrom(VideoDto videoDto) {
        return modelMapper.map(videoDto, Video.class);
    }
}
