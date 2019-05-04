package managers;

import entities.Book;
import entities.Sale;
import models.Cart;

import java.sql.Date;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

public class SalesManager {

    private int userId;
    private Cart cart;

    public SalesManager(int userId, Cart cart) {
        this.userId = userId;
        this.cart = cart;
    }

    public boolean confirmSale(String cardNum, Date expirationDate) {

        if (!isCardValid(cardNum, expirationDate)) return false;

        List<Sale> sales = extractSales(cardNum, expirationDate);

        for (Sale sale : sales) {
            //TODO:Implement

            addSale(sale);
        }

        return true;
    }

    public void addSale(Sale sale) {

        //TODO:Implement

    }

    private LinkedList<Sale> extractSales(String cardNum, Date expirationDate) {
        LinkedList<Sale> sales = new LinkedList<>();

        for (Book book : cart.getBooks()) {
            Sale sale = new Sale();
            sale.setBookId(book.getIsbn());
            sale.setSaleDate(getCurrentDate());
            sale.setSoldQuantity(cart.getBookQuantity(book.getIsbn()));
            sale.setUserId(userId);
            sale.setCardNum(cardNum);
            sale.setExpirationDate(expirationDate);
            sales.add(sale);
        }

        return sales;
    }

    private boolean isCardValid(String cardNum, Date expirationDate) {
        return expirationDate.after(new Date(Instant.now().getEpochSecond()));
    }

    private Date getCurrentDate() {
        return new Date(Instant.now().toEpochMilli());
    }

    public Cart getCart() {
        return cart;
    }
}
