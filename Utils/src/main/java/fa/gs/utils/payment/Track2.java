/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.payment;

import fa.gs.utils.misc.Assertions;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Track2 implements Serializable {

    private static final Pattern track2Pattern0 = Pattern.compile("(;([0-9]{1,19})=([0-9]{4})([0-9]{3})(.*)\\?)");

    // Similar al regex anterior solo que omite los caracteres sentinelas del comienzo.
    private static final Pattern track2Pattern1 = Pattern.compile("(([0-9]{1,19})=([0-9]{4})([0-9]{3})(.*))");

    /**
     * Valor en bruto.
     */
    private String raw;

    /**
     * Primary Account Number.
     */
    private String pan;

    /**
     * Fecha de expiracion en formato <code>YYMM</code>.
     */
    private String exp;

    /**
     * Codigo de Servicio.
     */
    private String svc;

    /**
     * Datos discrecionales.
     */
    private String dsc;

    private Track2() {
        this.raw = null;
        this.pan = null;
        this.exp = null;
        this.svc = null;
        this.dsc = null;
    }

    public static boolean accepts(String text) {
        boolean a = accepts(track2Pattern0, text);
        boolean b = accepts(track2Pattern1, text);
        return a || b;
    }

    private static boolean accepts(Pattern pattern, String text) {
        if (Assertions.stringNullOrEmpty(text)) {
            return false;
        }

        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    public static Track2 parse(String text) {
        if (accepts(track2Pattern0, text)) {
            return parse(track2Pattern0, text);
        }

        if (accepts(track2Pattern1, text)) {
            return parse(track2Pattern1, text);
        }

        return null;
    }

    private static Track2 parse(Pattern pattern, String text) {
        Track2 track2 = new Track2();

        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            track2.raw = getGroup(matcher, 1);
            track2.pan = getGroup(matcher, 2);
            track2.exp = getGroup(matcher, 3);
            track2.svc = getGroup(matcher, 4);
            track2.dsc = getGroup(matcher, 5);
        }

        return track2;
    }

    private static String getGroup(final Matcher matcher, final int group) {
        final int groupCount = matcher.groupCount();
        if (groupCount > group - 1) {
            return matcher.group(group);
        } else {
            return null;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getExpiration() {
        return exp;
    }

    public void setExpiration(String exp) {
        this.exp = exp;
    }

    public String getServiceCode() {
        return svc;
    }

    public void setServiceCode(String svc) {
        this.svc = svc;
    }

    public String getDiscretionaryData() {
        return dsc;
    }

    public void setDiscretionaryData(String dsc) {
        this.dsc = dsc;
    }
    //</editor-fold>

    @Override
    public String toString() {
        return "Track2{" + "raw=" + raw + ", pan=" + pan + ", exp=" + exp + ", svc=" + svc + ", cvv=" + dsc + '}';
    }

}
