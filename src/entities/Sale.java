package entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Sale {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date saleDate;
    private Date expirationDate;
    private String cardNum;
    private int soldQuantity;
    private int userId;
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
    @Column(name = "saleDate")
    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    @Basic
    @Column(name = "expirationDate")
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Basic
    @Column(name = "cardNum")
    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    @Basic
    @Column(name = "soldQuantity")
    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    @Basic
    @Column(name = "userID")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
        Sale sale = (Sale) o;
        return id == sale.id &&
                soldQuantity == sale.soldQuantity &&
                userId == sale.userId &&
                bookId == sale.bookId &&
                Objects.equals(saleDate, sale.saleDate) &&
                Objects.equals(expirationDate, sale.expirationDate) &&
                Objects.equals(cardNum, sale.cardNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, saleDate, expirationDate, cardNum, soldQuantity, userId, bookId);
    }
}
