/**
 * @auhtor Ramadan ismaeL
 */

package employee_management_system;

import javafx.application.Application;
import javafx.stage.Stage;

import employee_management_system.Models.ModelLogin;

public class App extends Application {

    @SuppressWarnings("exports")
    @Override
    public void start(Stage stage) {
        ModelLogin.getInstance().getViewsFactory().showLoginWindow();
    }
    public static void main(String[] args) {
        launch();
    }

}