package com.neeraj.preperation.utils;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.DestinationSetter;
import org.modelmapper.spi.MatchingStrategy;
import org.modelmapper.spi.SourceGetter;

import java.util.Map;

@Slf4j
public class MapperUtil {

    private MapperUtil() {
    }

    public static <D> D map(Object source, Class<D> destinationType) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        log.trace("Converting Source Type : {}, to Destination type : {}", source.getClass().getName(), destinationType.getName());
        return mapper.map(source, destinationType);
    }

    public static <S, D, V> D map(Object source, Class<D> destination, MatchingStrategy strategies, Map<SourceGetter<S>, DestinationSetter<D, V>> mapping) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(strategies);
        var propertyMapper = mapper.createTypeMap(source, destination);
//        mapping.forEach(propertyMapper::addMapping);
        return mapper.map(source, destination);
    }

    public static <S, D> D map(S source, Class<D> destinationType, PropertyMap<S, D> mapping) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        mapper.addMappings(mapping);
        log.trace("Converting Source Type : {}, to Destination type : {}", source.getClass().getName(), destinationType.getName());
        return mapper.map(source, destinationType);
    }

}
