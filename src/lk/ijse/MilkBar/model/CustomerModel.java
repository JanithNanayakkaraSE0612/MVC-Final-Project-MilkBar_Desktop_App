package lk.ijse.MilkBar.model;

import lk.ijse.MilkBar.to.Customer;
import lk.ijse.MilkBar.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {
    public static boolean save(Customer customer) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Customer VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql, customer.getId(), customer.getName(), customer.getAddress(), customer.getContact());
    }
    public static Customer search(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM Customer WHERE cus_id = ?";
        ResultSet result = CrudUtil.execute(sql, id);
        if(result.next()) {
            return new Customer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );
        }
        return null;
    }
    public static boolean update(Customer customer) throws SQLException, ClassNotFoundException {

        String sql = "update Customer set cus_name=? ,address=?,tel_number=? where cus_id=?";
        return  (CrudUtil.execute(sql,customer.getName(),customer.getAddress(),customer.getContact(),customer.getId()));
    }
    public static boolean delete(Customer customer) throws SQLException, ClassNotFoundException {
        String sql = "delete from Customer where cus_id = ?";
        return CrudUtil.execute(sql,customer.getId());
    }
    public static ArrayList<Customer> getAllCustomer() throws SQLException, ClassNotFoundException {
        String sql = "select * from customer";
        ResultSet result = CrudUtil.execute(sql);
        ArrayList<Customer> customers = new ArrayList<>();
        while (result.next()){
            customers.add(new Customer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            ));
        }
        return customers ;
    }
    public static ResultSet getAllIds() throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("SELECT cus_id FROM customer");
    }
    public static ResultSet getCustomerDetails(String valueOf) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("SELECT cus_name FROM customer WHERE cus_id=?",valueOf);
    }
}
