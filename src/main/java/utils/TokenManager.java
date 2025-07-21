package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class TokenManager {

    private static String token;
    private static final String LOGIN_JSON_PATH = "src/main/java/utils/data/LoginData.json";

    public static String getToken() {
        if (token == null) {
            token = generateToken();
        }
        return token;
    }

    private static String generateToken() {
        // Read validUser from JSON
        Map<String, Map<String, Object>> loginData = JsonUtil.readJsonFromFile(
                LOGIN_JSON_PATH,
                new TypeReference<Map<String, Map<String, Object>>>() {}
        );

        Map<String, Object> validUser = loginData.get("validUser");

        Response response = given()
                .contentType("application/json")
                .body(validUser)
                .post("http://localhost:8000/login");

        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Failed to get token. Status: " +
                    response.getStatusCode() + ", Response: " + response.getBody().asString());
        }

        return response.jsonPath().getString("access_token");
    }
}
