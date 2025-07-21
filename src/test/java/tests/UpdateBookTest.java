package tests;

import base.BaseTest;
import com.fasterxml.jackson.core.type.TypeReference;
import io.qameta.allure.Allure;
import org.testng.annotations.Test;
import utils.JsonUtil;
import java.util.Map;

import static org.hamcrest.Matchers.*;
/**
 * This test class demonstrates how to update a book using data from a JSON file.
 * It validates that the API allows updating book details correctly.
 */

public class UpdateBookTest extends BaseTest {

    @Test
    (description = "Update a book using data from JSON and validate the response")
    public void updateBookFromJson() {
        Allure.step("Loading update payload from bookData.json");

        
        Map<String, Map<String, Object>> allData = JsonUtil.readJsonFromFile(
            "src/main/java/utils/data/bookData.json",
            new TypeReference<Map<String, Map<String, Object>>>() {}
        );
        Map<String, Object> payload = allData.get("valid_update");

        int bookId = (int) payload.get("id");
        int expectedStatus = (int) payload.remove("expected_status");
        Allure.step("Updating book with ID: " + bookId);
        Allure.step("Payload: " + payload.toString());

        getSpec()
            .body(payload)
            .put("/books/" + bookId)
            .then()
            .statusCode(expectedStatus)
            .body("id", equalTo(bookId))
            .body("name", equalTo(payload.get("name")))
            .body("author", equalTo(payload.get("author")))
            .body("published_year", equalTo(payload.get("published_year")))
            .body("book_summary", equalTo(payload.get("book_summary")));
        Allure.step("Book updated successfully and response verified");
    }
}
