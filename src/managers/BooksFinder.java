package managers;

import database.TransactionsHandler;
import entities.Author;
import entities.Book;
import entities.BookPK;
import entities.Publisher;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BooksFinder {
    public Book findBook(int isbn, String title) {
        AtomicReference<Book> book = new AtomicReference<>();

        TransactionsHandler.execute((session -> book.set(session.get(Book.class, new BookPK(isbn, title)))));

        return book.get();
    }

    public Book findBookByTitle(String title) {
        AtomicReference<Book> book = new AtomicReference<>();

        TransactionsHandler.execute((session) -> book.set(getBookByTitle(session, title)));

        return book.get();
    }

    public Book findBookByISBN(int isbn) {
        AtomicReference<Book> book = new AtomicReference<>();

        TransactionsHandler.execute((session) -> book.set(getBookByISBN(session, isbn)));

        return book.get();
    }

    public List<Book> findBooksByISBN(List<Integer> bookIds) {
        AtomicReference<List<Book>> book = new AtomicReference<>();

        TransactionsHandler.execute((session) -> {
            List<Book> books = new ArrayList<>(bookIds.size());

            for (Integer isbn : bookIds) {
                books.add(getBookByISBN(session, isbn));
            }

            book.set(books);
        });

        return book.get();
    }

    public List<Book> findBooksByCategory(String category) {

        AtomicReference<List<Book>> books = new AtomicReference<>();

        TransactionsHandler.execute((session) -> books.set(findBooksByCategory(session, category)));

        return books.get();
    }

    public List<Book> findBooksByAuthor(String authorName) {

        AtomicReference<List<Book>> books = new AtomicReference<>();

        TransactionsHandler.execute((session) -> books.set(getBooksByAuthorName(session, authorName)));

        return books.get();
    }

    public List<Book> findBooksByPublisher(String publisherName) {
        AtomicReference<List<Book>> books = new AtomicReference<>();

        TransactionsHandler.execute((session) -> books.set(getBooksByPublisherName(session, publisherName)));

        return books.get();
    }

    public Author getAuthorById(int authorId) {
        AtomicReference<Author> author = new AtomicReference<>();

        TransactionsHandler.execute((session) -> author.set(getAuthorById(session, authorId)));

        return author.get();
    }

    public Publisher getPublisherById(int publisherId) {
        AtomicReference<Publisher> publisher = new AtomicReference<>();

        TransactionsHandler.execute((session) -> publisher.set(getPublisherById(session, publisherId)));

        return publisher.get();
    }


    private Author getAuthorById(Session session, int authorId) {

        String query = "FROM " + Author.class.getName() + " AS A WHERE A.id = :id";

        return session.createQuery(query, Author.class)
                .setParameter("id", authorId)
                .getSingleResult();
    }

    private Publisher getPublisherById(Session session, int publisherId) {
        String query = "FROM " + Publisher.class.getName() + " AS P WHERE P.id = :id";

        return session.createQuery(query, Publisher.class)
                .setParameter("id", publisherId)
                .getSingleResult();
    }

    private Book getBookByISBN(Session session, int isbn) {
        String query = "FROM " + Book.class.getName() + " AS B WHERE B.isbn = :isbn";

        return session.createQuery(query, Book.class)
                .setParameter("isbn", isbn)
                .getSingleResult();
    }

    private Book getBookByTitle(Session session, String title) {
        String query = "FROM " + Book.class.getName() + " AS B WHERE B.title = :title";

        return session.createQuery(query, Book.class)
                .setParameter("title", title)
                .getSingleResult();
    }

    private List<Book> findBooksByCategory(Session session, String category) {
        String query = "FROM " + Book.class.getName() + " AS B WHERE B.category = :category";
        return session.createQuery(query, Book.class)
                .setParameter("category", category)
                .getResultList();
    }

    private List<Book> getBooksByAuthorName(Session session, String authorName) {

        Author author = getAuthorByName(session, authorName);

        return getBooksByAuthor(session, author);
    }

    private Author getAuthorByName(Session session, String authorName) {
        String authorQuery = "FROM " + Author.class.getName() + " AS A WHERE A.authorName = :authorName";

        return session.createQuery(authorQuery, Author.class)
                .setParameter("authorName", authorName)
                .getSingleResult();
    }

    private List<Book> getBooksByAuthor(Session session, Author author) {
        String bookQuery = "FROM " + Book.class.getName() + " AS B WHERE B.authorId = :authorId";

        return session.createQuery(bookQuery, Book.class)
                .setParameter("authorId", author.getId())
                .getResultList();
    }

    private List<Book> getBooksByPublisherName(Session session, String publisherName) {

        Publisher publisher = getPublisherByName(session, publisherName);

        return getBooksByPublisher(session, publisher);
    }

    private Publisher getPublisherByName(Session session, String publisherName) {
        String publisherQuery = "FROM " + Publisher.class.getName() + " AS P WHERE P.name = :name";
        return session.createQuery(publisherQuery, Publisher.class)
                .setParameter("name", publisherName)
                .getSingleResult();
    }

    private List<Book> getBooksByPublisher(Session session, Publisher publisher) {
        String bookQuery = "FROM " + Book.class.getName() + " AS B WHERE B.publisherId = :publisherId";

        return session.createQuery(bookQuery, Book.class)
                .setParameter("publisherId", publisher.getId())
                .getResultList();
    }
}
