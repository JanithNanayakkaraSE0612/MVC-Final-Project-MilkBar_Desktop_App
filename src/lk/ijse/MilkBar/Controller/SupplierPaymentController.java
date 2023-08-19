package lk.ijse.MilkBar.Controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import lk.ijse.MilkBar.model.EmployeeModel;
import lk.ijse.MilkBar.model.SupplierModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierPaymentController {
    public JFXComboBox cmbCustomerID;
    public Label supplierName;
    public Label date;
    public Label time;
    public void cmbSupplierID(ActionEvent actionEvent) {
        ArrayList<String> ids = new ArrayList<>();
        try {
            ResultSet set = SupplierModel.getAllIds();
            while (set.next()) {
                ids.add(set.getString(1));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        cmbCustomerID.getItems().addAll(ids);

    }
    public void PaymentSuccessFully(ActionEvent actionEvent) {
    }
}
