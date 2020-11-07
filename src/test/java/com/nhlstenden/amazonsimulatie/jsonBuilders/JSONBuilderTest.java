package com.nhlstenden.amazonsimulatie.jsonBuilders;

import org.junit.Test;

import static org.junit.Assert.*;

public class JSONBuilderTest {

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

}