import panels.CustomerPanel;
import panels.ManagerPanel;

public class Main {


    public static void main(final String[] args) throws Exception {

        /*User user = new User();
        user.setFirstName("FName");
        user.setLastName("LName");
        user.setAddress("Address");
        user.setPassword("Password");
        user.setPhone("123456789");
        user.setUsername("username");
        user.setEmail("Email");*/

        CustomerPanel customerPanel = CustomerPanel.fromCredentials("Brock_Greenholt86", "5MDBPTXd2dGWzTl");

        ManagerPanel managerPanel = ManagerPanel.fromCredentials("Josiane.Dicki", "rPrBBa8rON0Phhz");


    }


}