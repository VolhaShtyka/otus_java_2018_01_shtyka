package ru.otus.shtyka.json_writer;

import org.json.simple.JSONObject;

import java.lang.reflect.Field;

public class JsonWriterObject implements JsonWritingAlgorithm {

    @Override
    public String toJson(Object object) {
        JSONObject jsonObject = new JSONObject();
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                jsonObject.put(field.getName(), field.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return jsonObject.toJSONString();
    }
}
