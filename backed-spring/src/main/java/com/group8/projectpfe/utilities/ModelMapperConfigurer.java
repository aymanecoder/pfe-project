package com.group8.projectpfe.utilities;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class ModelMapperConfigurer {

    public static void configureModelMapper(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        // Add additional configuration if needed for nested mappings and collections
    }
}