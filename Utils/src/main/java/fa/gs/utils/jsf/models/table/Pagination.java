/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf.models.table;

import java.io.Serializable;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Pagination implements Serializable {

    public static final Integer DEFAULT_LIMIT = 25;

    private final Integer limit;
    private final Integer offset;

    private Pagination(Integer limit, Integer offset) {
        this.limit = limit;
        this.offset = offset;
    }

    public static Pagination fromPages(Integer page, Integer pageSize) {
        // Limite, puede ser nulo si no se define un offset.
        Integer limit = pageSize;

        // Offset, varia de acuerdo al valor del limite.
        Integer offset = null;
        if (page != null) {
            if (limit == null) {
                limit = DEFAULT_LIMIT;
            }
            offset = Math.max(0, page - 1) * limit;
        }

        return fromLimits(limit, offset);
    }

    public static Pagination fromLimits(Integer limit, Integer offset) {
        return new Pagination(limit, offset);
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return offset;
    }
    //</editor-fold>

}
