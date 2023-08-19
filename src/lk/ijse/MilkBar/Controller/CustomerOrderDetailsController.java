package lk.ijse.MilkBar.Controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.MilkBar.model.CustomerModel;
import lk.ijse.MilkBar.model.ItemModel;
import lk.ijse.MilkBar.model.OrderModel;
import lk.ijse.MilkBar.tm.CustomerOrderDetailsTm;
import lk.ijse.MilkBar.to.Order;
import lk.ijse.MilkBar.util.Navigation;
import lk.ijse.MilkBar.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class CustomerOrderDetailsController implements Initializable {
    public AnchorPane pane;
    public Label lblOrderId;
    public Label lblOrderDate;
    public JFXComboBox cmbCustomerId;
    public Label lblCustomerName;
    public JFXComboBox cmbItemCode;
    public Label lblDescription;
    public Label lblUnitPrice;
    public Label lblQtyOnHand;
    public TextField txtQty;
    public TableColumn colItemCode;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public TableView<CustomerOrderDetailsTm> tblOrderCart;
    public Label lblNetTotal;
    public Label lblItemName;
    public TableColumn colItemName;

    ObservableList<CustomerOrderDetailsTm> tm = FXCollections.observableArrayList();

    public void cmbItemOnAction(ActionEvent actionEvent) {
            try {
            ResultSet set = ItemModel.getDetails(String.valueOf(cmbItemCode.getValue()));
            if (set.next()) {
                lblItemName.setText(set.getString(1));
                lblQtyOnHand.setText(set.getString(2));
                lblUnitPrice.setText(set.getString(3));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
    public void btnNewCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CUSTOMER,pane);
    }


    private void loadCustomerIds() {

        try {
            ResultSet set = CustomerModel.getCustomerDetails(String.valueOf(cmbCustomerId.getValue()));
            if (set.next()) {

                lblCustomerName.setText(set.getString(1));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {

        tm.add(new CustomerOrderDetailsTm(
                String.valueOf(cmbItemCode.getValue()),
                lblItemName.getText(),
                txtQty.getText(),
                lblUnitPrice.getText(),
                String.valueOf(Double.parseDouble(lblUnitPrice.getText()) * Double.parseDouble(txtQty.getText()))

        ));
        tblOrderCart.refresh();
        getTotal();
        cmbItemCode.getItems().clear();
        setItemIds();
        lblItemName.setText("");
        lblUnitPrice.setText("");
        txtQty.setText("");

    }
    private String total() {
        double total = 0;
        for (int i = 0; i < tm.size(); i++) {
           total += Double.parseDouble(tm.get(i).getTotal());

        }
        return String.valueOf(total);
    }

    private String timeNow() {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        return format.format(new Date());

    }
    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        try {
            if (OrderModel.setOrder(
                    new Order(
                            lblOrderId.getText(),
                            lblOrderDate.getText(),
                            timeNow(),
                            String.valueOf(cmbCustomerId.getValue()),
                            total()

                    ), tm
            )) {
                new Alert(Alert.AlertType.CONFIRMATION, "Ok").show();
                tm.clear();
                tblOrderCart.getItems().clear();
                txtQty.setText("");
                cmbCustomerId.getItems().clear();
                cmbItemCode.getItems().clear();
                setOrderId();
                setCusIds();
                setItemIds();
                lblItemName.setText("");
                lblCustomerName.setText("");
                lblUnitPrice.setText("");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCustomerIds();
        setDate();
        setCusIds();
        setItemIds();
        setOrderId();

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tblOrderCart.setItems(tm);
    }

    private void setOrderId() {
        try {
            ResultSet set = ItemModel.getLastId();
            if (set.next()) {
                String[] os = set.getString(1).split("O");
                int id = Integer.parseInt(os[1]);
                id++;
                lblOrderId.setText("O" + id);
            }else {
                lblOrderId.setText("O1");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setItemIds() {
        ArrayList<String> ids = new ArrayList<>();
        try {
            ResultSet set = ItemModel.getAllIds();
            while (set.next()) {
                ids.add(set.getString(1));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        cmbItemCode.getItems().addAll(ids);
    }

    private void setCusIds() {
        ArrayList<String> ids = new ArrayList<>();
        try {
            ResultSet set = CustomerModel.getAllIds();
            while (set.next()) {
                ids.add(set.getString(1));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        cmbCustomerId.getItems().addAll(ids);
    }

    private void setDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        lblOrderDate.setText(format.format(new Date()));
    }

    public void onClickedBackButton(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.HOME,pane);
    }
    public void getTotal(){
        double total = 0;
        for (CustomerOrderDetailsTm ab:tm){
            total +=Double.parseDouble(String.valueOf(ab.getTotal()));
        }
        lblNetTotal.setText(String.valueOf(total));
    }

}
