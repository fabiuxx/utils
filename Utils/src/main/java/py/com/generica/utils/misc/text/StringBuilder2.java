/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.generica.utils.misc.text;

/**
 *
 * @author Fabio A. González Sosa
 */
public class StringBuilder2 {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Ya que no se puede hererdar de la clase StringBuilder, se realiza una
     * composicion utilizando una instancia de dicha clase.
     */
    private final StringBuilder builder;
    //</editor-fold>

    /**
     * Constructor.
     */
    public StringBuilder2() {
        this(new StringBuilder());
    }

    /**
     * Constructor.
     *
     * @param builder Cadena construida de manera externa.
     */
    public StringBuilder2(StringBuilder builder) {
        this.builder = builder;
    }

    //<editor-fold defaultstate="collapsed" desc="Wrappers de metodos existentes en StringBuilder">
    public StringBuilder2 append(Object obj) {
        builder.append(obj);
        return this;
    }

    public StringBuilder2 append(String str) {
        builder.append(str);
        return this;
    }

    public StringBuilder2 append(StringBuffer sb) {
        builder.append(sb);
        return this;
    }

    public StringBuilder2 append(CharSequence s) {
        builder.append(s);
        return this;
    }

    public StringBuilder2 append(CharSequence s, int start, int end) {
        builder.append(s, start, end);
        return this;
    }

    public StringBuilder2 append(char[] str) {
        builder.append(str);
        return this;
    }

    public StringBuilder2 append(char[] str, int offset, int len) {
        builder.append(str, offset, len);
        return this;
    }

    public StringBuilder2 append(boolean b) {
        builder.append(b);
        return this;
    }

    public StringBuilder2 append(char c) {
        builder.append(c);
        return this;
    }

    public StringBuilder2 append(int i) {
        builder.append(i);
        return this;
    }

    public StringBuilder2 append(long lng) {
        builder.append(lng);
        return this;
    }

    public StringBuilder2 append(float f) {
        builder.append(f);
        return this;
    }

    public StringBuilder2 append(double d) {
        builder.append(d);
        return this;
    }
    //</editor-fold>

    /**
     * Permite agregar un texto formateado.
     *
     * @param fmt Formato del texto.
     * @param args Parametros de formato.
     * @return Esta misma instancia.
     */
    public StringBuilder2 append(String fmt, Object... args) {
        return append(true, fmt, args);
    }

    /**
     * Permite agregar un texto formateado de manera condicional.
     *
     * @param flag Indica si el texto debe ser agregado.
     * @param fmt Formato del texto.
     * @param args Parametros de formato.
     * @return Esta misma instancia.
     */
    public StringBuilder2 append(boolean flag, String fmt, Object... args) {
        if (flag) {
            if (args == null || args.length == 0) {
                return append(fmt);
            } else {
                return append(String.format(fmt, args));
            }
        }
        return this;
    }

    /**
     * Permite agregar un texto formateado y posterior a eso agrega una nueva
     * linea.
     *
     * @param fmt Formato del texto.
     * @param args Parametros de formato.
     * @return Esta misma instancia.
     */
    public StringBuilder2 appendln(String fmt, Object... args) {
        append(fmt, args);
        append("\n");
        return this;
    }

    /**
     * Permite agregar un texto formateado de manera condicional y posterior a
     * eso agrega una nueva linea.
     *
     * @param flag Indica si el texto debe ser agregado.
     * @param fmt Formato del texto.
     * @param args Parametros de formato.
     * @return Esta misma instancia.
     */
    public StringBuilder2 appendln(boolean flag, String fmt, Object... args) {
        if (flag) {
            append(fmt, args);
            append("\n");
        }
        return this;
    }

    @Override
    public String toString() {
        return builder.toString();
    }

}
