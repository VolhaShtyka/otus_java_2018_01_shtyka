package ru.otus.shtyka.json_writer;

import static ru.otus.shtyka.json_writer.JsonWriterObject.isSimpleValue;

public class JsonWriter {

    public String toJson(Object object) {
        if (object == null) {
            return "null";
        }
        JsonWritingAlgorithm algorithm = getJSONWriter(object);
        return algorithm.toJson(object);
    }

    private JsonWritingAlgorithm getJSONWriter(Object object) {
        if (isSimpleValue(object)) {
            return new JsonWriterSimple();
        } else {
            return new JsonWriterObject();
        }
    }
}
