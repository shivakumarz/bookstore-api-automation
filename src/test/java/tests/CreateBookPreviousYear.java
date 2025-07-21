package tests;

import base.BaseTest;
import com.fasterxml.jackson.core.type.TypeReference;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import utils.JsonUtil;
import java.util.Map;
import static org.hamcrest.Matchers.*;
/**
 * This test class demonstrates how to create a book with a published year in the past.
 * It validates that the API allows creating books with previous years.
 */


public class CreateBookPreviousYear extends BaseTest {

    @Test
    (description = "Create a book with a published year in the past")
    public void createBookWithPreviousYear() {
        // Load entire JSON
        Map<String, Map<String, Object>> allData = JsonUtil.readJsonFromFile(
            "src/main/java/utils/data/bookData.json",
            new TypeReference<Map<String, Map<String, Object>>>() {}
        );

        // Get the test-specific payload
        Map<String, Object> payload = allData.get("previous_year");
        int expectedStatus = (int) payload.remove("expected_status");

        getSpec()
            .body(payload)
            .post("/books/")
            .then()
            .statusCode(expectedStatus)
            .body("name", equalTo(payload.get("name")))
            .body("published_year", equalTo(payload.get("published_year")));

        Allure.step("Book created with previous year: " + payload.get("published_year"));
    }
    
}
