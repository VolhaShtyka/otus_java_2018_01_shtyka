package ru.otus.shtyka.json_writer;

import org.json.simple.JSONValue;

public class JsonWriterSimple implements JsonWritingAlgorithm {

    @Override
    public String toJson(Object object) {
        return JSONValue.toJSONString(object);
    }
}
