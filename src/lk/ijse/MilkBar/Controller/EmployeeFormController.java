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
import lk.ijse.MilkBar.model.EmployeeModel;
import lk.ijse.MilkBar.to.Customer;
import lk.ijse.MilkBar.to.Employee;
import lk.ijse.MilkBar.util.Navigation;
import lk.ijse.MilkBar.util.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class EmployeeFormController {
    public AnchorPane pane;
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtEmail;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colEmail;
    public TableColumn colSalary;
    public TextField txtSalary;
    public JFXTextField txtSearch;
    public TableView <Employee>tblEmployee;

    public void initialize () throws SQLException, ClassNotFoundException {

        tblEmployee.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblEmployee.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("Name"));
        tblEmployee.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Address"));
        tblEmployee.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("Email"));
        tblEmployee.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("Salary"));

        getAllData();
    }
    public void btnRemoveOnAction(ActionEvent actionEvent) {
        Employee employee = new Employee();
        emptyTextField(employee);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String ID= txtId.getText();
        Employee employee = new Employee(ID);
        EmployeeModel employeeModel = new EmployeeModel();

        boolean isDeleted = employeeModel.delete(employee);

        if(isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Employee Delete!").show();
            emptyTextField(employee);
            getAllData();
        } else {
            new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            emptyTextField(employee);
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String ID = txtId.getText();
        String Name = txtName.getText();
        String Address = txtAddress.getText();
        String email = txtEmail.getText();
        double salary = Double.parseDouble(txtSalary.getText());


        Employee employee = new Employee(ID,Name,Address,email,salary);
        boolean isUpdated = EmployeeModel.update(employee);
        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Update Employee").show();
            emptyTextField(employee);
            getAllData();
        }else {
            new Alert(Alert.AlertType.WARNING,"Something Happened").show();
            emptyTextField(employee);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        double salary = Double.parseDouble(txtSalary.getText());

        Employee employee = new Employee(id, name, address, email,salary);
        try {
            boolean isAdded = EmployeeModel.save(employee);

            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Added!").show();
                emptyTextField(employee);
                getAllData();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
                emptyTextField(employee);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void EnterKeyPressedOnAction(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String id = txtId.getText();


            try {
                Employee employee = EmployeeModel.search(id);
                if (employee != null) {
                    fillData(employee);

                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void txtSearchOnAction(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String id = txtSearch.getText();


            try {
                Employee employee = EmployeeModel.search(id);
                if (employee != null) {
                    fillData(employee);
                    txtSearch.clear();

                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void onClickBackButton(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.HOME, pane);
    }
    public void getAllData() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> AllData = EmployeeModel.getAllEmployee();
        ObservableList<Employee> observableList = FXCollections.observableArrayList();
        for (Employee employee :
                AllData) {
            observableList.add(new Employee(
                    employee.getId(),
                    employee.getName(),
                    employee.getAddress(),
                    employee.getEmail(),
                    employee.getSalary()

            ));
        }
        tblEmployee.setItems(observableList);
    }
    private void fillData(Employee employee) {
        txtId.setText(employee.getId());
        txtName.setText(employee.getName());
        txtAddress.setText(employee.getAddress());
        txtEmail.setText(employee.getEmail());
        txtSalary.setText(String.valueOf(employee.getSalary()));
    }
    public Employee getData(Employee employee) {
        String ID = txtId.getText();
        String Name = txtName.getText();
        String Address = txtAddress.getText();
        String email = txtEmail.getText();
        double salary = Double.parseDouble(txtSalary.getText());

        return employee;
    }

    private Employee emptyTextField(Employee employee) {
        getData(employee);
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtEmail.clear();
        txtSalary.clear();
        return employee;
    }
}
