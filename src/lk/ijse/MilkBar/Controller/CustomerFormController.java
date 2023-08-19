package lk.ijse.MilkBar.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.scene.paint.Paint;
import lk.ijse.MilkBar.model.CustomerModel;
import lk.ijse.MilkBar.to.Customer;
import lk.ijse.MilkBar.util.Navigation;
import lk.ijse.MilkBar.util.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerFormController {
    public TableColumn colTelNumber;
    public TableView <Customer>tblCustomer;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TextField txtTelNumber;

    public JFXTextField lblsearch;

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAddress;


    public void initialize () throws SQLException, ClassNotFoundException {

        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("Name"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Address"));
        tblCustomer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));
        
        getAllData();
    }
    @FXML
    void txtCustomerIdOnAction(ActionEvent event) {
        String id = txtId.getText();
        try {
            Customer customer = CustomerModel.search(id);
            if (customer != null) {
                fillData(customer);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillData(Customer customer) {
        txtId.setText(customer.getId());
        txtName.setText(customer.getName());
        txtAddress.setText(customer.getAddress());
        txtTelNumber.setText(customer.getContact());
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String ID = txtId.getText();
        String Name = txtName.getText();
        String Address = txtAddress.getText();
        String Contact = txtTelNumber.getText();


        Customer customer = new Customer(ID,Name,Address,Contact);
        boolean isUpdated = CustomerModel.update(customer);
        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Update Customer").show();
            emptyTextField(customer);
            getAllData();
        }else {
            new Alert(Alert.AlertType.WARNING,"Something Happened").show();
            emptyTextField(customer);
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String ID= txtId.getText();
        Customer customer = new Customer(ID);
        CustomerModel customerModel = new CustomerModel();

        boolean isDeleted = customerModel.delete(customer);

        if(isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Delete!").show();
            emptyTextField(customer);
            getAllData();
        } else {
            new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            emptyTextField(customer);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtTelNumber.getText();

        Customer customer = new Customer(id, name, address, contact);
        try {
            boolean isAdded = CustomerModel.save(customer);

            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Added!").show();
                emptyTextField(customer);
                getAllData();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
                emptyTextField(customer);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        Customer customer = new Customer();
       emptyTextField(customer);

    }

    public Customer getData(Customer customer) {
        String ID = txtId.getText();
        String Name = txtName.getText();
        String Address = txtAddress.getText();
        String contact = txtTelNumber.getText();

        return customer;
    }

    private Customer emptyTextField(Customer customer) {
        getData(customer);
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtTelNumber.clear();
        return customer;
    }

    public void onClickBack(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.HOME, pane);
    }

    public void keyPressedEnterOnAction(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String id = txtId.getText();


            try {
                Customer customer = CustomerModel.search(id);
                if (customer != null) {
                    fillData(customer);

                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void getAllData() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> AllData = CustomerModel.getAllCustomer();
        ObservableList<Customer> observableList = FXCollections.observableArrayList();
        for (Customer customer :
                AllData) {
            observableList.add(new Customer(
                    customer.getId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getContact()
            ));
        }
        tblCustomer.setItems(observableList);
    }

    public void btnSearchOnAction(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String id = lblsearch.getText();


            try {
                Customer customer = CustomerModel.search(id);
                if (customer != null) {
                    fillData(customer);
                    lblsearch.clear();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void name(KeyEvent keyEvent) {
        Pattern pattern=Pattern.compile("\\b([a-z]|[A-Z])+");
        if (!pattern.matcher(txtName.getText()).matches()){
            txtName.setStyle("-fx-text-fill: red");
        }else {
            txtName.setStyle("-fx-text-fill: black");

        }
    }

    public void address(KeyEvent keyEvent) {
        Pattern pattern=Pattern.compile("\\b([a-z]|[A-Z])+");
        if (!pattern.matcher(txtAddress.getText()).matches()){
            txtAddress.setStyle("-fx-text-fill: red");
        }else {
            txtAddress.setStyle("-fx-text-fill: black");

        }
    }

    public void contact(KeyEvent keyEvent) {
        Pattern pattern=Pattern.compile("0((11)|(7(7|0|8|4|9|1|[3-7]))|(3[1-8])|(4(1|5|7))|(5(1|2|4|5|7))|(6(3|[5-7]))|([8-9]1))[0-9]{7}");
        if (!pattern.matcher(txtTelNumber.getText()).matches()){
            txtTelNumber.setStyle("-fx-text-fill: red");
        }else {
            txtTelNumber.setStyle("-fx-text-fill: black");

        }
    }

    /*public void setPatterns() {
        Pattern idPattern = Pattern.compile("^(C0)([0-9]{1})([0-9]{1})$");
        Matcher idMatcher = idPattern.matcher(txtId.getText());

        Pattern namePattern = Pattern.compile("^[a-zA-Z\\s]+");
        nameMatcher = namePattern.matcher(txtName.getText());


        Pattern addressPattern = Pattern.compile("^([a-zA-Z0-9\\/,\\s])+([a-zA-z\\s,])+([a-zA-z])$");
        addressMatcher = addressPattern.matcher(txtAddress.getText());

        Pattern teleNumPattern = Pattern.compile("^(074|075|072|077|071|078)([0-9]{7}$)");
        teleNumMatcher = teleNumPattern.matcher(txtTelNumber.getText());
    }*/

}



