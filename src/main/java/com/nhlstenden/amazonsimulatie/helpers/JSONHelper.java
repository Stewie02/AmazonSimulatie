package com.nhlstenden.amazonsimulatie.helpers;

public class JSONHelper {

    public static String createKeyValueJSON(String key, String value) {
        return surroundString(key) + ": " + surroundString(value);
    }

    public static String surroundString(String s) {
        return "\"" + s + "\"";
    }
}
