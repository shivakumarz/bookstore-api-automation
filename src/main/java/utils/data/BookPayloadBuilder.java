package utils.data;

import java.util.HashMap;
import java.util.Map;

public class BookPayloadBuilder {

    private String name;
    private String author;
    private Integer published_year;
    private String book_summary;
    private Integer expected_status;

    public BookPayloadBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public BookPayloadBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public BookPayloadBuilder setPublishedYear(Integer year) {
        this.published_year = year;
        return this;
    }

    public BookPayloadBuilder setBookSummary(String summary) {
        this.book_summary = summary;
        return this;
    }

    public BookPayloadBuilder setExpectedStatus(Integer status) {
        this.expected_status = status;
        return this;
    }

    // For positive test cases
    public BookPayload build() {
        return new BookPayload(
            this.name,
            this.author,
            this.published_year != null ? this.published_year : 0,
            this.book_summary,
            this.expected_status != null ? this.expected_status : 200
        );
    }

    // For negative test cases 
    public Map<String, Object> buildAsMap() {
        Map<String, Object> map = new HashMap<>();
        if (name != null) map.put("name", name);
        if (author != null) map.put("author", author);
        if (published_year != null) map.put("published_year", published_year);
        if (book_summary != null) map.put("book_summary", book_summary);
        return map;
    }
}
