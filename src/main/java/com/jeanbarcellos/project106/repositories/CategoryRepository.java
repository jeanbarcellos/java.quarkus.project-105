package com.jeanbarcellos.project106.repositories;

import com.jeanbarcellos.core.RepositoryBase;
import com.jeanbarcellos.project106.domain.Category;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoryRepository extends RepositoryBase<Category, Long> {

}
