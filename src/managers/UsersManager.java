package managers;

import database.TransactionsHandler;
import entities.User;

import java.util.concurrent.atomic.AtomicReference;

public class UsersManager {

    public static void addUser(User user) throws Exception {
        if (user == null || user.getPrivilegeType() == User.PRIVILEGE_MANAGER)
            throw new Exception("Not allowed to create a manager!");

        TransactionsHandler.execute((session, transaction) -> session.save(user));
    }

    void updateUser(User user) {
        TransactionsHandler.execute((session, transaction) -> session.update(user));
    }

    void promoteUser(User user, int privilegeLevel) {
        user.setPrivilegeType(privilegeLevel);
        updateUser(user);
    }

    public User getUser(long userId) {
        AtomicReference<User> user = new AtomicReference<>();

        TransactionsHandler.execute((session, transaction) -> user.set(session.get(User.class, userId)));

        return user.get();
    }

    public static User getUser(String username, String password) {
        AtomicReference<User> user = new AtomicReference<>();

        TransactionsHandler.execute((session, transaction) -> user.set(session.get(User.class, username)));

        if (user.get() != null && !password.equals(user.get().getPassword())) return null;

        return user.get();
    }
}
