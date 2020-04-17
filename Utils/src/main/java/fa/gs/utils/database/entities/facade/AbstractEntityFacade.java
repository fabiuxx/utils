/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.entities.facade;

import fa.gs.utils.collections.Lists;
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
public abstract class AbstractEntityFacade<T> implements EntityFacade<T> {

    /**
     * Clase de la entidad asociada a la fachada.
     */
    protected final Class<T> entityClass;

    /**
     * Constructor.
     *
     * @param entityClass Clase de la entidad asociada a la fachada.
     */
    public AbstractEntityFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Obtiene la referencia a la clase de la entidad asociada.
     *
     * @return Clase de la entidad asociada a la facahada.
     */
    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }

    /**
     * Obtiene una instancia del administrador de entidades de la capa de
     * persistencia.
     *
     * @return Referencia al administrador de entidades de la capa de
     * persistencia.
     */
    public abstract EntityManager getEntityManager();

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
        em.flush();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public T selectById(Object id) {
        EntityManager em = getEntityManager();
        T entity = em.find(entityClass, id);
        return entity;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Collection<T> selectAll() {
        EntityManager em = getEntityManager();
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = em.createQuery(cq);
        return q.getResultList();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public T selectFirst() {
        EntityManager em = getEntityManager();
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = em.createQuery(cq);
        q.setMaxResults(1);
        List<T> L = q.getResultList();
        return Lists.first(L);
    }

}
