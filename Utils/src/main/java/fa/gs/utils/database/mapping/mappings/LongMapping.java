/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.mapping.mappings;

/**
 *
 * @author Fabio A. González Sosa
 */
public class LongMapping extends AbstractMapping<Long> {

    public LongMapping(String symbolName, Long fallback) {
        super(symbolName, fallback);
    }

    @Override
    public Class<Long> type() {
        return Long.class;
    }

}
