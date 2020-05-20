/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.annotations;

import fa.gs.utils.database.dto.mapping.QueryResultSetAdapter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Fabio A. González Sosa
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FgQueryResultSetAdapter {

    /**
     * Estrategia de mapeo de consultas nativas.
     *
     * @return Estrategia de mapeo de consultas nativas a utilizar.
     */
    Class<? extends QueryResultSetAdapter> adapter() default QueryResultSetAdapter.class;

}
