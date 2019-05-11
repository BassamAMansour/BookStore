package models;

import entities.Book;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Cart {
    private HashMap<Integer, Book> books = new HashMap<>();
    private HashMap<Integer, Integer> bookQuantity = new HashMap<>();

    public void addBook(Book book, int quantity) {

        if (books.containsKey(book.getIsbn())) {
            bookQuantity.put(book.getIsbn(), bookQuantity.get(book.getIsbn()) + quantity);
        } else {
            books.put(book.getIsbn(), book);
            bookQuantity.put(book.getIsbn(), quantity);
        }
    }

    public boolean removeBook(Book book) {
        bookQuantity.remove(book.getIsbn());
        return books.remove(book.getIsbn()) != null;
    }

    public List<Book> getBooks() {
        return new LinkedList<>(books.values());
    }

    public int getBookQuantity(int isbn) {
        return bookQuantity.getOrDefault(isbn, 1);
    }

    public double getTotalPrice() {
        double total = 0.0;

        for (Book book : books.values()) {
            total += book.getSellingPrice() * bookQuantity.get(book.getIsbn());
        }

        return total;
    }

    public Book getBook(int isbn) {
        return books.get(isbn);
    }

    public void clearAll(){
        books.clear();
        bookQuantity.clear();
    }
}
