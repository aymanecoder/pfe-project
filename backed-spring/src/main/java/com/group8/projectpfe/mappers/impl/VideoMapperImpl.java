package com.group8.projectpfe.mappers.impl;

import com.group8.projectpfe.domain.dto.VideoDto;
import com.group8.projectpfe.entities.VideoEntity;
import com.group8.projectpfe.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoMapperImpl implements Mapper<VideoEntity, VideoDto> {
    private final ModelMapper modelMapper;
    @Override
    public VideoDto mapTo(VideoEntity videoEntity) {
        return modelMapper.map(videoEntity,VideoDto.class);
    }

    @Override
    public VideoEntity mapFrom(VideoDto videoDto) {
        return modelMapper.map(videoDto,VideoEntity.class);
    }
}
