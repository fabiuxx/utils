/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.tasks;

import fa.gs.utils.result.simple.Result;
import java.io.Serializable;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface DbTask<TInput, TOutput> extends Serializable {

    Result<TOutput> execute(TInput input);

}
