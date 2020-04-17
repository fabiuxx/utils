/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.facade;

import fa.gs.utils.database.dto.DtoMapper;
import fa.gs.utils.database.jpa.Jpa;
import fa.gs.utils.database.query.commands.CountQuery;
import fa.gs.utils.misc.Assertions;
import fa.gs.utils.result.simple.Result;
import fa.gs.utils.result.simple.Results;
import javax.persistence.EntityManager;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class AbstractDtoFacade<T> implements DtoFacade<T> {

    /**
     * Clase del DTO asociado a la fachada.
     */
    protected final Class<T> dtoClass;

    /**
     * Constructor.
     *
     * @param dtoClass Claso de DTO.
     */
    protected AbstractDtoFacade(Class<T> dtoClass) {
        this.dtoClass = dtoClass;
    }

    /**
     * Obtiene la referencia a la clase del DTO asociado.
     *
     * @return Clase del DTO asociado a la facahada.
     */
    @Override
    public Class<T> getDtoClass() {
        return dtoClass;
    }

    /**
     * Obtiene una instancia del administrador de entidades de la capa de
     * persistencia.
     *
     * @return Referencia al administrador de entidades de la capa de
     * persistencia.
     */
    public abstract EntityManager getEntityManager();

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

}
