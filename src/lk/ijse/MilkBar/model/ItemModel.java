package lk.ijse.MilkBar.model;

import javafx.collections.ObservableList;
import lk.ijse.MilkBar.tm.CustomerOrderDetailsTm;
import lk.ijse.MilkBar.to.Item;
import lk.ijse.MilkBar.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemModel {
    public static boolean save(Item item) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO item VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql, item.getCode(), item.getName(), item.getQtyOnHand(),item.getUnitPrice());
    }
    public static Item search(String code) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM item WHERE item_code = ?";
        ResultSet result = CrudUtil.execute(sql, code);
        if(result.next()) {
            return new Item(
                    result.getString(1),
                    result.getString(2),
                    result.getInt(3),
                    result.getDouble(4)

            );
        }
        return null;
    }
    public static boolean update(Item item ) throws SQLException, ClassNotFoundException {

        String sql = "update item set item_name=?,qtyOnHand=?,unitprice=?  where item_code=?";
        return  (CrudUtil.execute(sql,item.getName(),item.getQtyOnHand(),item.getUnitPrice(),item.getCode()));
    }
    public static boolean delete(Item item) throws SQLException, ClassNotFoundException {
        String sql = "delete from item where  item_code= ?";
        return CrudUtil.execute(sql,item.getCode());
    }
    public static ArrayList<Item> getAllItem() throws SQLException, ClassNotFoundException {
        String sql = "select * from item";
        ResultSet result = CrudUtil.execute(sql);
        ArrayList<Item> items = new ArrayList<>();
        while (result.next()){
            items.add(new Item(
                    result.getString(1),
                    result.getString(2),
                    result.getInt(3),
                    result.getDouble(4)
            ));
        }
        return items;
    }
    private static boolean updateQty(Item item) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Item SET qtyOnHand = qtyOnHand - ? WHERE code = ?";
        return CrudUtil.execute(sql, item.getQtyOnHand(), item.getCode());
    }
    public static ArrayList<String> loadItemCodes() throws SQLException, ClassNotFoundException {
        String sql = "SELECT code FROM Item";
        ResultSet result = CrudUtil.execute(sql);

        ArrayList<String> codeList = new ArrayList<>();

        while (result.next()) {
            codeList.add(result.getString(1));
        }
        return codeList;
    }
    public static ResultSet getDetails(String valueOf) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("SELECT item_name,qtyOnHand,unitprice FROM item WHERE item_code=?", valueOf);
    }
    public static ResultSet getAllIds() throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("SELECT item_code FROM item");
    }
    public static ResultSet getLastId() throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1");
    }
    public static boolean updateItem(ObservableList<CustomerOrderDetailsTm> tm) throws SQLException, ClassNotFoundException {
        for (int i = 0; i < tm.size(); i++) {
            if (CrudUtil.execute("UPDATE  item SET  qtyOnHand=qtyOnHand-? WHERE item_code=?",
                    tm.get(i).getQty(),
                    tm.get(i).getItemCode()
            )) {

            } else {
                System.out.println("error");
                return false;
            }
        }
        return true;
    }
}
