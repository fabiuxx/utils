/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.database.dto;

import fa.gs.utils.misc.Assertions;
import fa.gs.utils.misc.Reflection;

/**
 *
 * @author Fabio A. González Sosa
 */
public class AnnotationTypes {

    public static Class<FgDto> FGDTO = FgDto.class;
    public static Class<FgProjection> FGPROJECTION = FgProjection.class;
    public static Class<FgJoin> FGJOIN = FgJoin.class;
    public static Class<FgJoins> FGJOINS = FgJoins.class;
    public static Class<FgWhere> FGWHERE = FgWhere.class;
    public static Class<FgWheres> FGWHERES = FgWheres.class;
    public static Class<FgHaving> FGHAVING = FgHaving.class;
    public static Class<FgHavings> FGHAVINGS = FgHavings.class;
    public static Class<FgOrderBy> FGORDERBY = FgOrderBy.class;
    public static Class<FgOrderBys> FGORDERBYS = FgOrderBys.class;
    public static Class<FgGroupBy> FGGROUPBY = FgGroupBy.class;
    public static Class<FgGroupBys> FGGROUPBYS = FgGroupBys.class;

    public static FgJoin[] getAllJoins(Class klass) {
        FgJoins collector = Reflection.getAnnotation(klass, FGJOINS);
        if (collector != null && Assertions.isNullOrEmpty(collector.value()) == false) {
            return collector.value();
        }

        FgJoin single = Reflection.getAnnotation(klass, FGJOIN);
        if (single != null) {
            return new FgJoin[]{single};
        }

        return null;
    }

    public static FgWhere[] getAllWheres(Class klass) {
        FgWheres collector = Reflection.getAnnotation(klass, FGWHERES);
        if (collector != null && Assertions.isNullOrEmpty(collector.value()) == false) {
            return collector.value();
        }

        FgWhere single = Reflection.getAnnotation(klass, FGWHERE);
        if (single != null) {
            return new FgWhere[]{single};
        }

        return null;
    }

    public static FgGroupBy[] getAllGroupBys(Class klass) {
        FgGroupBys collector = Reflection.getAnnotation(klass, FGGROUPBYS);
        if (collector != null && Assertions.isNullOrEmpty(collector.value()) == false) {
            return collector.value();
        }

        FgGroupBy single = Reflection.getAnnotation(klass, FGGROUPBY);
        if (single != null) {
            return new FgGroupBy[]{single};
        }

        return null;
    }

    public static FgHaving[] getAllHavings(Class klass) {
        FgHavings collector = Reflection.getAnnotation(klass, FGHAVINGS);
        if (collector != null && Assertions.isNullOrEmpty(collector.value()) == false) {
            return collector.value();
        }

        FgHaving single = Reflection.getAnnotation(klass, FGHAVING);
        if (single != null) {
            return new FgHaving[]{single};
        }

        return null;
    }

    public static FgOrderBy[] getAllOrderBys(Class klass) {
        FgOrderBys collector = Reflection.getAnnotation(klass, FGORDERBYS);
        if (collector != null && Assertions.isNullOrEmpty(collector.value()) == false) {
            return collector.value();
        }

        FgOrderBy single = Reflection.getAnnotation(klass, FGORDERBY);
        if (single != null) {
            return new FgOrderBy[]{single};
        }

        return null;
    }

}
