package lk.ijse.MilkBar.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.MilkBar.model.ItemModel;

import lk.ijse.MilkBar.to.Item;
import lk.ijse.MilkBar.util.Navigation;
import lk.ijse.MilkBar.util.Routes;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemFormController {
    public TableColumn colID;
    public TableColumn colName;
    public AnchorPane pane;
    public TextField txtItemCode;
    public TextField txtItemName;
    public TextField txtUnitPrice;
    public TableView <Item>tblItem;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TextField txtQtyOnHand;
    public JFXTextField txtSearch;

    public void initialize () throws SQLException, ClassNotFoundException {

        tblItem.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblItem.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblItem.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblItem.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        getAllData();
    }
    public void btnRemoveOnAction(ActionEvent actionEvent) {
        Item item = new Item();
        emptyTextField(item);
    }

   public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String code = txtItemCode.getText();
        String Name = txtItemName.getText();
       int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());


        Item item = new Item(code,Name,qtyOnHand,unitPrice);
        boolean isUpdated = ItemModel.update(item);
        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Update Supplier").show();
            emptyTextField(item);
            getAllData();
        }else {
            new Alert(Alert.AlertType.WARNING,"Something Happened").show();
            emptyTextField(item);
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String code= txtItemCode.getText();
        Item item = new Item(code);
        ItemModel itemModel = new ItemModel();

        boolean isDeleted = itemModel.delete(item);
        if(isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Item Delete!").show();
            emptyTextField(item);
            getAllData();
        } else {
            new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            emptyTextField(item);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String code = txtItemCode.getText();
        String name = txtItemName.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

        Item item = new Item(code, name,qtyOnHand,unitPrice);
        try {
            boolean isAdded = ItemModel.save(item);

            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Added!").show();
                emptyTextField(item);
                getAllData();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void lblSearchOnAction(KeyEvent keyEvent) {
        String code = txtSearch.getText();

        try {
            Item item = ItemModel.search(code);
            if(item != null) {
                fillData(item);
                txtSearch.clear();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void btnBackOnClickAction(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.HOME,pane);
    }
    public void getAllData() throws SQLException, ClassNotFoundException {
        ArrayList<Item> AllData = ItemModel.getAllItem();
        ObservableList<Item> observableList = FXCollections.observableArrayList();
        for (Item item :
                AllData) {
            observableList.add(new Item(
                    item.getCode(),
                    item.getName(),
                    item.getQtyOnHand(),
                    item.getUnitPrice()
            ));
        }
        tblItem.setItems(observableList);
    }
    private void fillData(Item item) {
        txtItemCode.setText(item.getCode());
        txtItemName.setText(item.getName());
        txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));

    }
    public Item getData(Item items) {
        String code = txtItemCode.getText();
        String Name = txtItemName.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        double qtyOnHand = Double.parseDouble(txtQtyOnHand.getText());
        return items;
    }

    private Item emptyTextField(Item items) {
        getData(items);
        txtItemCode.clear();
        txtItemName.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        return items;
    }

    public void keyPressedEnterOnAction(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String code = txtItemCode.getText();

            try {
                Item item = ItemModel.search(code);
                if (item != null) {
                    fillData(item);

                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
