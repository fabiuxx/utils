/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.tests.database;

import fa.gs.utils.database.dto.FgDto;
import fa.gs.utils.database.dto.FgJoin;
import fa.gs.utils.database.dto.FgProjection;
import fa.gs.utils.database.query.expressions.JoinExpression;

/**
 *
 * @author Fabio A. González Sosa
 */
@FgDto(table = "info.persona_email", as = "pe")
@FgJoin(type = JoinExpression.Type.LEFT, table = "info.persona", as = "p", on = "pe.id_persona = p.id")
@FgJoin(type = JoinExpression.Type.LEFT, table = "info.email", as = "e", on = "pe.id_email = e.id")
public class PersonaEmail {

    @FgProjection(name = "pe.id_email")
    public Integer idEmail;

    @FgProjection(name = "pe.id_persona")
    public Integer idPersona;
}
