package managers;

import database.TransactionsHandler;
import entities.Book;
import entities.BookPK;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BooksFinder {
    public Book findBook(int isbn, String title) {
        AtomicReference<Book> book = new AtomicReference<>();

        TransactionsHandler.execute((session -> book.set(session.get(Book.class, new BookPK(isbn, title)))));

        return book.get();
    }

    public List<Book> findBooksByCategory(String category) {

        //TODO:Implement

        return new ArrayList<>();
    }

    public List<Book> findBooksByAuthor(String author) {
        //TODO:Implement

        return new ArrayList<>();
    }

    public List<Book> findBooksByPublisher(String publisher) {
        //TODO:Implement

        return new ArrayList<>();
    }

}
