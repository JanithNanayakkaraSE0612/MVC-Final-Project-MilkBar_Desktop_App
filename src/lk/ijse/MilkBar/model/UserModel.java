package lk.ijse.MilkBar.model;

import javafx.scene.control.Alert;
import lk.ijse.MilkBar.to.User;
import lk.ijse.MilkBar.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserModel {
    public static boolean save(User user) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Users VALUES (?, ?, ?,?)";
        return CrudUtil.execute(sql, user.getEmpID(), user.getUserName(), user.getUserPassword(),user.getRole());
    }
    public static User search(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM Users WHERE emp_id = ?";
        ResultSet result = CrudUtil.execute(sql, id);
        if(result.next()) {
            return new User(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );
        }
        return null;
    }
    public static boolean update(User user) throws SQLException, ClassNotFoundException {
        String sql = "update users set username=? ,user_password=? user_role=? where emp_id=?";
        if (CrudUtil.execute(sql,user.getUserName(),user.getUserPassword(),user.getRole(),user.getEmpID())){
            new Alert(Alert.AlertType.INFORMATION,"Updated").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Error").show();
        }
        return true;
    }
    public static boolean delete(User user) throws SQLException, ClassNotFoundException {
        String sql = "delete from users where emp_id = ?";
        return CrudUtil.execute(sql,user.getEmpID());
    }
    public static ArrayList<User> getAllCustomer() throws SQLException, ClassNotFoundException {
        String sql = "select * from users";
        ResultSet result = CrudUtil.execute(sql);
        ArrayList<User> users = new ArrayList<>();
        while (result.next()){
            users.add(new User(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            ));
        }
        return users ;
    }
    public static ResultSet getAllRole() throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("SELECT user_role FROM Users");
    }
}
