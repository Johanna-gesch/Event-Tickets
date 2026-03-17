package dk.easv.eventtickets.GUI.Controllers.Model;

import dk.easv.eventtickets.BE.Customer;
import dk.easv.eventtickets.BE.User;
import dk.easv.eventtickets.BLL.CustomerManager;
import javafx.collections.ObservableList;

public class CustomerModel {
    private CustomerManager cMan;

    public CustomerModel() throws Exception {
        cMan = new CustomerManager();

    }

    public Customer createCustomer(Customer newCustomer) throws Exception {

        Customer Customercreated = cMan.createCustomer(newCustomer);

        return Customercreated;

    }
}
