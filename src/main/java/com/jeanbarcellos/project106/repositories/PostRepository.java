package com.jeanbarcellos.project106.repositories;

import com.jeanbarcellos.core.RepositoryBase;
import com.jeanbarcellos.project106.domain.Post;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PostRepository extends RepositoryBase<Post, Long> {

}
