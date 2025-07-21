package utils.data;

public class BookPayload {
    public String name;
    public String author;
    public int published_year;
    public String book_summary;
    public int expected_status;

    public BookPayload() {}

    public BookPayload(String name, String author, int published_year, String book_summary, int expected_status) {
        this.name = name;
        this.author = author;
        this.published_year = published_year;
        this.book_summary = book_summary;
        this.expected_status = expected_status;
    }
}
