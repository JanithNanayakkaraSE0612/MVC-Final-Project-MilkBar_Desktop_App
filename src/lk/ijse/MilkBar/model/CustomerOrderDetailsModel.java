package lk.ijse.MilkBar.model;

import javafx.collections.ObservableList;

import lk.ijse.MilkBar.tm.CustomerOrderDetailsTm;

import lk.ijse.MilkBar.to.Order;

import lk.ijse.MilkBar.util.CrudUtil;


import java.sql.SQLException;


public class CustomerOrderDetailsModel {
    public static boolean setDetails(Order order, ObservableList<CustomerOrderDetailsTm> tm) throws SQLException, ClassNotFoundException {
        for (int i = 0; i < tm.size(); i++) {
            if (CrudUtil.execute("INSERT INTO customerorderdetails VALUES (?,?,?,?,?)",
                    order.getOrderId(),
                    tm.get(i).getItemCode(),
                    tm.get(i).getQty(),
                    tm.get(i).getPrice(),
                    tm.get(i).getTotal()
            )) {
            } else {
                System.out.println("error");
                return false;
            }
        }
        return true;
    }
}

