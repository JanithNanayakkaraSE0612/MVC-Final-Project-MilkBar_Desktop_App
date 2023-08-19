package lk.ijse.MilkBar.model;

import lk.ijse.MilkBar.to.Employee;
import lk.ijse.MilkBar.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {
    public static boolean save(Employee employee) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Employee VALUES (?, ?, ?, ?,?)";
        return CrudUtil.execute(sql, employee.getId(), employee.getName(), employee.getAddress(), employee.getEmail(),employee.getSalary());
    }
    public static Employee search(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM Employee WHERE emp_id = ?";
        ResultSet result = CrudUtil.execute(sql, id);
        if(result.next()) {
            return new Employee(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getDouble(5)
            );
        }
        return null;
    }
    public static boolean update(Employee employee) throws SQLException, ClassNotFoundException {
        String sql = "update Employee set emp_name=? ,address=?,email=?,salary=? where emp_id=?";
        return (CrudUtil.execute(sql,employee.getName(),employee.getAddress(),employee.getEmail(),employee.getSalary(),employee.getId()));
    }
    public static boolean delete(Employee employee) throws SQLException, ClassNotFoundException {
        String sql = "delete from Employee where emp_id = ?";
        return CrudUtil.execute(sql,employee.getId());
    }
    public static ArrayList<Employee> getAllEmployee() throws SQLException, ClassNotFoundException {
        String sql = "select * from Employee";
        ResultSet result = CrudUtil.execute(sql);
        ArrayList<Employee> employees = new ArrayList<>();
        while (result.next()){
            employees.add(new Employee(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getDouble(5)
            ));
        }
        return employees ;
    }
    public static ArrayList<String> loadEmployeeID() throws SQLException, ClassNotFoundException {
        String sql = "SELECT emp_id FROM Employee";
        ResultSet result = CrudUtil.execute(sql);

        ArrayList<String> empIDList = new ArrayList<>();

        while (result.next()) {
            empIDList.add(result.getString(1));
        }
        return empIDList;
    }
    public static ResultSet getAllIds() throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("SELECT emp_id FROM Employee");
    }
}
