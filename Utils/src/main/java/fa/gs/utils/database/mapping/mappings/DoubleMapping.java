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
public class DoubleMapping extends AbstractMapping<Double> {

    public DoubleMapping(String symbolName, Double fallback) {
        super(symbolName, fallback);
    }

    @Override
    public Class<Double> type() {
        return Double.class;
    }

}
