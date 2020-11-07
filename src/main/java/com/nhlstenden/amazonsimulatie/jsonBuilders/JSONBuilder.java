package com.nhlstenden.amazonsimulatie.jsonBuilders;

/**
 * This class is used when building a JSON object
 * It has different functions to add the key value pairs
 */
public class JSONBuilder implements Builder {

    private final StringBuilder stringBuilder = new StringBuilder();
    private boolean notTheFirst = false;

    /**
     * Creates the JSON String Builder
     */
    public JSONBuilder() {
        stringBuilder.append("{");
    }

    /**
     * Creates a key value pair in the JSON object and surrounds them key and value with ""
     * @param key Key in JSON
     * @param value Value in JSON
     * @return This JSONBuilder
     */
    public JSONBuilder put(String key, String value) {
        return putInStringBuilder(surroundString(key), surroundString(value));
    }

    /**
     * Creates a key value pair in the JSON object and surrounds them key and value with ""
     * @param key Key in JSON
     * @param value Value in JSON
     * @return This JSONBuilder
     */
    public JSONBuilder put(String key, Number value) {
        return putInStringBuilder(surroundString(key), surroundString(value.toString()));
    }

    /**
     * Takes in a key and a Builder the creates the key value pair
     * @param key Key in JSON
     * @param value Builder to be the value
     * @return This JSONBuilder
     */
    public JSONBuilder put(String key, Builder value) {
        return putInStringBuilder(surroundString(key), value.toString());
    }

    /**
     * Creates a key value pair and doesn't surround the value with ""
     * @param key Key in JSON
     * @param value Value in JSON
     * @return This JSONBuilder
     */
    public JSONBuilder putNoSurrounding(String key, String value){
        return putInStringBuilder(surroundString(key), value);
    }

    /**
     * Puts the key and value in the StringBuilder and it also makes sure the comma's are placed correct
     * @param key Key in JSON
     * @param value Value in JSON
     * @return This JSONBuilder
     */
    private JSONBuilder putInStringBuilder(String key, String value) {
        if (notTheFirst) stringBuilder.append(',');
        stringBuilder.append(createKeyValueJSON(key, value));
        this.notTheFirst = true;
        return this;
    }

    /**
     * Returns the JSON Object in JSON
     * @return The JSON Object in JSON
     */
    @Override
    public String toString() {
        return stringBuilder.toString() + "}";
    }

    /**
     * Returns a String of key value pair
     * @param key JSON key
     * @param value JSON value
     * @return Return the JSON String
     */
    private String createKeyValueJSON(String key, String value) {
        return key + ":" + value;
    }

    /**
     * Surrounds a String with double quotes
     * @param s String to surround
     * @return Returns the surrounded String
     */
    private String surroundString(String s) {
        return "\"" + s + "\"";
    }
}
