package com.jeanbarcellos.project106.services;

import java.util.List;

import com.jeanbarcellos.core.Constants;
import com.jeanbarcellos.core.validation.Validate;
import com.jeanbarcellos.core.validation.Validator;
import com.jeanbarcellos.project106.domain.Comment;
import com.jeanbarcellos.project106.domain.Post;
import com.jeanbarcellos.project106.dtos.CommentRequest;
import com.jeanbarcellos.project106.dtos.CommentResponse;
import com.jeanbarcellos.project106.dtos.PostRequest;
import com.jeanbarcellos.project106.dtos.PostResponse;
import com.jeanbarcellos.project106.mapper.PostMapper;
import com.jeanbarcellos.project106.repositories.CategoryRepository;
import com.jeanbarcellos.project106.repositories.PersonRepository;
import com.jeanbarcellos.project106.repositories.PostRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class PostService {

    public static final String MSG_ERROR_POST_NOT_FOUND = "Não há categoria para o ID '%s' informado.";

    @Inject
    protected Validator validator;

    @Inject
    protected PostRepository repository;

    @Inject
    protected PostMapper mapper;

    @Inject
    PersonRepository personRepository;

    @Inject
    CategoryRepository categoryRepository;

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
        this.validate(request);

        var entity = this.mapper.toEntity(request);

        this.repository.persist(entity);

        this.repository.flush();

        return this.mapper.to(entity, PostResponse.class);
    }

    @Transactional
    public PostResponse update(@Validate PostRequest request) {
        this.validate(request);

        var entity = this.findByIdOrThrow(request.getId());

        this.mapper.copy(entity, request);

        this.repository.flush();

        return this.mapper.to(entity, PostResponse.class);
    }

    @Transactional
    public void delete(Long id) {
        if (!this.repository.existsById(id)) {
            throw new NotFoundException(String.format(MSG_ERROR_POST_NOT_FOUND, id));
        }

        this.repository.deleteById(id);

        this.repository.flush();
    }

    public List<CommentResponse> getAllComments(Long id) {
        var post = this.findByIdOrThrow(id);

        var comments = post.getComments();

        return this.mapper.toList(comments, CommentResponse.class);
    }

    @Transactional
    public void deleteAllComments(Long id) {
        var post = this.findByIdOrThrow(id);

        var comments = post.getComments();
        comments.clear();

        this.repository.flush();
    }

    @Transactional
    public CommentResponse insertComment(@Validate CommentRequest request) {
        var post = this.findByIdOrThrow(request.getPostId());

        var comment = this.mapper.to(request, Comment.class);

        var comments = post.getComments();
        comments.add(comment);

        this.repository.flush();

        return this.mapper.to(comment, CommentResponse.class);
    }

    public CommentResponse getCommentById(Long postId, Long commentId) {
        var post = this.findByIdOrThrow(postId);

        var comment = this.findCommentByIdOrThrow(post, commentId);

        return this.mapper.to(comment, CommentResponse.class);
    }

    @Transactional
    public CommentResponse updateComment(@Validate CommentRequest request) {
        var post = this.findByIdOrThrow(request.getPostId());

        var comment = this.findCommentByIdOrThrow(post, request.getId());

        this.mapper.copy(comment, request);

        return this.mapper.to(comment, CommentResponse.class);
    }

    public void deleteCommentById(Long postId, Long commentId) {
        var post = this.findByIdOrThrow(postId);

        var comment = this.findCommentByIdOrThrow(post, commentId);

        log.info("Comment {}", comment);
    }

    private Post findByIdOrThrow(Long id) {
        return this.repository.findByIdOrThrow(id,
                () -> new NotFoundException(String.format(MSG_ERROR_POST_NOT_FOUND, id)));
    }

    private Comment findCommentByIdOrThrow(Post post, Long commentId) {
        var comment = post.findCommentById(commentId);

        if (comment == null) {
            throw new NotFoundException(String.format(Constants.MSG_ERROR_ENTITY_NOT_FOUND, "comentário", commentId));
        }

        return comment;
    }

    private void validate(PostRequest request) {
        if (!this.categoryRepository.existsById(request.getCategoryId())) {
            throw new NotFoundException(
                    String.format(Constants.MSG_ERROR_ENTITY_NOT_FOUND, "categoria", request.getCategoryId()));
        }

        if (!this.personRepository.existsById(request.getAuthorId())) {
            throw new NotFoundException(
                    String.format(Constants.MSG_ERROR_ENTITY_NOT_FOUND, "pessoa", request.getAuthorId()));
        }
    }

}
