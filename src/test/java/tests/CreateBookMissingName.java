package tests;
import io.qameta.allure.*;
import base.BaseTest;
import com.fasterxml.jackson.core.type.TypeReference;
import org.testng.annotations.Test;
import utils.JsonUtil;

import java.util.Map;

import static org.hamcrest.Matchers.*;
/**
 * This test class demonstrates how to create a book with missing name field.
 * It validates that the API responds with an error when the name is not provided.
 */

public class CreateBookMissingName extends BaseTest {

    @Test
    (description = "Attempt to create a book without the required 'name' field")
    public void createBookWithMissingName() {
        // Load entire JSON
        Map<String, Map<String, Object>> allData = JsonUtil.readJsonFromFile(
            "src/main/java/utils/data/bookdata.json",
            new TypeReference<Map<String, Map<String, Object>>>() {}
        );

        // Get the test-specific payload
        Map<String, Object> payload = allData.get("missing_name");

        getSpec()
            .body(payload)
            .post("/books/")
            .then()
            .statusCode(500)
            .body(containsString("Internal Server Error"));

        Allure.step("Verified 500 Internal Server Error for missing 'name' field.");

    }
}
