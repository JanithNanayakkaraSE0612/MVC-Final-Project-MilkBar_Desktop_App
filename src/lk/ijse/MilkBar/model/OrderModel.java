package lk.ijse.MilkBar.model;

import javafx.collections.ObservableList;
import lk.ijse.MilkBar.db.DBConnection;
import lk.ijse.MilkBar.tm.CustomerOrderDetailsTm;
import lk.ijse.MilkBar.to.Order;
import lk.ijse.MilkBar.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderModel {
    public static boolean save(Order order) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Orders VALUES(?, ?, ?)";
        return CrudUtil.execute(sql, order.getOrderId(), order.getDate(), order.getCustomerId());
    }
    public static boolean setOrder(Order order, ObservableList<CustomerOrderDetailsTm> tm) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getDbConnection().getConnection();
            connection.setAutoCommit(false);
            if (OrderModel.setOrder(order)){
                if (CustomerOrderDetailsModel.setDetails(order, tm)) {
                    if (ItemModel.updateItem(tm)) {
                        connection.commit();
                        System.out.println("retun");
                        return true;
                    } else {
                        System.out.println("update rollback");
                        connection.rollback();
                    }
                } else {
                    System.out.println("order detail rollback");
                    connection.rollback();
                }
            } else {
                System.out.println("order rollback");
                connection.rollback();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }
    private static boolean setOrder(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO orders VALUES (?,?,?,?)",
                order.getOrderId(),
                order.getCustomerId(),
                order.getDate(),
                order.getTime()
                );

    }
    public static String generateNextOrderId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT id FROM Orders ORDER BY id DESC LIMIT 1";
        ResultSet result = CrudUtil.execute(sql);
        if (result.next()) {
            return generateNextOrderId(result.getString(1));
        }
        return generateNextOrderId(result.getString(null));
    }
    private static String generateNextOrderId(String currentOrderId) {
        if (currentOrderId != null) {
            String[] split = currentOrderId.split("D0");
            int id = Integer.parseInt(split[1]);
            id += 1;
            return "D0" + id;
        }
        return "O001";
    }
}
