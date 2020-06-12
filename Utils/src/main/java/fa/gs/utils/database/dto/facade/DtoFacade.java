/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.facade;

import fa.gs.utils.database.query.commands.CountQuery;
import fa.gs.utils.database.query.commands.SelectQuery;
import fa.gs.utils.result.simple.Result;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
public interface DtoFacade<T> {

    Class<T> getDtoClass();

    Result<Long> count(CountQuery query);

    Result<T[]> selectAll(SelectQuery query);

    Result<T> selectFirst(SelectQuery query);

}
