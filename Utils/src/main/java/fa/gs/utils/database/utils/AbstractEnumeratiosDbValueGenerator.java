/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.utils;

import fa.gs.utils.result.simple.Result;
import fa.gs.utils.result.simple.Results;

/**
 *
 * @author Fabio A. González Sosa
 * @param <TEnum> Parametro de tipo.
 * @param <TDomain> Parametro de tipo.
 */
public abstract class AbstractEnumeratiosDbValueGenerator<TEnum extends Enum<TEnum>, TDomain> implements DbValueGenerator {

    private final Class<TEnum> enumClass;

    protected AbstractEnumeratiosDbValueGenerator(Class<TEnum> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public Result<Void> generarValores() {
        Result<Void> result;

        try {
            for (TEnum enumConstant : enumClass.getEnumConstants()) {
                TDomain valorExistente = obtenerValorExistente(enumConstant);
                if (valorExistente != null) {
                    boolean puedeModificarse = valorExistentePuedeModificarse(enumConstant, valorExistente);
                    if (puedeModificarse) {
                        modificar(enumConstant, valorExistente);
                    }
                } else {
                    crear(enumConstant);
                }
            }

            result = Results.ok()
                    .nullable(true)
                    .build();
        } catch (Throwable thr) {
            result = Results.ko()
                    .cause(thr)
                    .build();
        }

        return result;
    }

    protected abstract TDomain obtenerValorExistente(TEnum enumConstant) throws Throwable;

    protected abstract boolean valorExistentePuedeModificarse(TEnum enumConstant, TDomain valorExistente);

    protected abstract void modificar(TEnum enumConstant, TDomain valorExistente) throws Throwable;

    protected abstract void crear(TEnum enumConstant) throws Throwable;

}
