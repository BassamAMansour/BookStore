package models;

import entities.Book;

import java.util.*;

public class Cart {
    private HashSet<Book> books = new HashSet<>();
    private HashMap<Integer, Integer> bookQuantity = new HashMap<>();

    public void addBook(Book book) {

        if (books.contains(book)) {
            bookQuantity.put(book.getIsbn(), bookQuantity.get(book.getIsbn()) + 1);
        } else {
            books.add(book);
            bookQuantity.put(book.getIsbn(), 1);
        }
    }

    public boolean removeBook(Book book) {
        bookQuantity.remove(book.getIsbn());
        return books.remove(book);
    }

    public List<Book> getBooks() {
        return new LinkedList<>(books);
    }

    public int getBookQuantity(long isbn) {
        return bookQuantity.getOrDefault(isbn, 1);
    }

    public double getTotalPrice() {
        double total = 0.0;

        for (Book book : books) {
            total += book.getSellingPrice() * bookQuantity.get(book.getIsbn());
        }

        return total;
    }
}
