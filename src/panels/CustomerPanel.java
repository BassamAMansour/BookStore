package panels;

import entities.User;
import managers.BooksFinder;
import managers.SalesManager;
import managers.UsersManager;
import models.Cart;

public class CustomerPanel {

    protected User user;
    protected SalesManager salesManager;
    protected BooksFinder booksFinder = new BooksFinder();

    protected CustomerPanel(User user) {
        this.user = user;
        this.salesManager = new SalesManager(user.getId(), new Cart());
    }

    private CustomerPanel() {
    }

    public static CustomerPanel fromCredentials(String username, String password) throws Exception {
        User user = new UsersManager().getUser(username, password);

        if (user == null) throw new Exception("User not found!");

        return new CustomerPanel(user);
    }

    public static CustomerPanel fromNewUser(User user) throws Exception {

        if (user == null || user.getPrivilegeType() == User.PRIVILEGE_MANAGER)
            throw new Exception("Not allowed to create a manager!");

        UsersManager.addUser(user);

        return new CustomerPanel(user);
    }

    public User getUser() {
        return user;
    }

    public SalesManager getSalesManager() {
        return salesManager;
    }

    public BooksFinder getBooksFinder() {
        return booksFinder;
    }
}
