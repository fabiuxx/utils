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
public class FloatMapping extends AbstractMapping<Float> {

    public FloatMapping(String symbolName, Float fallback) {
        super(symbolName, fallback);
    }

    @Override
    public Class<Float> type() {
        return Float.class;
    }

}
