package com.jeanbarcellos.core;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.jeanbarcellos.core.domain.IAggregateRoot;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceUtil;

public class RepositoryBase<TEntity extends IAggregateRoot, TId> // TId=ID, TEntity=Entity
        implements PanacheRepositoryBase<TEntity, TId> {

    protected static final String FIELD_ID = "id";
    private static final String QUERY_PARAM_JOIN = "=:";
    private static final String QUERY_AND_DELIMITER = " and ";

    private Class<TEntity> entityClass;

    // #region count

    public Long countBy(String fieldName, Object value) {
        return this.count(fieldName, value);
    }

    public Long countBy(Map<String, Object> params) {
        return this.count(this.createQueryFromMap(params), params);
    }

    public Long countById(TId id) {
        return this.count(FIELD_ID, id);
    }

    // #endregion

    // #region exists

    public boolean existsBy(String fieldName, Object value) {
        return this.count(fieldName, value) > 0;
    }

    public boolean existsBy(Map<String, Object> params) {
        return this.count(this.createQueryFromMap(params), params) > 0;
    }

    public boolean existsById(TId id) {
        return this.countById(id) > 0;
    }

    public <T extends Throwable> boolean existsByIdOrThrow(TId id,
            Supplier<? extends T> exceptionSupplier) throws T {
        if (!this.existsById(id)) {
            throw exceptionSupplier.get();
        }

        return true;
    }

    // #endregion

    // #region findBy

    public List<TEntity> findBy(String fieldName, Object value) {
        return this.list(fieldName, value);
    }

    // public List<TEntity> findBy(String fieldName, Object value, Sort sort) {
    // return this.list(fieldName, SortMapper.toPanacheSort(sort), value);
    // }

    public List<TEntity> findBy(Map<String, Object> params) {
        return this.list(this.createQueryFromMap(params), params);
    }

    // public List<TEntity> findBy(Map<String, Object> params, Sort sort) {
    // return this.list(this.createQueryFromMap(params),
    // SortMapper.toPanacheSort(sort), params);
    // }

    // public Page<TEntity> findBy(Map<String, Object> params, PageRequest
    // pageRequest) {
    // Long total = this.countBy(params);

    // List<TEntity> entities = this
    // .find(this.createQueryFromMap(params),
    // SortMapper.toPanacheSort(pageRequest.getSort()), params)
    // .page(pageRequest.getIndex(), pageRequest.getSize())
    // .list();

    // return Page.of(pageRequest.getPage(), pageRequest.getSize(), total,
    // entities);
    // }

    public <T extends Throwable> TEntity findByIdOrThrow(TId id,
            Supplier<? extends T> exceptionSupplier) throws T {
        TEntity entity = this.findById(id);

        if (entity == null) {
            throw exceptionSupplier.get();
        }

        return entity;
    }

    // #endregion

    // #region findFirstBy

    public TEntity findFirstBy(String fieldName, Object value) {
        var entities = this.findBy(fieldName, value);

        return entities.isEmpty() ? null : entities.get(0);
    }

    // public TEntity findFirstBy(String fieldName, Object value, Sort sort) {
    // var entities = this.findBy(fieldName, value, sort);

    // return entities.isEmpty() ? null : entities.get(0);
    // }

    public Optional<TEntity> findFirstByAsOptional(String fieldName, Object value) {
        var entity = this.findFirstBy(fieldName, value);

        return entity == null ? Optional.empty() : Optional.of(entity);
    }

    public <T extends Throwable> TEntity findFirstByOrTrhow(String fieldName, Object value,
            Supplier<? extends T> exceptionSupplier) throws T {
        return this.findFirstByAsOptional(fieldName, value).orElseThrow(exceptionSupplier);
    }

    // #endregion

    // #region findAll

    // public List<TEntity> findAll(Sort sort) {
    // return this.findAll(SortMapper.toPanacheSort(sort))
    // .list();
    // }

    // public Page<TEntity> findAll(PageRequest pageRequest) {
    // Long total = this.count();

    // List<TEntity> entities = this
    // .findAll(SortMapper.toPanacheSort(pageRequest.getSort()))
    // .page(pageRequest.getIndex(), pageRequest.getSize())
    // .list();

    // return Page.of(pageRequest.getPage(), pageRequest.getSize(), total,
    // entities);
    // }

    // #endregion

    // -------------------------------------------------------------------------

    public TEntity getReference(TId id) {
        return this.getEntityManager().getReference(this.getEntityClass(), id);
    }

    // -------------------------------------------------------------------------

    // isInitialized
    public boolean isLoaded(Object entity) {
        PersistenceUtil persistenceUnitUtil = Persistence.getPersistenceUtil();
        return persistenceUnitUtil.isLoaded(entity);
    }

    // isInitialized
    public boolean isLoaded(Object entity, String attributeName) {
        PersistenceUtil persistenceUnitUtil = Persistence.getPersistenceUtil();
        return persistenceUnitUtil.isLoaded(entity, attributeName);
    }

    // -------------------------------------------------------------------------

    public EntityTransaction getTransaction() {
        return this.getEntityManager().getTransaction();
        // return this.getSession().getTransaction();
    }

    // -------------------------------------------------------------------------

    // #region Private methods

    protected String createQueryFromMap(Map<String, Object> map) {
        return map.entrySet().stream()
                .map(entry -> entry.getKey() + QUERY_PARAM_JOIN + entry.getKey())
                .collect(Collectors.joining(QUERY_AND_DELIMITER));
    }

    @SuppressWarnings("unchecked")
    private Class<TEntity> getEntityClass() {
        if (this.entityClass == null) {
            this.entityClass = (Class<TEntity>) ((ParameterizedType) getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0];
        }

        return this.entityClass;
    }

    // #endregion

    public void detach(TEntity entity) {
        this.getEntityManager().detach(entity);
    }

    public void merge(TEntity entity) {
        this.getEntityManager().merge(entity);
    }
}
