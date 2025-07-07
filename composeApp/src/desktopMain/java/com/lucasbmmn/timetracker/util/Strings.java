package com.lucasbmmn.timetracker.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Helper class to get string values from the strings resources bundle
 */
public class Strings {
    public static final ResourceBundle BUNDLE = ResourceBundle.getBundle("values.strings");

    /**
     * Gets a string for the given key from the resource bundle or one of its parents.
     * If no string can be found for the key, returns the key.
     *
     * @param key the key for the desired string
     * @throws    NullPointerException if {@code key} is {@code null}
     * @throws    ClassCastException if the object found for the given key is not a string
     * @return the string for the given key
     */
    public static String get(String key) {
        return BUNDLE.containsKey(key) ? BUNDLE.getString(key) : key;
    }
}
