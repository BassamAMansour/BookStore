package managers;

import entities.Book;
import entities.Order;

import java.util.List;

 public class StoreManager {

    private int userId;

    public StoreManager(int userId) {
        this.userId = userId;
    }

    boolean addBook(Book book) {
        book.setAddedBy(userId);


        return true;
    }

    void addBooks(List<Book> books) {

        for (Book book : books) {
            book.setAddedBy(userId);
        }

    }

    boolean updateBook(Book book) {


        return true;
    }

    void addOrder(Order order) {

    }

    boolean confirmOrder(long orderId) {

        return true;
    }

}
