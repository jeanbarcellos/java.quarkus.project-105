package com.jeanbarcellos.project106.repositories;

import com.jeanbarcellos.core.Constants;
import com.jeanbarcellos.core.RepositoryBase;
import com.jeanbarcellos.core.exception.ApplicationException;
import com.jeanbarcellos.project106.domain.Comment;
import com.jeanbarcellos.project106.domain.Post;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;

@ApplicationScoped
public class PostRepository extends RepositoryBase<Post, Long> {

    public Comment findCommentById(Long postId, Long commentId) {
        try {
            var qlString = "SELECT c FROM Comment c WHERE c.id = :commentId AND c.post.id = :postId";

            var query = this.getEntityManager().createQuery(qlString, Comment.class);
            query.setParameter("commentId", commentId);
            query.setParameter("postId", postId);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new ApplicationException(Constants.MSG_ERROR_PERSISTENCE, e);
        }
    }

}
