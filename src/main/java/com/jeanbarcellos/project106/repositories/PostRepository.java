package com.jeanbarcellos.project106.repositories;

import com.jeanbarcellos.core.RepositoryBase;
import com.jeanbarcellos.project106.domain.Comment;
import com.jeanbarcellos.project106.domain.Post;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Query;

@ApplicationScoped
public class PostRepository extends RepositoryBase<Post, Long> {

    public Comment findCommentById(Long postId, Long commentId) {

        String sql = "SELECT c FROM Comment c WHERE c.id = :commendId AND c.post.id = :postId";

        Query query = this.getEntityManager().createQuery(sql, Comment.class);
        query.setParameter("commendId", commentId);
        query.setParameter("postId", postId);

        return (Comment) query.getSingleResult();
    }

}
