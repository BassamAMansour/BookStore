import entities.Sale;
import panels.CustomerPanel;
import panels.ManagerPanel;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

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

        List<Sale> sales = managerPanel.getReportsManager().getSalesAfterDate(new Date(Instant.now().getEpochSecond()));

        System.out.println(sales);
    }


}