package dk.easv.eventtickets.DAL.Customer;

import dk.easv.eventtickets.BE.Customer;
import dk.easv.eventtickets.DAL.DBConnector;

import java.sql.*;
import java.util.List;

public class CustomerDAO_DB implements ICustomerDataAccess{
    private final DBConnector databaseConnector;

    public CustomerDAO_DB() throws Exception{
        databaseConnector = new DBConnector();
    }

    @Override
    public Customer createCustomer(Customer newCustomer) throws Exception {
        String customerSql = "INSERT INTO dbo.Customers (FName, LName, Email) VALUES (?, ?, ?);";

        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(customerSql, Statement.RETURN_GENERATED_KEYS);

            //Bind parameters
            stmt.setString(1, newCustomer.getFName());
            stmt.setString(2, newCustomer.getLName());
            stmt.setString(3, newCustomer.getEmail());

            //Run the SQL statement
            stmt.executeUpdate();

            //Get generated ID
            ResultSet rs = stmt.getGeneratedKeys();
            int customerId = 0;
            if(rs.next()){
                customerId = rs.getInt(1);
            }

            Customer createdCustomer = new Customer(customerId, newCustomer.getFName(), newCustomer.getLName(), newCustomer.getEmail());

            return createdCustomer;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Could not create User", e);
        }
    }
}
