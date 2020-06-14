/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.utils;

import fa.gs.utils.database.dto.DialectProvider;
import fa.gs.utils.database.dto.facade.AbstractDtoFacade;
import fa.gs.utils.database.dto.facade.DtoFacade;
import fa.gs.utils.database.jpa.EntityManagerProvider;
import fa.gs.utils.database.jpa.facade.AbstractEntityFacade;
import fa.gs.utils.database.jpa.facade.EntityFacade;
import fa.gs.utils.database.query.Dialect;
import fa.gs.utils.database.query.commands.CountQuery;
import fa.gs.utils.database.query.commands.SelectQuery;
import fa.gs.utils.result.simple.Result;
import javax.persistence.EntityManager;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public abstract class AbstractMixedFacade<T> implements EntityFacade<T>, DtoFacade<T>, EntityManagerProvider, DialectProvider {

    private final EntityFacade<T> entityFacadeImpl;

    private final DtoFacade<T> dtoFacadeImpl;

    /**
     * Constructor.
     *
     * @param domainClass Clase que esta anotada como entidad y como dto.
     */
    protected AbstractMixedFacade(final Class<T> domainClass) {
        this.entityFacadeImpl = new AbstractEntityFacade<T>(domainClass) {
            @Override
            public EntityManager getEntityManager() {
                return AbstractMixedFacade.this.getEntityManager();
            }
        };

        this.dtoFacadeImpl = new AbstractDtoFacade<T>(domainClass) {
            @Override
            public Dialect getDialect() {
                return AbstractMixedFacade.this.getDialect();
            }

            @Override
            public EntityManager getEntityManager() {
                return AbstractMixedFacade.this.getEntityManager();
            }
        };
    }

    //<editor-fold defaultstate="collapsed" desc="Implementacion DtoFacade">
    @Override
    public Class<T> getDtoClass() {
        return dtoFacadeImpl.getDtoClass();
    }

    @Override
    public Result<Long> count(String query) {
        return dtoFacadeImpl.count(query);
    }

    @Override
    public Result<T[]> selectAll(String query) {
        return dtoFacadeImpl.selectAll(query);
    }

    @Override
    public Result<T> selectFirst(String query) {
        return dtoFacadeImpl.selectFirst(query);
    }

    @Override
    public Result<Long> count(CountQuery query) {
        return dtoFacadeImpl.count(query);
    }

    @Override
    public Result<T[]> selectAll(SelectQuery query) {
        return dtoFacadeImpl.selectAll(query);
    }

    @Override
    public Result<T> selectFirst(SelectQuery query) {
        return dtoFacadeImpl.selectFirst(query);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Implementacion EntityFacade">
    @Override
    public Class<T> getEntityClass() {
        return entityFacadeImpl.getEntityClass();
    }

    @Override
    public void attach(T entity) {
        entityFacadeImpl.attach(entity);
    }

    @Override
    public void detach(T entity) {
        entityFacadeImpl.detach(entity);
    }

    @Override
    public T create(T entity) {
        return entityFacadeImpl.create(entity);
    }

    @Override
    public T edit(T entity) {
        return entityFacadeImpl.edit(entity);
    }

    @Override
    public void remove(T entity) {
        entityFacadeImpl.remove(entity);
    }
    //</editor-fold>

}
