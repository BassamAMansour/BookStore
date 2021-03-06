package managers;

import database.TransactionsHandler;
import entities.Book;
import entities.Sale;
import entities.User;
import org.hibernate.Session;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ReportsManager {

    public List<Sale> getSalesAfterDate(Date date) {
        AtomicReference<List<Sale>> sales = new AtomicReference<>();

        TransactionsHandler.execute((session -> sales.set(getSalesAfterDate(session, date))));

        return sales.get();
    }

    public List<User> getTopPurchasersAfterDate(Date date, int limit) {
        AtomicReference<List<User>> users = new AtomicReference<>();

        TransactionsHandler.execute((session -> users.set(getTopPurchasersAfterDate(session, date, limit))));

        return users.get();
    }

    public List<Book> getTopSellingBooksAfterDate(Date date, int limit) {
        AtomicReference<List<Book>> books = new AtomicReference<>();

        TransactionsHandler.execute((session -> books.set(getTopSellingBooksAfterDate(session, date, limit))));

        return books.get();
    }

    private List<Sale> getSalesAfterDate(Session session, Date date) {
        String query = "FROM " + Sale.class.getName() + " AS S WHERE S.saleDate >= :saleDate";

        return session.createQuery(query, Sale.class)
                .setParameter("saleDate", date)
                .getResultList();
    }


    private List<User> getTopPurchasersAfterDate(Session session, Date date, int limit) {

        String query = "SELECT S.userId FROM " + Sale.class.getName() +
                " AS S WHERE S.saleDate >= :saleDate GROUP BY S.userId ORDER BY COUNT(S.userId) DESC";

        List<Integer> userIds = session.createQuery(query, Integer.class)
                .setParameter("saleDate", date)
                .setMaxResults(limit)
                .getResultList();

        return new UsersManager().getUsers(userIds);
    }


    private List<Book> getTopSellingBooksAfterDate(Session session, Date date, int limit) {
        String query = "SELECT S.bookId FROM " + Sale.class.getName() +
                " AS S WHERE S.saleDate >= :saleDate GROUP BY S.bookId ORDER BY COUNT(S.bookId) DESC";

        List<Integer> bookIds = session.createQuery(query, Integer.class)
                .setParameter("saleDate", date)
                .setMaxResults(limit)
                .getResultList();

        return new BooksFinder().findBooksByISBN(bookIds);
    }


}
