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
import lk.ijse.MilkBar.model.SupplierModel;
import lk.ijse.MilkBar.to.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierFormController {
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableView <Supplier>tblSupplier;
    public TextField txtId;
    public TextField txtName;
    public TextField txtTelNumber;
    public TableColumn colTelNumber;
    public JFXTextField txtSearch;
    public TextField txtUnitPrice;
    public AnchorPane pane;
    public TextField txtContact;
    public TextField txtEmail;
    public TableColumn colContact;
    public TableColumn colEmail;
    public TableColumn colCompany;
    public TextField txtCompany;

    public void initialize () throws SQLException, ClassNotFoundException {

        tblSupplier.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblSupplier.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("Name"));
        tblSupplier.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblSupplier.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("email"));
        tblSupplier.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("company"));
        getAllData();
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        String company = txtCompany.getText();


        Supplier supplier = new Supplier(id, name, contact,email,company);
        try {
            boolean isAdded = SupplierModel.save(supplier);

            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added!").show();
                emptyTextField(supplier);
                getAllData();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
                emptyTextField(supplier);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {
        Supplier supplier = new Supplier();
        emptyTextField(supplier);
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = txtId.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        String company = txtCompany.getText();

        Supplier supplier = new Supplier(id,name,contact,email,company);
        boolean isUpdated = SupplierModel.update(supplier);
        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Update Supplier").show();
            emptyTextField(supplier);
            getAllData();
        }else {
            new Alert(Alert.AlertType.WARNING,"Something Happened").show();
            emptyTextField(supplier);
        }
    }
    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String ID= txtId.getText();
        Supplier supplier = new Supplier(ID);
        SupplierModel supplierModel = new SupplierModel();

        boolean isDeleted = supplierModel.delete(supplier);
        if(isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Supplier Delete!").show();
            emptyTextField(supplier);
            getAllData();
        } else {
            new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            emptyTextField(supplier);
        }

    }
    public void lblEnterKeyPressedOnAction(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String id = txtId.getText();

            try {
                Supplier supplier = SupplierModel.search(id);
                if (supplier != null) {
                    fillData(supplier);

                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void SearchOnAction(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String id = txtSearch.getText();

            try {
                Supplier supplier = SupplierModel.search(id);
                if (supplier != null) {
                    fillData(supplier);
                    txtSearch.clear();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void getAllData() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> AllData = SupplierModel.getAllSupplier();
        ObservableList<Supplier> observableList = FXCollections.observableArrayList();
        for (Supplier supplier :
                AllData) {
            observableList.add(new Supplier(
                    supplier.getId(),
                    supplier.getName(),
                    supplier.getContact(),
                    supplier.getEmail(),
                    supplier.getCompany()
            ));
        }
        tblSupplier.setItems(observableList);
    }
    private void fillData(Supplier supplier) {
        txtId.setText(supplier.getId());
        txtName.setText(supplier.getName());
        txtContact.setText(supplier.getContact());
        txtEmail.setText(supplier.getEmail());
        txtCompany.setText(supplier.getCompany());


    }
    public Supplier getData(Supplier supplier) {
        String id = txtId.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        String company = txtCompany.getText();
        return supplier;
    }
    private Supplier emptyTextField(Supplier supplier) {
        getData(supplier);
        txtId.clear();
        txtName.clear();
        txtContact.clear();
        txtEmail.clear();
        txtCompany.clear();
        return supplier;
    }
    public void onClickBackButton(MouseEvent mouseEvent) { }

}
