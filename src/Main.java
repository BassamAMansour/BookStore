import entities.Book;
import entities.Order;
import entities.User;
import managers.UsersManager;
import org.hibernate.service.spi.Manageable;
import entities.Sale;
import panels.CustomerPanel;
import panels.ManagerPanel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.time.Instant;
import java.util.List;

public class Main {

    public static void main(final String[] args) throws Exception {


        //* Testing Business Processes *//

        CustomerPanel customerPanel = CustomerPanel.fromCredentials("Brock_Greenholt86", "5MDBPTXd2dGWzTl");

        ManagerPanel managerPanel = ManagerPanel.fromCredentials("Josiane.Dicki", "rPrBBa8rON0Phhz");

        List<Sale> sales = managerPanel.getReportsManager().getSalesAfterDate(new Date(Instant.now().getEpochSecond()));

        System.out.println(sales);

        CustomerPanel userPanel;
        //ManagerPanel managerPanel;
        /*
        // Sign up - new customer
        User user = new User();
        user.setUsername("mahmoudtarek");
        user.setPassword("12345");
        user.setEmail("xyz@gmail.com");
        user.setFirstName("Mahmoud");
        user.setLastName("Tarek");
        user.setPhone("012333");
        user.setAddress("address");
        user.setPrivilegeType(User.PRIVILEGE_CUSTOMER);
        customerPanel = CustomerPanel.fromNewUser(user);

        // Login
        customerPanel = CustomerPanel.fromCredentials("mahmoudtarek","12345");
        */

        // Login as manager
        userPanel = CustomerPanel.fromCredentials("admin","admin");
        if(userPanel.getUser().getPrivilegeType()==User.PRIVILEGE_MANAGER){
            userPanel = ManagerPanel.fromManager(userPanel.getUser());
        }

        /*
        // edit Account
        customerPanel.getUser().setPhone("333");
        */

        /*
        // search for books
        Book book1 = customerPanel.getBooksFinder().findBook(32323);
        Book book2 = customerPanel.getBooksFinder().findBook(21644);
        List<Book> list = customerPanel.getBooksFinder().findBooksByAuthor("author name");
        // FindBookByTitle is missing

        // Add books to cart
        customerPanel.getSalesManager().getCart().addBook(book1,2);
        customerPanel.getSalesManager().getCart().addBook(book2,1);

        // View items in the cart
        List<Book> cartItems = customerPanel.getSalesManager().getCart().getBooks();
        // getQuantities is missing

        // Remove items from the cart
        customerPanel.getSalesManager().getCart().removeBook(book2);

        // Checkout a shopping cart
        customerPanel.getSalesManager().confirmSale("creditCardNum",new Date(2020,6,30));

        // Add new Books
        // Not sure
        customerPanel = CustomerPanel.fromCredentials("admin","admin");
        managerPanel = (ManagerPanel) customerPanel;
        managerPanel.getStoreManager().addBook(book2);

        // Modify existing books
        // how to use updateBook ?
        managerPanel.getStoreManager().updateBook(book2);

        // Place orders on books
        Order order = new Order();
        order.setBookId(book1.getIsbn());   // no getId
        order.setOrderedQuantity(5);
        managerPanel.getStoreManager().addOrder(order);

        // Confirm orders
        managerPanel.getStoreManager().confirmOrder(order.getId());

        // Promote customers
        managerPanel.getUsersManager().promoteUser(user,User.PRIVILEGE_MANAGER);

        // View sales reports
        // working on ..

        // Logout
        // has no meaning for us
        */


    }

}