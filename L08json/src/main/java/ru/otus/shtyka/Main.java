package ru.otus.shtyka;

import com.google.gson.Gson;
import ru.otus.shtyka.json_writer.JsonWriter;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        JsonWriter writer = new JsonWriter();
        Gson gson = new Gson();
        List<String> list = Arrays.asList("one", "two");
        System.out.println(gson.toJson(125.25));
        System.out.println(writer.toJson(125.25));
        System.out.println(gson.toJson(list));
        System.out.println(writer.toJson(list));
    }
}
