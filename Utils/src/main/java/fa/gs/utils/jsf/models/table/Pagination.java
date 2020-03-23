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

    public static final Long DEFAULT_LIMIT = 25L;

    private final Long limit;
    private final Long offset;

    private Pagination(Long limit, Long offset) {
        this.limit = limit;
        this.offset = offset;
    }

    public static Pagination fromPages(Long page, Long pageSize) {
        // Limite, puede ser nulo si no se define un offset.
        Long limit = pageSize;

        // Offset, varia de acuerdo al valor del limite.
        Long offset = null;
        if (page != null) {
            if (limit == null) {
                limit = DEFAULT_LIMIT;
            }
            offset = Math.max(0, page - 1) * limit;
        }

        return fromLimits(limit, offset);
    }

    public static Pagination fromLimits(Long limit, Long offset) {
        return new Pagination(limit, offset);
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public Long getLimit() {
        return limit;
    }

    public Long getOffset() {
        return offset;
    }
    //</editor-fold>

}
