/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.collections.trees;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Data;

/**
 *
 * @author Fabio A. González Sosa
 * @param <T> Parametro de tipo.
 */
@Data
public class Nodo<T extends Serializable> implements Serializable {

    private T valor;
    private final Collection<Nodo<T>> hijos;

    public Nodo(T valor) {
        this.valor = valor;
        this.hijos = new ArrayList<>();
    }

    public void add(Nodo<T> hijo) {
        this.hijos.add(hijo);
    }

}
