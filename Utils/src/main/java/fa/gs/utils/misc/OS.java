/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.misc;

/**
 *
 * @author Fabio A. González Sosa
 */
public class OS {

    private static final String osName = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return osName.contains("win");
    }

    public static boolean isMac() {
        return osName.contains("mac");
    }

    public static boolean isUnix() {
        return (osName.contains("nix") || osName.contains("nux") || osName.contains("aix"));
    }

    public static boolean isSolaris() {
        return osName.contains("sunos");
    }

}
