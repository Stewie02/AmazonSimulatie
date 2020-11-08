package com.nhlstenden.amazonsimulatie.jsonBuilders;

import org.junit.Test;

import static org.junit.Assert.*;

public class JSONBuilderTest {

    /**
     * This is a small test with 3 keys value pairs
     */
    @Test
    public void testTestToString() {
        String actual = new JSONBuilder()
                .put("key1", "value1")
                .put("key2", "value2")
                .put("key3", "value3")
                .toString();

        String expected = "{\"key1\":\"value1\",\"key2\":\"value2\",\"key3\":\"value3\"}";
        assertEquals(expected, actual);
    }

    /**
     * In this test we put a JSONBuilder object in the JSONBuilder object
     */
    @Test
    public void testJSONObjectInJSON() {
        String actual = new JSONBuilder()
                .put("key1", "value1")
                .put("key2", new JSONBuilder()
                        .put("key3", "value3")
                        .put("key4", "value4")
                ).toString();

        String expected = "{\"key1\":\"value1\",\"key2\":{\"key3\":\"value3\",\"key4\":\"value4\"}}";
        assertEquals(expected, actual);
    }

    /**
     * We create just a very big JSON object
     */
    @Test
    public void testVeryBigJSONObject() {
        JSONBuilder actualObject = new JSONBuilder();
        for (int i = 0; i < 100; i++)
            actualObject.put("key"+(i+1), "value"+(i+1));

        StringBuilder expected = new StringBuilder("{");
        for (int i = 0; i < 100; i++)
        {
            String toAdd = i != 0 ? ",\"key" + (i+1) + "\":\"value" + (i+1) + "\"" : "\"key" + (i+1) + "\":\"value" + (i+1) + "\"";
            expected.append(toAdd);
        }
        expected.append("}");

        assertEquals(expected.toString(), actualObject.toString());
    }

}