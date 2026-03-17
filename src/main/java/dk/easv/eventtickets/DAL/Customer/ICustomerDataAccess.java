package dk.easv.eventtickets.DAL.Customer;

import dk.easv.eventtickets.BE.Customer;
import dk.easv.eventtickets.BE.Event;

import java.util.List;

public interface ICustomerDataAccess {
    List<Customer> getAllCustomer() throws Exception;

    Customer createCustomer(Customer customer) throws Exception;

    void updateCustomer(Customer customer) throws Exception;

    void deleteCustomer(Customer customer) throws Exception;

    List<Customer> getMoviesForCustomer(Customer customer) throws Exception;
}
