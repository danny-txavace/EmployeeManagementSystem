module employee_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires javafx.graphics;
    requires javafx.base;
    requires org.xerial.sqlitejdbc;
    requires java.sql;

    opens employee_management_system to javafx.fxml;
    exports employee_management_system;
    exports employee_management_system.Controllers;
    exports employee_management_system.Models;
    exports employee_management_system.Views;
}
