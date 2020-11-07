package com.nhlstenden.amazonsimulatie.jsonBuilders;

/**
 * This class creates an JSON array
 * A JSONBuilder can be passed in as a value
 */
public class JSONArrayBuilder implements Builder {

    private final StringBuilder stringBuilder = new StringBuilder();
    private boolean notTheFirst = false;

    /**
     * Creates the JSON array String Builder
     */
    public JSONArrayBuilder() {
        stringBuilder.append("[");
    }

    /**
     * Puts in a JSONBuilder object in the JSON array
     * @param jsonBuilder Object to add in the array
     * @return This JSONArrayBuilder
     */
    public JSONArrayBuilder put(JSONBuilder jsonBuilder) {
        if (notTheFirst) stringBuilder.append(",");
        stringBuilder.append(jsonBuilder.toString());
        notTheFirst = true;
        return this;
    }

    /**
     * Returns the JSON array String
     * @return String in JSON array format
     */
    @Override
    public String toString() {
        return stringBuilder.toString() + "]";
    }

}
