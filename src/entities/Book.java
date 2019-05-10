package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(BookPK.class)
public class Book {

    private int isbn;
    private String title;
    private int authorId;
    private int threshold;
    private int sellingPrice;
    private int category;
    private int quantity;
    private int publicationYear;
    private int publisherId;
    private int addedBy;

    @Id
    @Column(name = "isbn")
    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    @Id
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "authorID")
    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Basic
    @Column(name = "threshold")
    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    @Basic
    @Column(name = "sellingPrice")
    public int getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    @Basic
    @Column(name = "category")
    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Basic
    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "publicationYear")
    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    @Basic
    @Column(name = "publisherID")
    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    @Basic
    @Column(name = "addedBy")
    public int getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(int addedBy) {
        this.addedBy = addedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn == book.isbn &&
                authorId == book.authorId &&
                threshold == book.threshold &&
                sellingPrice == book.sellingPrice &&
                category == book.category &&
                quantity == book.quantity &&
                publicationYear == book.publicationYear &&
                publisherId == book.publisherId &&
                addedBy == book.addedBy &&
                Objects.equals(title, book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, authorId, threshold, sellingPrice, category, quantity, publicationYear, publisherId, addedBy);
    }

    public static class Category {
        public static final int ERROR = 0;
        public static final int SCIENCE = 1;
        public static final int ART = 2;
        public static final int RELIGION = 3;
        public static final int HISTORY = 4;
        public static final int GEOGRAPHY = 5;

        public static int getCategory(String category) {
            if ("Science".equals(category)) return SCIENCE;
            else if ("Art".equals(category)) return ART;
            else if ("Religion".equals(category)) return RELIGION;
            else if ("History".equals(category)) return HISTORY;
            else if ("Geography".equals(category)) return GEOGRAPHY;
            else return ERROR;
        }

        public static String getCategory(int category) {
            if (category == SCIENCE) return "Science";
            else if (category == ART) return "Art";
            else if (category == RELIGION) return "Religion";
            else if (category == HISTORY) return "History";
            else if (category == GEOGRAPHY) return "Geography";
            else return "Error";
        }
    }
}
