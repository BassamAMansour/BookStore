package managers;

import database.TransactionsHandler;
import entities.Book;
import entities.Sale;
import models.Cart;
import org.hibernate.Session;

import java.sql.Date;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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

        AtomicBoolean saleSuccess = new AtomicBoolean(true);

        TransactionsHandler.execute((session -> saleSuccess.set(proceedWithSales(session, sales))));

        if(saleSuccess.get())
            cart.clearAll();

        return saleSuccess.get();
    }

    private boolean proceedWithSales(Session session, List<Sale> sales) {

        boolean saleSuccess = true;

        //TODO: Add locks
        for (Sale sale : sales) {
            Book dbBook = new BooksFinder().findBookByISBN(sale.getBookId());

            if (dbBook.getQuantity() < sale.getSoldQuantity()) {
                saleSuccess = false;
                rejectSale(session, sale);
                break;
            } else {
                dbBook.setQuantity(dbBook.getQuantity() - sale.getSoldQuantity());
                session.update(dbBook);
                session.save(sale);
            }
        }

        return saleSuccess;
    }

    private void rejectSale(Session session, Sale sale) {
        session.getTransaction().rollback();
        System.out.printf("Sale #%d for book isbn = %d was rejected for insufficient quantity!\n", sale.getId(), sale.getBookId());
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
