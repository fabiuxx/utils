/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.jpa.facade;

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

    private void flush(EntityManager em) {
        /**
         * Por defecto, cada operacion a traves de hibernate se mantiene dentro
         * del contexto del entity manager y son enviados a la base de datos al
         * finalizar la transaccion actual (normalmente, cada metodo dentro de
         * un EJB incluye una transaccion implicita). Cuando las modificaciones
         * son enviadas (se hace flush) a la base de datos, la misma normalmente
         * esta configurada con auto-commit, lo cual implica que cada sentencia
         * es ejecutada y persistida directamente en la base de datos
         * independientemente a las demas sentencias dentro de un bloque de
         * sentencias de una funcionalidad EJB.
         *
         * Para mantener la transaccionabilidad, es necesario que cada bloque de
         * sentencias hibernate (funcionalidades en un EJB) sean ancapsulados
         * dentro de una transaccion explicita de usuario que pueda ser
         * confirmada o rechazada directamente. Con esto, por mas que se haga
         * flush de cada sentencia, las mismas no seran aplicadas hasta que la
         * transaccion general sea confirmada (commit) o rechazada (rollback).
         *
         * Es necesario que los cambios a nivel hibernate esten disponibles
         * inmediatamente en base de datos ya que existen funcionalidades que se
         * ejecutan enteramente dentro del contexto de la base de datos
         * (funciones/triggers plpython) y podrian requerir acceder a los datos
         * que existen solo dentro del contexto del entity manager de hibernate.
         */
        em.flush();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void attach(T entity) {
        EntityManager em = getEntityManager();
        em.merge(entity);
        flush(em);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void detach(T entity) {
        EntityManager em = getEntityManager();
        em.detach(entity);
        flush(em);
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
            flush(em);
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
            flush(em);
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
        flush(em);
    }

}
