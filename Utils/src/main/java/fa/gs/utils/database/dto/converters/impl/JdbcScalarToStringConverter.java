/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto.converters.impl;

/**
 *
 * @author Fabio A. González Sosa
 */
public class JdbcScalarToStringConverter extends SimpleJdbcScalarCastConverter<String> {

    public JdbcScalarToStringConverter() {
        super(String.class);
    }

}
