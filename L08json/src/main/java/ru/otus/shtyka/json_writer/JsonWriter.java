package ru.otus.shtyka.json_writer;

import java.util.Collection;

public class JsonWriter {

    public String toJson(Object object) {
        if (object == null) {
            return "null";
        }
        JsonWritingAlgorithm algorithm = getJSONWriter(object);
        return algorithm.toJson(object);
    }

    private JsonWritingAlgorithm getJSONWriter(Object object) {
        if (object instanceof Number || object instanceof String || object instanceof Collection || object instanceof Boolean) {
            return new JsonWriterSimple();
        } else {
            return new JsonWriterObject();
        }
    }
}
