package lk.ijse.MilkBar.Controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.MilkBar.model.EmployeeModel;
import lk.ijse.MilkBar.model.UserModel;
import lk.ijse.MilkBar.to.User;
import lk.ijse.MilkBar.util.Navigation;
import lk.ijse.MilkBar.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class UserFormController implements Initializable {
    public TextField txtId;
    public TextField txtName;
    public AnchorPane pane;
    public TextField txtPassword;
    public TextField txtFullName;
    public TextField txtRole;
    public JFXComboBox txtEmpID;
    public JFXTextField txtUserName;

    public void btnRemoveOnAction(ActionEvent actionEvent) {
        User user = new User();
        emptyTextField(user);
    }
    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String ID = txtId.getText();
        String Name = txtName.getText();
        String password = txtPassword.getText();
        String fullName = txtFullName.getText();

        User user = new User(ID,Name,password,fullName);
        boolean isUpdated = UserModel.update(user);
        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Update Supplier").show();
            emptyTextField(user);
        }else {
            new Alert(Alert.AlertType.WARNING,"Something Happened").show();
            emptyTextField(user);
        }
    }
    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String ID= String.valueOf(txtEmpID.getValue());
        User  user = new User(ID);
        UserModel userModel = new UserModel();

        boolean isDeleted = userModel.delete(user);
        if(isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Supplier Delete!").show();
            emptyTextField(user);
        } else {
            new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            emptyTextField(user);
        }
    }
    public void btnSaveOnAction(ActionEvent actionEvent) {
        String empId = String.valueOf(txtEmpID.getValue());
        String name = txtUserName.getText();
        String password = txtPassword.getText();
        String role = txtRole.getText();

        User user = new User( empId,name, password,role);
        try {
            boolean isAdded = UserModel.save(user);

            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void fillData(User user) {
        txtUserName.setText(user.getUserName());
        txtPassword.setText(user.getUserPassword());
        txtRole.setText(user.getRole());
    }
    public User getData(User user) {
        String Name = txtUserName.getText();
        String password = txtPassword.getText();
        String role = txtRole.getText();
        return user;
    }
    private User emptyTextField(User user) {
        getData(user);
        txtUserName.clear();
        txtPassword.clear();
        txtRole.clear();
        return user;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadEmployeeID();
    }

    public void onClickBackAction(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.HOME,pane);
    }
    private void loadEmployeeID() {
        ArrayList<String> ids = new ArrayList<>();
        try {
            ResultSet set = EmployeeModel.getAllIds();
            while (set.next()) {
                ids.add(set.getString(1));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        txtEmpID.getItems().addAll(ids);
    }
}
