package managers;

import entities.Book;
import entities.Sale;
import entities.User;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public class ReportsManager {

    List<Sale> getSalesAfterDate(Date date) {
        return new LinkedList<>();
    }

    List<User> getTopPurchasers(int limit, Date afterDate) {
        return new LinkedList<>();
    }

    List<Book> getTopSellingBooks(int limit, Date afterDate) {
        return new LinkedList<>();
    }
}
