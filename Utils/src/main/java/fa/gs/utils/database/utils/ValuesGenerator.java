/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.utils;

import fa.gs.utils.result.simple.Result;
import fa.gs.utils.result.simple.Results;
import java.util.Collection;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class ValuesGenerator<T, V> {

    public abstract Collection<T> generarValores();

    protected abstract Result<T> obtenerValor(V valor, Object... args);

    protected abstract Result<T> crearValor(V valor, Object... args);

    protected boolean puedeModificar(T valorExistente, V valorFijo, Object... args) {
        return false;
    }

    protected Result<T> modificarValor(T valorExistente, V valorFijo, Object... args) {
        return Results.ok()
                .value(valorExistente)
                .build();
    }

    /**
     * Obtiene o crea un valor concreto para un valor fijo dado.
     *
     * @param valorFijo Valor fijo.
     * @param args Parametros adicionales.
     * @return Valor concreto.
     */
    protected final T obtenerOCrearValor(V valorFijo, Object... args) {
        T valor;

        try {
            // Verificar si ya existe el valor concreto.
            Result<T> resValor = obtenerValor(valorFijo, args);
            resValor.raise(true);

            // Verificar si el valor debe ser modificado.
            final T valorExistente = resValor.value();
            if (puedeModificar(valorExistente, valorFijo, args)) {
                resValor = modificarValor(valorExistente, valorFijo, args);
                resValor.raise(true);
            }

            valor = resValor.value();
        } catch (Throwable thr) {
            valor = null;
        }

        // Control de corte.
        if (valor != null) {
            return valor;
        }

        // Crear el valor concreto si aun no existe.
        try {
            Result<T> resValor = crearValor(valorFijo, args);
            resValor.raise(true);
            valor = resValor.value();
        } catch (Throwable thr) {
            valor = null;
        }

        return valor;
    }

}
