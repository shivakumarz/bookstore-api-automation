package tests;
import io.qameta.allure.*;
import base.BaseTest;
import utils.data.BookPayload;
import com.github.javafaker.Faker;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

/**
 * This test class demonstrates how to create a book with random data using Faker.
 * It validates the response and ensures the book is created successfully.
 */

public class CreateBook extends BaseTest {

    Faker faker = new Faker();

    private BookPayload generateFakeBook(int expectedStatus) {
        return new BookPayload(
            faker.book().title(),
            faker.book().author(),
            faker.number().numberBetween(1990, 2027),
            faker.lorem().sentence(),
            expectedStatus
        );
    }

    @Test
    (description = "Create a book with valid dynamically generated data")
    public void createBookWithValidRandomData() {
        BookPayload book = generateFakeBook(200);
        Allure.step("Generated book: " + book.name + " by " + book.author);

        getSpec()
            .body(book)
            .post("/books/")
            .then()
            .statusCode(book.expected_status)
            .header("Content-Type", equalTo("application/json"))
            .body("id", notNullValue())
            .body("name", equalTo(book.name))
            .body("author", equalTo(book.author))
            .body("published_year", equalTo(book.published_year))
            .body("book_summary", equalTo(book.book_summary));

         Allure.step("Book creation request validated successfully.");
    }
}
