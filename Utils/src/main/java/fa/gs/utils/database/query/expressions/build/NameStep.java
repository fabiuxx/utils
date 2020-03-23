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
public interface NameStep<T extends NameStep<T>> {

    T name(String... parts);

    T name(Name name);

}
