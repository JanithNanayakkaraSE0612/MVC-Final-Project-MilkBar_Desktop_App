package lk.ijse.MilkBar.model;

import lk.ijse.MilkBar.to.Supplier;
import lk.ijse.MilkBar.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {
    public static boolean save(Supplier supplier) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Supplier VALUES (?, ?, ?, ?,?)";
        return CrudUtil.execute(sql, supplier.getId(), supplier.getName(), supplier.getContact(), supplier.getEmail(),supplier.getCompany());
    }
    public static Supplier search(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM Supplier WHERE sup_id = ?";
        ResultSet result = CrudUtil.execute(sql, id);
        if(result.next()) {
            return new Supplier(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5)
            );
        }
        return null;
    }
    public static boolean update(Supplier supplier) throws SQLException, ClassNotFoundException {

        String sql = "update Supplier set sup_name=? ,contact=?,email=?,company=? where sup_id=?";
        return  (CrudUtil.execute(sql,supplier.getName(),supplier.getContact(),supplier.getEmail(),supplier.getCompany(),supplier.getId()));

    }
    public static boolean delete(Supplier supplier) throws SQLException, ClassNotFoundException {
        String sql = "delete from Supplier where sup_id = ?";
        return CrudUtil.execute(sql,supplier.getId());
    }
    public static ArrayList<Supplier> getAllSupplier() throws SQLException, ClassNotFoundException {
        String sql = "select * from Supplier";
        ResultSet result = CrudUtil.execute(sql);
        ArrayList<Supplier> suppliers = new ArrayList<>();
        while (result.next()){
            suppliers.add(new Supplier(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5)
            ));
        }
        return suppliers ;
    }
    public static ResultSet getAllIds() throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("SELECT sup_id FROM Supplier");
    }
}
