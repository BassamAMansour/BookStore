package entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class BookPK implements Serializable {
    private int isbn;
    private String title;

    public BookPK(int isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }

    @Column(name = "isbn")
    @Id
    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    @Column(name = "title")
    @Id
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookPK bookPK = (BookPK) o;
        return isbn == bookPK.isbn &&
                Objects.equals(title, bookPK.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title);
    }
}
