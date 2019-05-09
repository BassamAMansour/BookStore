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

    public void addBook(Book book) {
        book.setAddedBy(userId);

        TransactionsHandler.execute((session) -> session.saveOrUpdate(book));
    }

    public void addBooks(List<Book> books) {
        TransactionsHandler.execute((session) -> {
            for (Book book : books) {
                book.setAddedBy(userId);
                session.saveOrUpdate(book);
            }
        });
    }

    public void updateBook(Book book) {
        TransactionsHandler.execute((session) -> session.update(book));
    }

    public void addOrder(Order order) {
        TransactionsHandler.execute((session) -> session.saveOrUpdate(order));
    }

    public Order getOrder(long orderId) {
        AtomicReference<Order> order = new AtomicReference<>();

        TransactionsHandler.execute((session) -> order.set(session.get(Order.class, orderId)));

        return order.get();
    }

    public List<Order> getAllOrders() {
        AtomicReference<List<Order>> orders = new AtomicReference<>();

        TransactionsHandler.execute((session) -> orders.set(session.createQuery("FROM " + Order.class.getName(), Order.class).getResultList()));

        return orders.get();
    }

    public void confirmOrder(long orderId) {
        TransactionsHandler.execute((session) -> session.delete(session.get(Order.class, orderId)));
    }

}
