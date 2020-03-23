/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.query.expressions.build;

import fa.gs.utils.database.query.expressions.names.Name;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public interface ProjectionStep<T extends ProjectionStep<T>> extends NameStep<T> {

    T all(String... parts);

    T all(Name name);

    T as(String alias);

    T as(Name name);

}
