package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "book_order")
public class Order {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int orderedQuantity;
    private int bookId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "orderedQuantity")
    public int getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(int orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    @Basic
    @Column(name = "bookID")
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                orderedQuantity == order.orderedQuantity &&
                bookId == order.bookId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderedQuantity, bookId);
    }
}
