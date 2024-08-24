package com.jeanbarcellos.core;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class MapperBase {

    private ModelMapper modelMapper;

    protected <S, D> D map(S source, Class<D> destinationType) {
        return this.getModelMapper()
                .map(source, destinationType);
    }

    protected <S, D> List<D> mapList(List<S> source, Class<D> destinationType) {
        var mp = this.getModelMapper();

        return source
                .stream()
                .map(element -> mp.map(element, destinationType))
                .collect(Collectors.toList());
    }

    protected <D, S> D copyProperties(D destination, S source) {
        this.getModelMapper().map(source, destination);

        return destination;
    }

    private ModelMapper getModelMapper() {
        if (this.modelMapper != null) {
            return modelMapper;
        }

        var mp = new ModelMapper();

        mp.getConfiguration()
                .setSkipNullEnabled(true);

        return mp;
    }

}
