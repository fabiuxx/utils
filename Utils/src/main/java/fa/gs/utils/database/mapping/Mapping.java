/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.mapping;

import java.io.Serializable;

/**
 *
 * @author Fabio A. González Sosa
 */
public interface Mapping<T> extends Serializable {

    MappingSymbol symbol();

    Class<T> type();

    T fallback();

}
