/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.tasks;

import fa.gs.utils.result.simple.Result;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class DbValueGenerationTask<T> implements DbTask<Void, Void> {

    @Override
    public Result<Void> execute(Void input) {
        return generarValores();
    }

    protected abstract Result<Void> generarValores();

}
