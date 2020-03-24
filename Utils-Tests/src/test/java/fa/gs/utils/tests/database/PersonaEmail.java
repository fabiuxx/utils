/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests.database;

import fa.gs.utils.database.dto.FgDto;
import fa.gs.utils.database.dto.FgJoin;
import fa.gs.utils.database.dto.FgOrderBy;
import fa.gs.utils.database.dto.FgProjection;
import fa.gs.utils.database.dto.FgWhere;
import fa.gs.utils.database.query.expressions.JoinExpression;
import java.io.Serializable;

/**
 *
 * @author Fabio A. González Sosa
 */
@FgDto(table = "info.persona_email", as = "pe")
@FgJoin(type = JoinExpression.Type.LEFT, table = "info.persona", as = "p", on = "pe.id_persona = p.id")
@FgJoin(type = JoinExpression.Type.LEFT, table = "info.email", as = "e", on = "pe.id_email = e.id")
@FgWhere(value = "3 = 3")
@FgOrderBy(value = PersonaEmail.FILTERS.ID_PERSONA)
public class PersonaEmail implements Serializable {

    @FgProjection(value = FILTERS.ID_EMAIL)
    public Integer idEmail;

    @FgProjection(value = FILTERS.ID_PERSONA)
    public Integer idPersona;

    @FgProjection(value = FILTERS.ID_EMAIL, as = "x", converter = EnumTestConverter.class)
    public EnumTest enumTest;

    public static final class FILTERS {

        public static final String ID_EMAIL = "pe.id_email";
        public static final String ID_PERSONA = "pe.id_persona";
    }

}
