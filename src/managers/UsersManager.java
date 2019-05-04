package managers;

import database.TransactionsHandler;
import entities.User;

import java.util.concurrent.atomic.AtomicReference;

public class UsersManager {

    public static void addUser(User user) throws Exception {
        if (user == null || user.getPrivilegeType() == User.PRIVILEGE_MANAGER)
            throw new Exception("Not allowed to create a manager!");

        TransactionsHandler.execute((session) -> session.save(user));
    }

    public static User getUser(String username, String password) {
        AtomicReference<User> user = new AtomicReference<>();

        //TODO:Implement
        TransactionsHandler.execute((session) -> user.set(session.get(User.class, username)));

        if (user.get() != null && !password.equals(user.get().getPassword())) return null;

        return user.get();
    }

    void promoteUser(User user, int privilegeLevel) {
        user.setPrivilegeType(privilegeLevel);
        updateUser(user);
    }

    void updateUser(User user) {
        TransactionsHandler.execute((session) -> session.update(user));
    }

    public User getUser(int userId) {
        AtomicReference<User> user = new AtomicReference<>();

        //TODO:Implement

        TransactionsHandler.execute((session) -> user.set(session.get(User.class, userId)));

        return user.get();
    }
}
