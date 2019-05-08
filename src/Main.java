import entities.Book;
import entities.Order;
import entities.User;
import managers.UsersManager;
import org.hibernate.service.spi.Manageable;
import panels.CustomerPanel;
import panels.ManagerPanel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(final String[] args) throws Exception {

        User user = new User();
        user.setFirstName("FName");
        user.setLastName("LName");
        user.setAddress("Address");
        user.setPassword("Password");
        user.setPhone("123456789");
        user.setUsername("username");
        user.setEmail("Email");

        CustomerPanel customerPanel = CustomerPanel.fromNewUser(user);
    }

}