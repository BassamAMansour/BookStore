package panels;

import entities.User;
import managers.ReportsManager;
import managers.StoreManager;
import managers.UsersManager;

public class ManagerPanel extends CustomerPanel {
    private StoreManager storeManager = new StoreManager(user.getId());
    private UsersManager usersManager = new UsersManager();
    private ReportsManager reportsManager = new ReportsManager();

    private ManagerPanel(User manager) {
        super(manager);
    }

    public static ManagerPanel fromManager(User manager) throws Exception {
        if (manager == null || manager.getPrivilegeType() != User.PRIVILEGE_MANAGER)
            throw new Exception("The provided user isn't manager!");

        return new ManagerPanel(manager);
    }

    public static ManagerPanel fromCredentials(String username, String password) throws Exception {
        return fromManager(new UsersManager().getUser(username, password));
    }

    public StoreManager getStoreManager() {
        return storeManager;
    }

    public UsersManager getUsersManager() {
        return usersManager;
    }

    public ReportsManager getReportsManager() {
        return reportsManager;
    }
}
