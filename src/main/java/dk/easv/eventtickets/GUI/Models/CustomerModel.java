package dk.easv.eventtickets.GUI.Models;

import dk.easv.eventtickets.BE.Customer;
import dk.easv.eventtickets.BLL.CustomerManager;

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
