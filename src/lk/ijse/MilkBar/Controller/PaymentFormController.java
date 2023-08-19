package lk.ijse.MilkBar.Controller;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.MilkBar.util.Navigation;
import lk.ijse.MilkBar.util.Routes;

import java.io.IOException;

public class PaymentFormController {
    public AnchorPane pane;

    public void btnBackOnAction(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.HOME,pane);
    }

    public void CustomerClickedOnAction(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.CUSTOMER_PAYMENT,pane);
    }

    public void SupplierPaymentClickedOnAction(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.SUPPLIER_PAYMENT,pane);
    }
}
