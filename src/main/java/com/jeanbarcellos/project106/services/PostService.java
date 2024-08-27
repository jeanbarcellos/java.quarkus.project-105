package com.jeanbarcellos.project106.services;

import java.util.List;

import com.jeanbarcellos.core.validation.Validate;
import com.jeanbarcellos.core.validation.Validator;
import com.jeanbarcellos.project106.domain.Post;
import com.jeanbarcellos.project106.dtos.PostRequest;
import com.jeanbarcellos.project106.dtos.PostResponse;
import com.jeanbarcellos.project106.mapper.PostMapper;
import com.jeanbarcellos.project106.repositories.PostRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class PostService {

    public static final String MSG_ERROR_CATEGORY_NOT_FOUND = "Não há categoria para o ID '%s' informado.";

    @Inject
    protected Validator validator;

    @Inject
    protected PostRepository repository;

    @Inject
    protected PostMapper mapper;

    public List<PostResponse> getAll() {
        var entities = this.repository.listAll();

        return this.mapper.toList(entities, PostResponse.class);
    }

    public PostResponse getById(Long id) {
        var entity = this.findByIdOrThrow(id);

        return this.mapper.to(entity, PostResponse.class);
    }

    @Transactional
    public PostResponse insert(@Validate PostRequest request) {
        var entity = this.mapper.toEntity(request);

        this.repository.persist(entity);

        this.repository.flush();

        return this.mapper.to(entity, PostResponse.class);
    }

    @Transactional
    public PostResponse update(@Validate PostRequest request) {
        var entity = this.findByIdOrThrow(request.getId());

        this.mapper.copy(entity, request);

        this.repository.flush();

        return this.mapper.to(entity, PostResponse.class);
    }

    @Transactional
    public void delete(Long id) {
        if (!this.repository.existsById(id)) {
            throw new NotFoundException(String.format(MSG_ERROR_CATEGORY_NOT_FOUND, id));
        }

        this.repository.deleteById(id);

        this.repository.flush();
    }

    private Post findByIdOrThrow(Long id) {
        return this.repository.findByIdOrThrow(id,
                () -> new NotFoundException(String.format(MSG_ERROR_CATEGORY_NOT_FOUND, id)));
    }

}
