/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.mapping.mappings;

import fa.gs.utils.database.mapping.Mapping;
import fa.gs.utils.database.mapping.MappingSymbol;

/**
 *
 * @author Fabio A. González Sosa
 */
public abstract class AbstractMapping<T> implements Mapping<T> {

    private final MappingSymbol symbol;

    private final T fallback;

    AbstractMapping(String symbolName, T fallback) {
        this.symbol = new MappingSymbolImpl(symbolName);
        this.fallback = fallback;
    }

    @Override
    public MappingSymbol symbol() {
        return symbol;
    }

    @Override
    public T fallback() {
        return fallback;
    }

    private static class MappingSymbolImpl implements MappingSymbol {

        private final String name;

        public MappingSymbolImpl(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

    }

}
