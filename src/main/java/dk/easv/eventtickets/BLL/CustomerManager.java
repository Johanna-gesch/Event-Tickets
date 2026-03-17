package dk.easv.eventtickets.BLL;

import dk.easv.eventtickets.BE.Customer;
import dk.easv.eventtickets.DAL.Customer.CustomerDAO_DB;
import dk.easv.eventtickets.DAL.Customer.ICustomerDataAccess;

public class CustomerManager {
    private final ICustomerDataAccess customerDao;

    public CustomerManager() throws Exception {
        customerDao = new CustomerDAO_DB();

    }

    public Customer createCustomer(Customer customer) throws Exception {
        return customerDao.createCustomer(customer);
    }


}
