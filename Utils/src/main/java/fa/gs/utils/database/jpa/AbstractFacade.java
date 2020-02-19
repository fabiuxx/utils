/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.jpa;

import java.util.Collection;
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
 */
public abstract class AbstractFacade<T> implements Facade<T> {

    /**
     * Clase de la entidad asociada a la fachada.
     */
    protected final Class<T> entityClass;

    /**
     * Constructor.
     *
     * @param entityClass Clase de la entidad asociada a la fachada.
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Obtiene la referencia a la clase de la entidad asociada.
     *
     * @return Clase de la entidad asociada a la facahada.
     */
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
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void detach(T entity) {
        EntityManager em = getEntityManager();
        em.detach(entity);
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
        T entity0 = em.merge(entity);
        em.remove(entity0);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public T find(Object id) {
        EntityManager em = getEntityManager();
        T entity = em.find(entityClass, id);
        return entity;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Collection<T> findAll() {
        EntityManager em = getEntityManager();
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = em.createQuery(cq);
        return q.getResultList();
    }

}
