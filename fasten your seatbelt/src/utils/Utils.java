package utils;

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
	 * Trim a String. <code>null</code> will be seen as the empty String.
	 * This will never return null, always empty String.
	 *
	 * @param  s A String or null.
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
        if (!Utils.isEmpty(string1)){
            string1 += seperator;
        }
        string1 += string2;
        return string1;
    }


}
