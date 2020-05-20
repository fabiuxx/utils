/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.crud;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.database.dto.DtoMapper;
import fa.gs.utils.database.dto.facade.DtoFacade;
import fa.gs.utils.database.entities.facade.EntityFacade;
import fa.gs.utils.database.jpa.Jpa;
import fa.gs.utils.database.query.commands.CountQuery;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.result.simple.Result;
import fa.gs.utils.result.simple.Results;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public abstract class AbstractMixedFacade<T> implements EntityFacade<T>, DtoFacade<T> {

    private final Class<T> domainClass;

    /**
     * Constructor.
     *
     * @param domainClass Clase que esta anotada como entidad y como dto.
     */
    protected AbstractMixedFacade(Class<T> domainClass) {
        this.domainClass = domainClass;
    }

    /**
     * Obtiene una instancia del administrador de entidades de la capa de
     * persistencia.
     *
     * @return Referencia al administrador de entidades de la capa de
     * persistencia.
     */
    public abstract EntityManager getEntityManager();

    //<editor-fold defaultstate="collapsed" desc="Implementacion DtoFacade">
    /**
     * Obtiene la referencia a la clase del DTO asociado.
     *
     * @return Clase del DTO asociado a la facahada.
     */
    @Override
    public Class<T> getDtoClass() {
        return domainClass;
    }

    @Override
    public Result<Long> count(String query) {
        Result<Long> result;

        try {
            Long value = Jpa.count(query, CountQuery.COUNT_FIELD_NAME, getEntityManager());
            result = Results.ok()
                    .value(value)
                    .build();
        } catch (Throwable thr) {
            result = Results.ko()
                    .cause(thr)
                    .message("Ocurrio un error cargando datos.")
                    .tag("dto.classname", getDtoClass().getCanonicalName())
                    .build();
        }

        return result;
    }

    @Override
    public Result<T[]> selectAll(String query) {
        Result<T[]> result;

        try {
            DtoMapper<T> mapper = DtoMapper.prepare(getDtoClass());
            T[] values = mapper.select(query, getEntityManager());
            result = Results.ok()
                    .value(values)
                    .build();
        } catch (Throwable thr) {
            result = Results.ko()
                    .cause(thr)
                    .message("Ocurrio un error cargando datos.")
                    .tag("dto.classname", getDtoClass().getCanonicalName())
                    .build();
        }

        return result;
    }

    @Override
    public Result<T> selectFirst(String query) {
        Result<T> result;

        try {
            Result<T[]> resSelect = selectAll(query);
            resSelect.raise();

            T value;
            T[] values = resSelect.value(null);
            if (Assertions.isNullOrEmpty(values)) {
                value = null;
            } else {
                value = values[0];
            }

            result = Results.ok()
                    .value(value)
                    .build();
        } catch (Throwable thr) {
            result = Results.ko()
                    .cause(thr)
                    .message("Ocurrio un error cargando datos.")
                    .tag("dto.classname", getDtoClass().getCanonicalName())
                    .build();
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Implementacion EntityFacade">
    /**
     * Obtiene la referencia a la clase de la entidad asociada.
     *
     * @return Clase de la entidad asociada a la facahada.
     */
    @Override
    public Class<T> getEntityClass() {
        return domainClass;
    }

    /**
     * Permite verificar si no se viola ninguna restriccion antes de persistir
     * una entidad.
     *
     * @param entity Entidad.
     * @return True si la entidad cumple sus restricciones, False caso
     * contrario.
     */
    public boolean checkConstraints(T entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if (constraintViolations.size() > 0) {
            for (ConstraintViolation<T> constraint : constraintViolations) {
                String violation = constraint.getRootBeanClass().getSimpleName() + "." + constraint.getPropertyPath() + " " + constraint.getMessage();
                throw new ValidationException(violation);
            }
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void attach(T entity) {
        EntityManager em = getEntityManager();
        em.merge(entity);
        em.flush();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void detach(T entity) {
        EntityManager em = getEntityManager();
        em.detach(entity);
        em.flush();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public T create(T entity) {
        boolean valid = checkConstraints(entity);
        if (valid) {
            EntityManager em = getEntityManager();
            em.persist(entity);
            em.flush();
        }
        return entity;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public T edit(T entity) {
        boolean valid = checkConstraints(entity);
        if (valid) {
            EntityManager em = getEntityManager();
            em.merge(entity);
            em.flush();
        }
        return entity;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void remove(T entity) {
        EntityManager em = getEntityManager();
        em.remove(entity);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Deprecated
    public T selectById(Object id) {
        EntityManager em = getEntityManager();
        T entity = em.find(getEntityClass(), id);
        return entity;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Deprecated
    public Collection<T> selectAll() {
        EntityManager em = getEntityManager();
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(getEntityClass()));
        javax.persistence.Query q = em.createQuery(cq);
        return q.getResultList();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Deprecated
    public T selectFirst() {
        EntityManager em = getEntityManager();
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(getEntityClass()));
        javax.persistence.Query q = em.createQuery(cq);
        q.setMaxResults(1);
        List<T> L = q.getResultList();
        return Lists.first(L);
    }
    //</editor-fold>

}
