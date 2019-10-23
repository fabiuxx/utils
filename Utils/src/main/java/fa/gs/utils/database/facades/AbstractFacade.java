/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.facades;

import fa.gs.utils.collections.maps.ResultSetMap;
import fa.gs.utils.database.criteria.Condition;
import fa.gs.utils.database.criteria.Operator;
import fa.gs.utils.database.criteria.OrderKind;
import fa.gs.utils.database.criteria.QueryCriteria;
import fa.gs.utils.database.criteria.Sorting;
import fa.gs.utils.database.utils.ResultSetMapper;
import fa.gs.utils.misc.Assertions;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Path;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.CacheMode;
import org.hibernate.transform.Transformers;

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
     * Elimina una entidad dada del contexto de persistencia, indicando que
     * cualquier modificacion sobre la misma no tendra impacto final dentro de
     * la base de datos.
     *
     * @param entity Entidad.
     */
    @Override
    public void detach(T entity) {
        EntityManager em = getEntityManager();
        em.detach(entity);
    }

    /**
     * Crea un nuevo registro para la entidad.
     *
     * @param entity Objeto a ser persistido.
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
     * Guarda los cambios efectuados sobre una entidad.
     *
     * @param entity Objeto cuyas modificaciones seran persistidas.
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
     * Elimina el registro asociado a la entidad.
     *
     * @param entity Objeto a ser eliminado.
     */
    @Override
    public void remove(T entity) {
        EntityManager em = getEntityManager();
        T entity0 = em.merge(entity);
        em.remove(entity0);
    }

    /**
     * Refresca el estado interno de la entidad.
     *
     * @param entity Objeto a ser refrescado.
     */
    @Override
    public T refresh(T entity) {
        EntityManager em = getEntityManager();
        em.refresh(entity);
        return entity;
    }

    /**
     * Agrega una entidad no administrada al contexto de persistencia.
     *
     * @param entity Objeto a ser agregado.
     */
    @Override
    public T merge(T entity) {
        EntityManager em = getEntityManager();
        em.merge(entity);
        return entity;
    }

    /**
     * Obtiene la información de una entidad respecto a un Identificador.
     *
     * @param id Identificador de la entidad solicitada.
     * @return Objeto asociado al Identificador.
     */
    @Override
    public T find(Object id) {
        EntityManager em = getEntityManager();
        T entity = em.find(entityClass, id);
        return entity;
    }

    /**
     * Obtiene la informacion de una o varias entidad que concuerden con
     * criterios avanzados de busqueda.
     *
     * @param criteria Criterios avanzados de busqueda que permite la inclusion
     * de filtros y predicados de ordenacion de atributos.
     * @return Lista de objetos que coinciden con los criterios de busqueda.
     */
    @Override
    public List<T> find(QueryCriteria criteria) {
        EntityManager em = getEntityManager();

        // Seleccion.
        javax.persistence.criteria.CriteriaBuilder qb = em.getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = qb.createQuery();
        javax.persistence.criteria.Root<T> root = cq.from(entityClass);
        cq.select(root);

        // Filtro.
        if (!Assertions.isNullOrEmpty(criteria.getFilters())) {
            javax.persistence.criteria.Predicate filterBy = buildWhereClause(qb, root, criteria);
            cq.where(filterBy);
        }

        // Ordenacion
        if (!Assertions.isNullOrEmpty(criteria.getSorts())) {
            List<javax.persistence.criteria.Order> orderBy = buildeOrderClause(qb, root, criteria.getSorts());
            cq.orderBy(orderBy);
        }

        // Preparacion de la consulta.
        javax.persistence.Query q = em.createQuery(cq);

        // Limite de cantidad de registros.
        if (criteria.getLimit() != null) {
            q.setMaxResults(criteria.getLimit());
        }

        // Omision de registros.
        if (criteria.getOffset() != null) {
            q.setFirstResult(criteria.getOffset());
        }

        // Hints.
        if (!Assertions.isNullOrEmpty(criteria.getHints())) {
            Map<String, Object> hints = criteria.getHints();
            for (Map.Entry<String, Object> entry : hints.entrySet()) {
                q.setHint(entry.getKey(), entry.getValue());
            }
        }

        // Ejecucion de la consulta.
        q.setHint("org.hibernate.cacheMode", CacheMode.REFRESH);
        return q.getResultList();
    }

    /**
     * Obtiene la lista completa de registros en una tabla.
     *
     * @return Lista de registros.
     */
    @Override
    public List<T> findAll() {
        EntityManager em = getEntityManager();
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = em.createQuery(cq);
        return q.getResultList();
    }

    /**
     * Obtiene la informacion de una entidad que coincida con criterios
     * avanzados de busqueda.
     *
     * @param criteria Criterios avanzados de busqueda que permite la inclusion
     * de filtros y predicados de ordenacion de atributos.
     * @return Objeto que coincide con los criterios de busqueda.
     */
    @Override
    public T findFirst(QueryCriteria criteria) {
        try {
            List<T> elements = find(criteria);
            if (elements == null || elements.isEmpty()) {
                return null;
            } else {
                return elements.get(0);
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Ejecuta una query construida arbitrariamente y transforma el conjunto de
     * resultados a una lista de objetos idealmente mas manejables.
     *
     * @param sql Query a ejecutar.
     * @return Coleccion de objetos que encapsulan la informacion de las
     * diferentes filas encontradas.
     */
    public Collection<Map<String, Object>> select(String sql) {
        EntityManager em = getEntityManager();

        // Ejecutar la query e indicar que necesitamos mapear el resultset a un mapa, valga la redundancia.
        org.hibernate.Query query = em.createNativeQuery(sql)
                .unwrap(org.hibernate.Query.class)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        return (Collection<Map<String, Object>>) query.list();
    }

    /**
     * Ejecuta una query construida arbitrariamente y transforma el conjunto de
     * resultados a una lista de objetos idealmente mas manejables.
     *
     * @param sql Query a ejecutar.
     * @param mapper Mapeador de objetos.
     * @return Coleccion de objetos que encapsulan la informacion de las
     * diferentes filas encontradas.
     */
    public Collection<T> select(String sql, ResultSetMapper<T> mapper) {
        // Ejecutar la query y obtener la coleccion de mapas que representan el resultset.
        Collection<Map<String, Object>> maps = select(sql);

        // Mapear cada registro a un objeto.
        Collection<T> objects = new LinkedList<>();
        for (Map<String, Object> map : maps) {
            T obj = mapper.adapt(new ResultSetMap(map));
            if (obj != null) {
                objects.add(obj);
            }
        }
        return objects;
    }

    /**
     * Obtiene un entero que representa la cantidad total de registros en una
     * tabla.
     *
     * @return Cantidad de registros en una tabla.
     */
    @Override
    public Integer count() {
        EntityManager em = getEntityManager();
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(em.getCriteriaBuilder().count(rt));
        javax.persistence.Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    /**
     * Obtiene un entero que representa la cantidad total de registros en una
     * tabla utilizando criterios avanzadas de busqueda.
     *
     * @param criteria Criterios avanzados de busqueda que permite la inclusion
     * de filtros y predicados de ordenacion de atributos.
     * @return Cantidad de registros en una tabla.
     */
    @Override
    public Integer count(QueryCriteria criteria) {
        EntityManager em = getEntityManager();

        // Seleccion.
        javax.persistence.criteria.CriteriaBuilder qb = em.getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = qb.createQuery(Long.class);
        javax.persistence.criteria.Root<T> root = cq.from(entityClass);
        cq.select(qb.count(root));

        // Filtro.
        if (!Assertions.isNullOrEmpty(criteria.getFilters())) {
            javax.persistence.criteria.Predicate predicate = buildWhereClause(qb, root, criteria);
            cq.where(predicate);
        }

        // Preparacion de la consulta.
        javax.persistence.Query q = em.createQuery(cq);

        // Hints.
        if (!Assertions.isNullOrEmpty(criteria.getHints())) {
            Map<String, Object> hints = criteria.getHints();
            for (Map.Entry<String, Object> entry : hints.entrySet()) {
                q.setHint(entry.getKey(), entry.getValue());
            }
        }

        // Ejecucion de consulta.
        return ((Long) q.getSingleResult()).intValue();
    }

    /**
     * Metodo de ayuda para generar clausulas dinamicas WHERE.
     *
     * @param cb Criteria builder.
     * @param root Root de la entidad a consultar.
     * @param criteria Coleccion de criterios de filtrado.
     * @return Predicado de clausula WHERE compuesto de diferentes predicados
     * separados por el operador OR.
     */
    protected javax.persistence.criteria.Predicate buildWhereClause(javax.persistence.criteria.CriteriaBuilder cb, javax.persistence.criteria.Root<T> root, QueryCriteria criteria) {
        javax.persistence.criteria.Predicate predicate = cb.and();
        Collection<Condition> filters = criteria.getFilters();
        for (Condition filter : filters) {
            Operator operator = filter.getOperator();
            Object value = filter.getRightExpression();
            javax.persistence.criteria.Path<?> path = resolvePath(root, filter.getLeftExpression());
            if (path != null) {
                switch (operator) {
                    case IN:
                        if (value instanceof Object[]) {
                            predicate = cb.and(predicate, path.in((Object[]) value));
                        }
                        break;
                    case LIKE:
                        predicate = cb.and(predicate, cb.like((Path<String>) path, '%' + String.valueOf(value) + '%'));
                        break;
                    case EQUALS:
                        predicate = cb.and(predicate, cb.equal(path, value));
                        break;
                    case NOT_EQUALS:
                        predicate = cb.and(predicate, cb.notEqual(path, value));
                        break;
                    case GREATER_EQUAL:
                        predicate = cb.and(predicate, cb.greaterThanOrEqualTo((javax.persistence.criteria.Path) path, (javax.persistence.criteria.Expression) cb.literal(value)));
                        break;
                    case GREATER:
                        predicate = cb.and(predicate, cb.greaterThan((javax.persistence.criteria.Path) path, (javax.persistence.criteria.Expression) cb.literal(value)));
                        break;
                    case LESS_EQUAL:
                        predicate = cb.and(predicate, cb.lessThanOrEqualTo((javax.persistence.criteria.Path) path, (javax.persistence.criteria.Expression) cb.literal(value)));
                        break;
                    case LESS:
                        predicate = cb.and(predicate, cb.lessThan((javax.persistence.criteria.Path) path, (javax.persistence.criteria.Expression) cb.literal(value)));
                        break;
                }
            }
        }
        return predicate;
    }

    /**
     * Metodo de ayuda para generar una clausula dinamicas ORDER BY.
     *
     * @param cb Criteria builder.
     * @param root Root de la entidad a consultar.
     * @param sorts Citerios de ordenacion para la clausula ORDER BY.
     * @return Predicado de clausula ORDER BY.
     */
    protected List<javax.persistence.criteria.Order> buildeOrderClause(javax.persistence.criteria.CriteriaBuilder cb, javax.persistence.criteria.Root<T> root, Collection<Sorting> sorts) {
        List<javax.persistence.criteria.Order> order = new LinkedList();
        for (Sorting sort : sorts) {
            OrderKind sortOrder = sort.getOrder();
            if (null != sortOrder) {
                javax.persistence.criteria.Path<?> sortField = resolvePath(root, sort.getExpression());
                switch (sortOrder) {
                    case DESCENDING:
                        // Orden descendente.
                        order.add(cb.desc(sortField));
                        break;
                    case ASCENDING:
                        // Orden ascendente.
                        order.add(cb.asc(sortField));
                        break;
                    default:
                        break;
                }
            }
        }
        return order;
    }

    protected javax.persistence.criteria.Path resolvePath(javax.persistence.criteria.Root root, String field) {
        Path path = null;
        /**
         * Se debe utilizar 'Pattern.quote' ya que el caracter de punto (.) es
         * un caracter especial para las expresiones regulares.
         */
        String[] parts = field.split(Pattern.quote("."));
        for (String part : parts) {
            if (path == null) {
                path = root.get(part);
            } else {
                path = path.get(part);
            }
        }
        return path;
    }

}
