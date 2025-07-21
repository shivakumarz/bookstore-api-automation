package tests;

import base.BaseTest;
import com.fasterxml.jackson.core.type.TypeReference;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.JsonUtil;

import java.util.Map;

import static org.hamcrest.Matchers.*;

public class DeleteBookTest extends BaseTest {

    @Test
    (description = "Create a book, delete it, and confirm it's not retrievable")
    public void testDeleteBookAndVerifyNotFound() {
        Map<String, Map<String, Object>> allData = JsonUtil.readJsonFromFile(
            "src/main/java/utils/data/bookData.json",
            new TypeReference<Map<String, Map<String, Object>>>() {}
        );
        Map<String, Object> payload = allData.get("delete_book");

        payload.remove("expected_status");
        Allure.step("Payload for book creation: " + payload);

        Response createResponse = getSpec()
            .body(payload)
            .post("/books/")
            .then()
            .statusCode(200)
            .extract().response();

        int bookId = createResponse.jsonPath().getInt("id");
        Allure.step("Book created with ID: " + bookId);

        getSpec()
            .delete("/books/" + bookId)
            .then()
            .statusCode(200)
            .body("message", equalTo("Book deleted successfully"));
        Allure.step("Deleted book ID: " + bookId);

        getSpec()
            .get("/books/" + bookId)
            .then()
            .statusCode(404)
            .body("detail", equalTo("Book not found"));
        Allure.step("Verified that book ID " + bookId + " returns 404 Not Found");
    }
}
