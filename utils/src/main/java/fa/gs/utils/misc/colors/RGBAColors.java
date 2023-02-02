/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc.colors;

import fa.gs.utils.collections.Maps;
import java.util.Map;

/**
 * Implementación copiada y adaptada desde libreria iText
 * (<a href="https://itextpdf.com/">ver</a>). Específicamente se toma la
 * implementación de la clase {@code com.itextpdf.kernel.colors.RGBAColors} y se
 * adapta para operar con AWT.
 */
public class RGBAColors {

    public static final Map<String, int[]> WEB_COLORS = Maps.empty();

    static {
        WEB_COLORS.put("aliceblue", new int[]{0xf0, 0xf8, 0xff, 0xff});
        WEB_COLORS.put("antiquewhite", new int[]{0xfa, 0xeb, 0xd7, 0xff});
        WEB_COLORS.put("aqua", new int[]{0x00, 0xff, 0xff, 0xff});
        WEB_COLORS.put("aquamarine", new int[]{0x7f, 0xff, 0xd4, 0xff});
        WEB_COLORS.put("azure", new int[]{0xf0, 0xff, 0xff, 0xff});
        WEB_COLORS.put("beige", new int[]{0xf5, 0xf5, 0xdc, 0xff});
        WEB_COLORS.put("bisque", new int[]{0xff, 0xe4, 0xc4, 0xff});
        WEB_COLORS.put("black", new int[]{0x00, 0x00, 0x00, 0xff});
        WEB_COLORS.put("blanchedalmond", new int[]{0xff, 0xeb, 0xcd, 0xff});
        WEB_COLORS.put("blue", new int[]{0x00, 0x00, 0xff, 0xff});
        WEB_COLORS.put("blueviolet", new int[]{0x8a, 0x2b, 0xe2, 0xff});
        WEB_COLORS.put("brown", new int[]{0xa5, 0x2a, 0x2a, 0xff});
        WEB_COLORS.put("burlywood", new int[]{0xde, 0xb8, 0x87, 0xff});
        WEB_COLORS.put("cadetblue", new int[]{0x5f, 0x9e, 0xa0, 0xff});
        WEB_COLORS.put("chartreuse", new int[]{0x7f, 0xff, 0x00, 0xff});
        WEB_COLORS.put("chocolate", new int[]{0xd2, 0x69, 0x1e, 0xff});
        WEB_COLORS.put("coral", new int[]{0xff, 0x7f, 0x50, 0xff});
        WEB_COLORS.put("cornflowerblue", new int[]{0x64, 0x95, 0xed, 0xff});
        WEB_COLORS.put("cornsilk", new int[]{0xff, 0xf8, 0xdc, 0xff});
        WEB_COLORS.put("crimson", new int[]{0xdc, 0x14, 0x3c, 0xff});
        WEB_COLORS.put("cyan", new int[]{0x00, 0xff, 0xff, 0xff});
        WEB_COLORS.put("darkblue", new int[]{0x00, 0x00, 0x8b, 0xff});
        WEB_COLORS.put("darkcyan", new int[]{0x00, 0x8b, 0x8b, 0xff});
        WEB_COLORS.put("darkgoldenrod", new int[]{0xb8, 0x86, 0x0b, 0xff});
        WEB_COLORS.put("darkgray", new int[]{0xa9, 0xa9, 0xa9, 0xff});
        WEB_COLORS.put("darkgrey", new int[]{0xa9, 0xa9, 0xa9, 0xff});
        WEB_COLORS.put("darkgreen", new int[]{0x00, 0x64, 0x00, 0xff});
        WEB_COLORS.put("darkkhaki", new int[]{0xbd, 0xb7, 0x6b, 0xff});
        WEB_COLORS.put("darkmagenta", new int[]{0x8b, 0x00, 0x8b, 0xff});
        WEB_COLORS.put("darkolivegreen", new int[]{0x55, 0x6b, 0x2f, 0xff});
        WEB_COLORS.put("darkorange", new int[]{0xff, 0x8c, 0x00, 0xff});
        WEB_COLORS.put("darkorchid", new int[]{0x99, 0x32, 0xcc, 0xff});
        WEB_COLORS.put("darkred", new int[]{0x8b, 0x00, 0x00, 0xff});
        WEB_COLORS.put("darksalmon", new int[]{0xe9, 0x96, 0x7a, 0xff});
        WEB_COLORS.put("darkseagreen", new int[]{0x8f, 0xbc, 0x8f, 0xff});
        WEB_COLORS.put("darkslateblue", new int[]{0x48, 0x3d, 0x8b, 0xff});
        WEB_COLORS.put("darkslategray", new int[]{0x2f, 0x4f, 0x4f, 0xff});
        WEB_COLORS.put("darkslategrey", new int[]{0x2f, 0x4f, 0x4f, 0xff});
        WEB_COLORS.put("darkturquoise", new int[]{0x00, 0xce, 0xd1, 0xff});
        WEB_COLORS.put("darkviolet", new int[]{0x94, 0x00, 0xd3, 0xff});
        WEB_COLORS.put("deeppink", new int[]{0xff, 0x14, 0x93, 0xff});
        WEB_COLORS.put("deepskyblue", new int[]{0x00, 0xbf, 0xff, 0xff});
        WEB_COLORS.put("dimgray", new int[]{0x69, 0x69, 0x69, 0xff});
        WEB_COLORS.put("dimgrey", new int[]{0x69, 0x69, 0x69, 0xff});
        WEB_COLORS.put("dodgerblue", new int[]{0x1e, 0x90, 0xff, 0xff});
        WEB_COLORS.put("firebrick", new int[]{0xb2, 0x22, 0x22, 0xff});
        WEB_COLORS.put("floralwhite", new int[]{0xff, 0xfa, 0xf0, 0xff});
        WEB_COLORS.put("forestgreen", new int[]{0x22, 0x8b, 0x22, 0xff});
        WEB_COLORS.put("fuchsia", new int[]{0xff, 0x00, 0xff, 0xff});
        WEB_COLORS.put("gainsboro", new int[]{0xdc, 0xdc, 0xdc, 0xff});
        WEB_COLORS.put("ghostwhite", new int[]{0xf8, 0xf8, 0xff, 0xff});
        WEB_COLORS.put("gold", new int[]{0xff, 0xd7, 0x00, 0xff});
        WEB_COLORS.put("goldenrod", new int[]{0xda, 0xa5, 0x20, 0xff});
        WEB_COLORS.put("gray", new int[]{0x80, 0x80, 0x80, 0xff});
        WEB_COLORS.put("grey", new int[]{0x80, 0x80, 0x80, 0xff});
        WEB_COLORS.put("green", new int[]{0x00, 0x80, 0x00, 0xff});
        WEB_COLORS.put("greenyellow", new int[]{0xad, 0xff, 0x2f, 0xff});
        WEB_COLORS.put("honeydew", new int[]{0xf0, 0xff, 0xf0, 0xff});
        WEB_COLORS.put("hotpink", new int[]{0xff, 0x69, 0xb4, 0xff});
        WEB_COLORS.put("indianred", new int[]{0xcd, 0x5c, 0x5c, 0xff});
        WEB_COLORS.put("indigo", new int[]{0x4b, 0x00, 0x82, 0xff});
        WEB_COLORS.put("ivory", new int[]{0xff, 0xff, 0xf0, 0xff});
        WEB_COLORS.put("khaki", new int[]{0xf0, 0xe6, 0x8c, 0xff});
        WEB_COLORS.put("lavender", new int[]{0xe6, 0xe6, 0xfa, 0xff});
        WEB_COLORS.put("lavenderblush", new int[]{0xff, 0xf0, 0xf5, 0xff});
        WEB_COLORS.put("lawngreen", new int[]{0x7c, 0xfc, 0x00, 0xff});
        WEB_COLORS.put("lemonchiffon", new int[]{0xff, 0xfa, 0xcd, 0xff});
        WEB_COLORS.put("lightblue", new int[]{0xad, 0xd8, 0xe6, 0xff});
        WEB_COLORS.put("lightcoral", new int[]{0xf0, 0x80, 0x80, 0xff});
        WEB_COLORS.put("lightcyan", new int[]{0xe0, 0xff, 0xff, 0xff});
        WEB_COLORS.put("lightgoldenrodyellow", new int[]{0xfa, 0xfa, 0xd2, 0xff});
        WEB_COLORS.put("lightgreen", new int[]{0x90, 0xee, 0x90, 0xff});
        WEB_COLORS.put("lightgray", new int[]{0xd3, 0xd3, 0xd3, 0xff});
        WEB_COLORS.put("lightgrey", new int[]{0xd3, 0xd3, 0xd3, 0xff});
        WEB_COLORS.put("lightpink", new int[]{0xff, 0xb6, 0xc1, 0xff});
        WEB_COLORS.put("lightsalmon", new int[]{0xff, 0xa0, 0x7a, 0xff});
        WEB_COLORS.put("lightseagreen", new int[]{0x20, 0xb2, 0xaa, 0xff});
        WEB_COLORS.put("lightskyblue", new int[]{0x87, 0xce, 0xfa, 0xff});
        WEB_COLORS.put("lightslategray", new int[]{0x77, 0x88, 0x99, 0xff});
        WEB_COLORS.put("lightslategrey", new int[]{0x77, 0x88, 0x99, 0xff});
        WEB_COLORS.put("lightsteelblue", new int[]{0xb0, 0xc4, 0xde, 0xff});
        WEB_COLORS.put("lightyellow", new int[]{0xff, 0xff, 0xe0, 0xff});
        WEB_COLORS.put("lime", new int[]{0x00, 0xff, 0x00, 0xff});
        WEB_COLORS.put("limegreen", new int[]{0x32, 0xcd, 0x32, 0xff});
        WEB_COLORS.put("linen", new int[]{0xfa, 0xf0, 0xe6, 0xff});
        WEB_COLORS.put("magenta", new int[]{0xff, 0x00, 0xff, 0xff});
        WEB_COLORS.put("maroon", new int[]{0x80, 0x00, 0x00, 0xff});
        WEB_COLORS.put("mediumaquamarine", new int[]{0x66, 0xcd, 0xaa, 0xff});
        WEB_COLORS.put("mediumblue", new int[]{0x00, 0x00, 0xcd, 0xff});
        WEB_COLORS.put("mediumorchid", new int[]{0xba, 0x55, 0xd3, 0xff});
        WEB_COLORS.put("mediumpurple", new int[]{0x93, 0x70, 0xdb, 0xff});
        WEB_COLORS.put("mediumseagreen", new int[]{0x3c, 0xb3, 0x71, 0xff});
        WEB_COLORS.put("mediumslateblue", new int[]{0x7b, 0x68, 0xee, 0xff});
        WEB_COLORS.put("mediumspringgreen", new int[]{0x00, 0xfa, 0x9a, 0xff});
        WEB_COLORS.put("mediumturquoise", new int[]{0x48, 0xd1, 0xcc, 0xff});
        WEB_COLORS.put("mediumvioletred", new int[]{0xc7, 0x15, 0x85, 0xff});
        WEB_COLORS.put("midnightblue", new int[]{0x19, 0x19, 0x70, 0xff});
        WEB_COLORS.put("mintcream", new int[]{0xf5, 0xff, 0xfa, 0xff});
        WEB_COLORS.put("mistyrose", new int[]{0xff, 0xe4, 0xe1, 0xff});
        WEB_COLORS.put("moccasin", new int[]{0xff, 0xe4, 0xb5, 0xff});
        WEB_COLORS.put("navajowhite", new int[]{0xff, 0xde, 0xad, 0xff});
        WEB_COLORS.put("navy", new int[]{0x00, 0x00, 0x80, 0xff});
        WEB_COLORS.put("oldlace", new int[]{0xfd, 0xf5, 0xe6, 0xff});
        WEB_COLORS.put("olive", new int[]{0x80, 0x80, 0x00, 0xff});
        WEB_COLORS.put("olivedrab", new int[]{0x6b, 0x8e, 0x23, 0xff});
        WEB_COLORS.put("orange", new int[]{0xff, 0xa5, 0x00, 0xff});
        WEB_COLORS.put("orangered", new int[]{0xff, 0x45, 0x00, 0xff});
        WEB_COLORS.put("orchid", new int[]{0xda, 0x70, 0xd6, 0xff});
        WEB_COLORS.put("palegoldenrod", new int[]{0xee, 0xe8, 0xaa, 0xff});
        WEB_COLORS.put("palegreen", new int[]{0x98, 0xfb, 0x98, 0xff});
        WEB_COLORS.put("paleturquoise", new int[]{0xaf, 0xee, 0xee, 0xff});
        WEB_COLORS.put("palevioletred", new int[]{0xdb, 0x70, 0x93, 0xff});
        WEB_COLORS.put("papayawhip", new int[]{0xff, 0xef, 0xd5, 0xff});
        WEB_COLORS.put("peachpuff", new int[]{0xff, 0xda, 0xb9, 0xff});
        WEB_COLORS.put("peru", new int[]{0xcd, 0x85, 0x3f, 0xff});
        WEB_COLORS.put("pink", new int[]{0xff, 0xc0, 0xcb, 0xff});
        WEB_COLORS.put("plum", new int[]{0xdd, 0xa0, 0xdd, 0xff});
        WEB_COLORS.put("powderblue", new int[]{0xb0, 0xe0, 0xe6, 0xff});
        WEB_COLORS.put("purple", new int[]{0x80, 0x00, 0x80, 0xff});
        WEB_COLORS.put("red", new int[]{0xff, 0x00, 0x00, 0xff});
        WEB_COLORS.put("rosybrown", new int[]{0xbc, 0x8f, 0x8f, 0xff});
        WEB_COLORS.put("royalblue", new int[]{0x41, 0x69, 0xe1, 0xff});
        WEB_COLORS.put("saddlebrown", new int[]{0x8b, 0x45, 0x13, 0xff});
        WEB_COLORS.put("salmon", new int[]{0xfa, 0x80, 0x72, 0xff});
        WEB_COLORS.put("sandybrown", new int[]{0xf4, 0xa4, 0x60, 0xff});
        WEB_COLORS.put("seagreen", new int[]{0x2e, 0x8b, 0x57, 0xff});
        WEB_COLORS.put("seashell", new int[]{0xff, 0xf5, 0xee, 0xff});
        WEB_COLORS.put("sienna", new int[]{0xa0, 0x52, 0x2d, 0xff});
        WEB_COLORS.put("silver", new int[]{0xc0, 0xc0, 0xc0, 0xff});
        WEB_COLORS.put("skyblue", new int[]{0x87, 0xce, 0xeb, 0xff});
        WEB_COLORS.put("slateblue", new int[]{0x6a, 0x5a, 0xcd, 0xff});
        WEB_COLORS.put("slategray", new int[]{0x70, 0x80, 0x90, 0xff});
        WEB_COLORS.put("slategrey", new int[]{0x70, 0x80, 0x90, 0xff});
        WEB_COLORS.put("snow", new int[]{0xff, 0xfa, 0xfa, 0xff});
        WEB_COLORS.put("springgreen", new int[]{0x00, 0xff, 0x7f, 0xff});
        WEB_COLORS.put("steelblue", new int[]{0x46, 0x82, 0xb4, 0xff});
        WEB_COLORS.put("tan", new int[]{0xd2, 0xb4, 0x8c, 0xff});
        WEB_COLORS.put("teal", new int[]{0x00, 0x80, 0x80, 0xff});
        WEB_COLORS.put("thistle", new int[]{0xd8, 0xbf, 0xd8, 0xff});
        WEB_COLORS.put("tomato", new int[]{0xff, 0x63, 0x47, 0xff});
        WEB_COLORS.put("transparent", new int[]{0xff, 0xff, 0xff, 0x00});
        WEB_COLORS.put("turquoise", new int[]{0x40, 0xe0, 0xd0, 0xff});
        WEB_COLORS.put("violet", new int[]{0xee, 0x82, 0xee, 0xff});
        WEB_COLORS.put("wheat", new int[]{0xf5, 0xde, 0xb3, 0xff});
        WEB_COLORS.put("white", new int[]{0xff, 0xff, 0xff, 0xff});
        WEB_COLORS.put("whitesmoke", new int[]{0xf5, 0xf5, 0xf5, 0xff});
        WEB_COLORS.put("yellow", new int[]{0xff, 0xff, 0x00, 0xff});
        WEB_COLORS.put("yellowgreen", new int[]{0x9a, 0xcd, 0x32, 0xff});
    }

    /**
     * Obtiene una representacion de color según el esquema RGBA.
     *
     * @param value Definición de color, pudiendo ser uno de los siguientes
     * tipos:<ul>
     * <li>Un nombre de color web. Ver
     * <a href="http://en.wikipedia.org/wiki/Web_colors">Wikipedia</a>.</li>
     * <li>Un color en formato hexadecimal tipo #RGB, #RRGGBB o #RRGGBBAA.</li>
     * <li>Un color estilo CSS: rgb(R,G,B) o rgb(R,G,B,A).</li>
     * </ul>
     * @return el correspondiente array con los componentes R,G,B y A
     * respectivamente. Si ocurre un fallo se retorna {@code null}.
     */
    public static RGBAColor getColor(String value) {
        int[] rgbaColor = getRGBAComponents(value);
        if (rgbaColor == null) {
            return new RGBAColor(0, 0, 0);
        } else {
            return new RGBAColor(rgbaColor[0], rgbaColor[1], rgbaColor[2], rgbaColor[3]);
        }
    }

    /**
     * Obtiene un array de enteros con los componentes de color según el esquema
     * RGBA.
     *
     * @param value Definición de color, pudiendo ser uno de los siguientes
     * tipos:<ul>
     * <li>Un nombre de color web. Ver
     * <a href="http://en.wikipedia.org/wiki/Web_colors">Wikipedia</a>.</li>
     * <li>Un color en formato hexadecimal tipo #RGB, #RRGGBB o #RRGGBBAA.</li>
     * <li>Un color estilo CSS: rgb(R,G,B) o rgb(R,G,B,A).</li>
     * </ul>
     * @return el correspondiente array con los componentes R,G,B y A
     * respectivamente. Si ocurre un fallo se retorna {@code null}.
     */
    private static int[] getRGBAComponents(String value) {
        try {
            value = value.toLowerCase();
            if (isValidRGBAHexColor(value)) {
                value = value.substring(1);
                if (value.length() == 3) {
                    String r = value.substring(0, 1);
                    String g = value.substring(1, 2);
                    String b = value.substring(2);
                    return new int[]{singleHex(r), singleHex(g), singleHex(b), RGBAColor.MAX_CHANNEL_VALUE};
                } else if (value.length() == 6) {
                    String r = value.substring(0, 2);
                    String g = value.substring(2, 4);
                    String b = value.substring(4);
                    return new int[]{multiHex(r), multiHex(g), multiHex(b), RGBAColor.MAX_CHANNEL_VALUE};
                } else if (value.length() == 8) {
                    String r = value.substring(0, 2);
                    String g = value.substring(2, 4);
                    String b = value.substring(4, 6);
                    String a = value.substring(6);
                    return new int[]{multiHex(r), multiHex(g), multiHex(b), multiHex(a)};
                } else {
                    return null;
                }
            } else if (WEB_COLORS.containsKey(value)) {
                int[] webColor = WEB_COLORS.get(value);
                byte r = (byte) webColor[0];
                byte g = (byte) webColor[1];
                byte b = (byte) webColor[2];
                byte a = (byte) webColor[3];
                return new int[]{r, g, b, a};
            } else {
                return null;
            }
        } catch (Exception exc) {
            return null;
        }
    }

    private static boolean isValidRGBAHexColor(String hex) {
        if (!hex.startsWith("#")) {
            return false;
        }
        int len = hex.length();
        if (len == 4 || len == 7 || len == 9) {
            String match = "#[0-9a-fA-F]{" + (len - 1) + "}";
            return hex.matches(match);
        }
        return false;
    }

    private static int singleHex(String c) {
        return multiHex(c + c);
    }

    private static int multiHex(String s) {
        return Integer.parseInt(s, 16);
    }

}
