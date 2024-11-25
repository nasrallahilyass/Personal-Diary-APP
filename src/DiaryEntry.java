import java.io.Serializable;
import java.time.LocalDate;

public class DiaryEntry implements Serializable {

    private LocalDate date;
    private String title;
    private String content;
    private String author;


    // Constructor
    public DiaryEntry() {}

    public DiaryEntry(LocalDate date, String title, String content, String author) {
        this.date = date;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    // Getters and Setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // toString method
    @Override
    public String toString() {
        return "-------------------------------------\n" +
                "📅 Date    : " + date + "\n" +
                "📝 Title   : " + title + "\n" +
                "✏️ Content : " + content + "\n" +
                "👤 Author  : " + author + "\n" +
                "-------------------------------------";
    }
}
