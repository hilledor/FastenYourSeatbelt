package utils;

import java.util.Collection;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jdor
 */
public class Utils {

    /**
     * Check if a variable is null or empty or only contains spaces.
     *
     * @param x a string.
     * @return true if x is null or empty
     */
    public static boolean isEmpty(final String x) {
        if (x == null || x.length() == 0) {
            return true;
        }
        return trim(x).length() == 0;
    }

    /**
     * Trim a String. <code>null</code> will be seen as the empty String. This
     * will never return null, always empty String.
     *
     * @param s A String or null.
     * @return A trimmed string.
     */
    public static String trim(final String s) {
        return trim(s, "");
    }

    /**
     * Trim a String, but return <code>defaultValue<code> if
     * <code>s</code> is null.
     *
     * @param s The String to trim.
     * @param defaultValue The value to return if s is null.
     * @return A String.
     */
    public static String trim(final String s, final String defaultValue) {
        if (s == null) {
            return defaultValue;
        }
        return s.trim();
    }

    public static String glue(String string1, String string2, String seperator) {
        if (!Utils.isEmpty(string1) && !Utils.isEmpty(string2)) {
            string1 += seperator;
        }
        string1 += string2;
        return string1;
    }

    public static void alert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }

    public static void assertNotNull(String msg, Object obj) {
        if (obj == null) {
            throw new IllegalStateException(msg);
        }
    }

    public static String glue(Collection<String> list, String separator) {
        boolean isFirst = true;
        StringBuilder result = new StringBuilder();
        for (String s : list) {
            if (isFirst) {
                isFirst = false;
            } else {
                result.append(separator);
            }
            result.append(s);
        }
        return result.toString();
    }

    public static void glue(StringBuilder result, String separator,
            Object... values) {
        if (values == null) {
            return;
        }
        boolean isFirst = true;
        for (int i = 0; i < values.length; i++) {
            if (values[i] instanceof Object[]) {
                throw new IllegalStateException("values[i] is een array");
            }
            if (values[i] != null) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    result.append(separator);
                }
                result.append(values[i].toString());
            }
        }
    }

}
