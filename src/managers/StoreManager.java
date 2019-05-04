package managers;

import database.TransactionsHandler;
import entities.Book;
import entities.Order;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class StoreManager {

    private int userId;

    public StoreManager(int userId) {
        this.userId = userId;
    }

    boolean addBook(Book book) {
        book.setAddedBy(userId);

        TransactionsHandler.execute((session) -> session.saveOrUpdate(book));

        return true;
    }

    void addBooks(List<Book> books) {

        for (Book book : books) {
            book.setAddedBy(userId);
        }

        TransactionsHandler.execute((session) -> {
            for (Book book : books) {
                session.saveOrUpdate(book);
            }
        });
    }

    void updateBook(Book book) {
        TransactionsHandler.execute((session) -> session.update(book));
    }

    Book getBook(int isbn) {
        AtomicReference<Book> book = new AtomicReference<>();

        TransactionsHandler.execute((session) -> book.set(session.get(Book.class, isbn)));

        return book.get();
    }

    void addOrder(Order order) {
        TransactionsHandler.execute((session) -> session.saveOrUpdate(order));
    }

    Order getOrder(long orderId) {
        AtomicReference<Order> order = new AtomicReference<>();

        TransactionsHandler.execute((session) -> order.set(session.get(Order.class, orderId)));

        return order.get();
    }

    void confirmOrder(long orderId) {
        TransactionsHandler.execute((session) -> session.delete(session.get(Order.class, orderId)));
    }

}
