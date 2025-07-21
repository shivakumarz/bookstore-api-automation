package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.JsonUtil;
import config.Config;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
/**
 * AuthData class to handle authentication data and tests.
 */

public class AuthData {

    private static final String JSON_FILE_PATH = "src/main/java/utils/data/LoginData.json";

    @DataProvider(name = "loginDataProvider")
    public Object[][] loginDataProvider() {
        Map<String, Map<String, Object>> allData = JsonUtil.readJsonFromFile(
                JSON_FILE_PATH,
                new TypeReference<Map<String, Map<String, Object>>>() {}
        );

        return new Object[][]{
                {allData.get("invalidPassword"), 400},
                {allData.get("missingFields"), 500},
                {allData.get("validUser"), 200}
        };
    }

    @Test(dataProvider = "loginDataProvider")
    @Description("Verifies login functionality using various payloads from JSON")
    public void loginWithVariousData(Map<String, Object> payload, int expectedStatusCode) {
        Response response = given()
                .contentType("application/json")
                .body(payload)
                .post(Config.getBaseUrl() + "/login");

        int actualStatusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        Allure.step("Received status code: " + actualStatusCode);
        Allure.step("Response body: " + responseBody);

        System.out.println("Status Code: " + actualStatusCode);
        System.out.println("Response for payload: " + payload + " -> " + responseBody);

        // Validate status code
        response.then().statusCode(expectedStatusCode);

       // Validate headers based on expected response type
        if (expectedStatusCode == 200 || expectedStatusCode == 400) {
            response.then().header("Content-Type", containsString("application/json"));
        } else {
            System.out.println("Skipping Content-Type validation for status code: " + expectedStatusCode);
        }

        // Handle JSON response content safely
        try {
            switch (expectedStatusCode) {
                case 200:
                    response.then()
                            .body("access_token", notNullValue())
                            .body("token_type", equalTo("bearer"));
                    break;

                case 400:
                    response.then()
                            .body("detail", equalTo("Incorrect email or password"));
                    break;

                case 500:
                    Allure.step("Internal Server Error:\\n " + responseBody);
                    System.err.println("Internal Server Error:\n" + responseBody);
                    break;

                default:
                    Allure.step("Unhandled status code: " + expectedStatusCode);
                    System.out.println("Unhandled status code: " + expectedStatusCode);
            }
        } catch (Exception e) {
            Allure.step("Validation exception: " + e.getMessage());
            Allure.step("Raw response: " + responseBody);
            System.err.println("Validation exception: " + e.getMessage());
            System.err.println("Raw response: " + responseBody);
        }
    }
}
