package com.jeanbarcellos.core;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.jeanbarcellos.core.domain.IAggregateRoot;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class MapperBase<TEntity extends IAggregateRoot> {

    private ModelMapper modelMapper;

    private Class<TEntity> entityClass;

    public <S> TEntity toEntity(S source) {
        return this.map(source);
    }

    public <D> D to(TEntity source, Class<D> destinationType) {
        return this.map(source, destinationType);
    }

    public <D> List<D> toList(List<TEntity> source, Class<D> destinationType) {
        return this.mapList(source, destinationType);
    }

    // ---

    protected <S> TEntity map(S source) {
        return this.getModelMapper()
                .map(source, this.getEntityClass());
    }

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

    @SuppressWarnings("unchecked")
    private Class<TEntity> getEntityClass() {
        if (this.entityClass == null) {
            this.entityClass = (Class<TEntity>) ((ParameterizedType) getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0];
        }

        return this.entityClass;
    }

}
