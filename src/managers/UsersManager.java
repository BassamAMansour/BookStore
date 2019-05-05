package managers;

import database.TransactionsHandler;
import entities.User;
import org.hibernate.Session;

import java.util.concurrent.atomic.AtomicReference;

public class UsersManager {

    public static void addUser(User user) throws Exception {
        if (user == null || user.getPrivilegeType() == User.PRIVILEGE_MANAGER)
            throw new Exception("Not allowed to create a manager!");

        TransactionsHandler.execute((session) -> session.save(user));
    }

    public User getUser(String username, String password) {
        AtomicReference<User> user = new AtomicReference<>();

        TransactionsHandler.execute((session) -> user.set(getUserByCredentials(username, password, session)));

        if (user.get() != null && !password.equals(user.get().getPassword())) return null;

        return user.get();
    }

    public User getUser(int userId) {
        AtomicReference<User> user = new AtomicReference<>();

        TransactionsHandler.execute((session) -> user.set(getUserById(userId, session)));

        return user.get();
    }

    private User getUserById(int userId, Session session) {
        String query = "FROM " + User.class.getName() + " AS U WHERE U.id = :userId";
        return session.createQuery(query, User.class)
                .setParameter("id", userId)
                .getSingleResult();
    }

    public void promoteUser(User user, int newPrivilegeLevel) {
        user.setPrivilegeType(newPrivilegeLevel);
        updateUser(user);
    }

    public void updateUser(User user) {
        TransactionsHandler.execute((session) -> session.update(user));
    }

    private User getUserByCredentials(String username, String password, Session session) {
        String query = "FROM " +
                User.class.getName() +
                " AS U WHERE U.username = :username AND U.password = :password";

        return session.createQuery(query, User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();
    }
}
