package ru.otus.shtyka.json_writer;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import ru.otus.shtyka.json_writer.test_objects.EmptyClass;
import ru.otus.shtyka.json_writer.test_objects.TestAClass;
import ru.otus.shtyka.json_writer.test_objects.TestBClass;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JsonWriterTest {
    private JsonWriter writer;
    private Gson gson;

    @Before
    public void initial() {
        writer = new JsonWriter();
        gson = new Gson();
    }

    @Test
    public void writeNullTest() {
        assertEquals("JSON Strings are not identical", gson.toJson(null), writer.toJson(null));
    }

    @Test
    public void writeEmptyObjectTest() {
        EmptyClass testValue = new EmptyClass();
        String testJsonValue = writer.toJson(testValue);
        assertEquals("JSON Strings are not identical", gson.toJson(testValue), testJsonValue);
        assertEquals("GSON Values are not identical", gson.fromJson(testJsonValue, EmptyClass.class), testValue);
    }

    @Test
    public void writePrimitiveValuesTest() {
        Double testValue = 125.65;
        String testJsonValue = writer.toJson(testValue);
        assertEquals("JSON Strings are not identical", gson.toJson(testValue), testJsonValue);
        assertEquals("GSON Values are not identical", gson.fromJson(testJsonValue, Double.class), testValue);
    }

    @Test
    public void writeStringValuesTest() {
        String testValue = "Trololo";
        String testJsonValue = writer.toJson(testValue);
        assertEquals("JSON Strings are not identical", gson.toJson(testValue), testJsonValue);
        assertEquals("GSON Values are not identical", gson.fromJson(testJsonValue, String.class), testValue);
    }

    @Test
    public void writeObjectBTest() {
        TestBClass testValue = new TestBClass(36, "Trololo", Arrays.asList(53.5, 659.36, 698.1));
        String testJsonValue = writer.toJson(testValue);
        assertEquals("JSON Strings are not identical", gson.toJson(testValue), testJsonValue);
        assertEquals("GSON Values are not identical", gson.fromJson(testJsonValue, TestBClass.class), testValue);
    }

    @Test
    public void writeObjectATest() {
        TestBClass bClassValue = new TestBClass(20, "Trolo", Arrays.asList(1.0, 9.36, 698.1));
        TestAClass testValue = new TestAClass(1, "Trolalo", Arrays.asList(5.9, 325.20), bClassValue);
        String testJsonValue = writer.toJson(testValue);
        assertEquals("JSON Strings are not identical", gson.toJson(testValue), testJsonValue);
        assertEquals("GSON Values are not identical", gson.fromJson(testJsonValue, TestAClass.class), testValue);
    }

    @Test
    public void writeArrayTest() {
        List<String> testValue = Arrays.asList("Trololo", "Ololo", "Tralala");
        String testJsonValue = writer.toJson(testValue);
        assertEquals("JSON Strings are not identical", gson.toJson(testValue), testJsonValue);
        assertEquals("GSON Values are not identical", gson.fromJson(testJsonValue, List.class), testValue);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void writeBooleanTest() {
        Boolean testValue = true;
        String testJsonValue = writer.toJson(testValue);
        assertEquals("JSON Strings are not identical", gson.toJson(testValue), testJsonValue);
        assertEquals("GSON Values are not identical", gson.fromJson(testJsonValue, Boolean.class), testValue);
    }
}