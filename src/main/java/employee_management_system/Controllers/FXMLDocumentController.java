/**
 * @auhtor Ramadan ismaeL
 */

package employee_management_system.Controllers;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

import employee_management_system.Models.ConexaoDAO;
import employee_management_system.Models.ModelLogin;
import employee_management_system.Models.getData;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class FXMLDocumentController implements Initializable {
    @SuppressWarnings("exports")
    public TextField username_fld;
    @SuppressWarnings("exports")
    public PasswordField password_fld;
    @SuppressWarnings("exports")
    public Button login_btn;
    @SuppressWarnings("exports")
    public Button close;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        close.setOnAction(event -> close());
        login_btn.setOnAction(event -> loginAdmin());
    }

    private void close() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(" Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to live?");
        Optional<ButtonType> option = alert.showAndWait();

        if(option.get().equals(ButtonType.OK)) {
            System.exit(0);
        }
    }

    public void loginAdmin() {
        String sql = "select * from admin where username = ? and password = ?";

        connect = ConexaoDAO.connectDAO();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username_fld.getText());
            prepare.setString(2, password_fld.getText());

            result = prepare.executeQuery();
            Alert alert;

            if(username_fld.getText().isEmpty() || password_fld.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                if(result.next()) {
                    getData.username = username_fld.getText();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login");
                    alert.showAndWait();

                    login_btn.getScene().getWindow().hide();
                    ModelLogin.getInstance().getViewsFactory().showDashboardWindow();
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
                }
            }
        } catch(Exception error) {
            error.printStackTrace();
        } finally {
            try {
                if (connect != null && !connect.isClosed()) {
                    connect.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
}
