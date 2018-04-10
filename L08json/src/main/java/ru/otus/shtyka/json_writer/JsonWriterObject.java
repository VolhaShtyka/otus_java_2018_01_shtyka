package ru.otus.shtyka.json_writer;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.lang.reflect.Field;
import java.util.Collection;

public class JsonWriterObject implements JsonWritingAlgorithm {

    @Override
    public String toJson(Object object) {
        JSONObject jsonObject = new JSONObject();
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(object);
                if (!(fieldValue instanceof Number || fieldValue instanceof String || fieldValue instanceof Collection || fieldValue instanceof Boolean)) {
                    jsonObject.put(field.getName(), JSONValue.parse(this.toJson(fieldValue)));
                } else {
                    jsonObject.put(field.getName(), fieldValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return jsonObject.toJSONString();
    }
}
