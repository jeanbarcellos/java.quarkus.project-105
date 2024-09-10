Mensagens

```java
    private Post findByIdOrThrow(Long id) {
        return this.repository.findByIdOrThrow(id,
                () -> new NotFoundException(String.format(MSG_ERROR_POST_NOT_FOUND, id)));
                // () -> new NotFoundException(String.format(Constants.MSG_ERROR_ENTITY_NOT_FOUND, MSG_POST_NAME, id)));
    }

```

Find Hibernate or JPA

```java
    /**
     * Obter comentário de um post
     *
     * OBS: Usando JPA/Jakarta
     *
     * @param postId
     * @param commentId
     * @return Comment
     */
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

    /**
     * Obter comentário de um post
     *
     * OBS: Usando Hibernate
     *
     * @param postId
     * @param commentId
     * @return Comment
     */
    public Comment findCommentById2(Long postId, Long commentId) {
        try {
            var qlString = "SELECT c FROM Comment c WHERE c.id = :commentId AND c.post.id = :postId";

            var query = this.getSession().createQuery(qlString, Comment.class);
            query.setParameter("commentId", commentId);
            query.setParameter("postId", postId);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new ApplicationException(Constants.MSG_ERROR_PERSISTENCE, e);
        }
    }

```
