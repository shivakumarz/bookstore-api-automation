package tests;

import base.BaseTest;
import io.restassured.common.mapper.TypeRef;
import com.fasterxml.jackson.core.type.TypeReference;
import io.qameta.allure.Allure;
import org.testng.annotations.Test;
import utils.JsonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;
/**
 * This test class validates the full lifecycle of a book: create, update, get, and delete.
 * It uses data from a JSON file to perform these operations and verifies the responses.
 */

public class ValidateCreateUpdateGetDeleteBook extends BaseTest {

    @Test
    (description = "End-to-end test for book lifecycle using chained REST calls")
    public void testFullBookFlow() {
        Allure.step("Loading test data from JSON");
        Map<String, Map<String, Object>> allData = JsonUtil.readJsonFromFile(
            "src/main/java/utils/data/bookData.json",
            new TypeReference<Map<String, Map<String, Object>>>() {}
        );

        Map<String, Object> payload = allData.get("valid_book_flow");
        int expectedStatus = (int) payload.remove("expected_status");

        Allure.step("Creating new book");
        int bookId = getSpec()
            .body(payload)
            .post("/books/")
            .then()
            .statusCode(expectedStatus)
            .extract().path("id");
        Allure.step("Book created with ID: " + bookId);

        Allure.step("Updating book with new values");
        Map<String, Object> updatePayload = new HashMap<>();
        updatePayload.put("name", "Updated Chain Book");
        updatePayload.put("author", "Updated Chain Author");
        updatePayload.put("published_year", 2025);
        updatePayload.put("book_summary", "Updated chaining summary");


        getSpec()
            .body(updatePayload)
            .put("/books/" + bookId)
            .then()
            .statusCode(200)
            .body("id", equalTo(bookId));

        Allure.step("Verifying updated book exists via GET /books/");
        List<Map<String, Object>> allBooks = getSpec()
            .get("/books/")
            .then()
            .statusCode(200)
            .extract().as(new TypeRef<List<Map<String, Object>>>() {});

        Map<String, Object> updatedBook = allBooks.stream()
            .filter(book -> {
                Object idValue = book.get("id");
                return idValue instanceof Integer && ((Integer) idValue).equals(bookId);
            })
            .findFirst()
            .orElseThrow(() -> new AssertionError("Updated book not found"));

        assertEquals(updatedBook.get("name"), updatePayload.get("name"));

        Allure.step("Deleting the book");
        getSpec()
            .delete("/books/" + bookId)
            .then()
            .statusCode(200);

        Allure.step("Validating book deletion");
        List<Map<String, Object>> booksAfterDelete = getSpec()
            .get("/books/")
            .then()
            .statusCode(200)
            .extract().as(new TypeRef<List<Map<String, Object>>>() {});

        boolean exists = booksAfterDelete.stream()
            .anyMatch(book -> {
                Object idValue = book.get("id");
                return idValue instanceof Integer && ((Integer) idValue).equals(bookId);
            });

        assertEquals(exists, false, "Book should not exist after deletion");
    }
}
