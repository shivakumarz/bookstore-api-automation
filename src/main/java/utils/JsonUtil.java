package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonUtil {

    // Reuse a single ObjectMapper instance for performance
    private static final ObjectMapper mapper = new ObjectMapper();

    // Read JSON into a POJO or concrete class
    public static <T> T readJsonFromFile(String path, Class<T> clazz) {
        try {
            return mapper.readValue(new File(path), clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + path, e);
        }
    }

    // Read JSON into generic types like Map<String, Object>, List<>, or nested maps
    public static <T> T readJsonFromFile(String path, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(new File(path), typeReference);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + path, e);
        }
    }
}
