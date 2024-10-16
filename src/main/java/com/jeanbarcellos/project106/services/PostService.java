package com.jeanbarcellos.project106.services;

import java.util.ArrayList;
import java.util.List;

import com.jeanbarcellos.core.Constants;
import com.jeanbarcellos.core.exception.ValidationException;
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

    public static final String MSG_ERROR_POST_NOT_FOUND = "Não há post para o ID '%s' informado.";

    public static final String MSG_POST_NAME = "post";
    public static final String MSG_PESSOA_NAME = "pessoa";
    public static final String MSG_CATEGORIA_NAME = "categoria";
    public static final String MSG_COMENTARIO_NAME = "comentário";

    @Inject
    protected Validator validator;

    @Inject
    protected PostRepository repository;

    @Inject
    protected PostMapper mapper;

    @Inject
    protected PersonRepository personRepository;

    @Inject
    protected CategoryRepository categoryRepository;

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

    // -----------------------------------------------------------------------------------------------------------------
    // Comentários
    // -----------------------------------------------------------------------------------------------------------------

    public List<CommentResponse> getAllComments(Long postId) {
        var post = this.findByIdOrThrow(postId);

        var comments = post.getComments();

        return this.mapper.toList(comments, CommentResponse.class);
    }

    @Transactional
    public void deleteAllComments(Long postId) {
        var post = this.findByIdOrThrow(postId);

        post.removeAllComments();

        this.repository.flush();
    }

    public CommentResponse getCommentById(Long postId, Long commentId) {
        var post = this.findByIdOrThrow(postId);

        var comment = this.findCommentByIdOrThrow(post, commentId);

        return this.mapper.to(comment, CommentResponse.class);
    }

    @Transactional
    public CommentResponse insertComment(@Validate CommentRequest request) {
        validate(request); // test

        var post = this.findByIdOrThrow(request.getPostId());

        var comment = this.mapper.to(request, Comment.class);

        post.addComment(comment);

        this.repository.flush();

        return this.mapper.to(comment, CommentResponse.class);
    }

    @Transactional
    public CommentResponse updateComment(@Validate CommentRequest request) {
        var post = this.findByIdOrThrow(request.getPostId());

        var comment = this.findCommentByIdOrThrow(post, request.getId());

        this.mapper.copy(comment, request);

        this.repository.flush();

        return this.mapper.to(comment, CommentResponse.class);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        var post = this.findByIdOrThrow(postId);

        var comment = this.findCommentByIdOrThrow(post, commentId);

        post.removeCommentById(comment.getId());

        this.repository.flush();
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private Post findByIdOrThrow(Long id) {
        return this.repository.findByIdOrThrow(id,
                () -> new NotFoundException(String.format(MSG_ERROR_POST_NOT_FOUND, id)));
    }

    private Comment findCommentByIdOrThrow(Post post, Long commentId) {

        var comment = this.repository.findCommentById(post.getId(), commentId);

        if (comment == null) {
            throw new NotFoundException(
                    String.format(Constants.MSG_ERROR_ENTITY_NOT_FOUND, MSG_COMENTARIO_NAME, commentId));
        }

        return comment;
    }

    private void validate(PostRequest request) {
        if (!this.categoryRepository.existsById(request.getCategoryId())) {
            throw new NotFoundException(
                    String.format(Constants.MSG_ERROR_ENTITY_NOT_FOUND, MSG_CATEGORIA_NAME, request.getCategoryId()));
        }

        if (!this.personRepository.existsById(request.getAuthorId())) {
            throw new NotFoundException(
                    String.format(Constants.MSG_ERROR_ENTITY_NOT_FOUND, MSG_PESSOA_NAME, request.getAuthorId()));
        }
    }

    private void validate(CommentRequest request) {
        var errors = new ArrayList<String>();
        var pattern = "possui valor de %s que não existe. Valor: %s";

        if (!this.repository.existsById(request.getPostId())) {
            var msg = String.format(pattern, MSG_CATEGORIA_NAME, request.getPostId());
            errors.add(Validator.createMessage("postId", msg));
        }

        if (!this.personRepository.existsById(request.getAuthorId())) {
            var msg = String.format(pattern, MSG_PESSOA_NAME, request.getAuthorId());
            errors.add(Validator.createMessage("authorId", msg));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(Constants.MSG_ERROR_VALIDATION, errors);
        }
    }

}
