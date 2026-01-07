/**
 * @author Ramadan ismaeL
 */

package employee_management_system.Controllers;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import employee_management_system.Models.ConexaoDAO;
import employee_management_system.Models.ModelLogin;
import employee_management_system.Models.employeeData;
import employee_management_system.Models.getData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DashboardController implements Initializable {
    private Alert alert;
    private String css = this.getClass().getResource("/employee_management_system/Styless/dashboardDesign.css").toExternalForm();
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement prepare = null;
    private ResultSet result = null;
    private Image image;    

    @SuppressWarnings("exports")
    public AnchorPane main_form;
    @SuppressWarnings("exports")
    public Button addEmployee_add_btn;
    @SuppressWarnings("exports")
    public Button addEmployee_btn;
    @SuppressWarnings("exports")
    public Button addEmployee_clear_btn;
    public TableView<employeeData> addEmployee_tableView;
    public TableColumn<employeeData, String> addEmployee_col_date;
    public TableColumn<employeeData, String> addEmployee_col_employeeID;
    public TableColumn<employeeData, String> addEmployee_col_firstName;
    public TableColumn<employeeData, String> addEmployee_col_gender;
    public TableColumn<employeeData, String> addEmployee_col_lastName;
    public TableColumn<employeeData, String> addEmployee_col_phone;
    public TableColumn<employeeData, String> addEmployee_col_position;
    @SuppressWarnings("exports")
    public Button addEmployee_delete_btn;
    @SuppressWarnings("exports")
    public TextField addEmployee_employeeID_fld;
    @SuppressWarnings("exports")
    public TextField addEmployee_firstName_fld;
    @SuppressWarnings("exports")
    public AnchorPane addEmployee_form;
    public ComboBox<String> addEmployee_gender_cb;
    @SuppressWarnings("exports")
    public Button addEmployee_import_btn;
    @SuppressWarnings("exports")
    public TextField addEmployee_lastName_fld;
    @SuppressWarnings("exports")
    public TextField addEmployee_phone_fld;
    public ComboBox<String> addEmployee_position_cb;
    @SuppressWarnings("exports")
    public TextField addEmployee_search;    
    @SuppressWarnings("exports")
    public Button addEmployee_update_btn;
    @SuppressWarnings("exports")
    public Button close;
    @SuppressWarnings("exports")
    public Button home_btn;
    public BarChart<String, Number> home_chart;
    @SuppressWarnings("exports")
    public AnchorPane home_form;
    @SuppressWarnings("exports")
    public Label home_totalEmployees;
    @SuppressWarnings("exports")
    public Label home_totalInactiveEmployees;
    @SuppressWarnings("exports")
    public Label home_totalPresents;
    @SuppressWarnings("exports")
    public Button logout;
    @SuppressWarnings("exports")
    public Button minimize;
    @SuppressWarnings("exports")
    public Button salary_btn;
    @SuppressWarnings("exports")
    public Button salary_clear_btn;
    public TableView<employeeData> salary_tableView;
    public TableColumn<employeeData, String> salary_col_employeeID;
    //public TableColumn<?, ?> salary_col_firstName;
    public TableColumn<employeeData, String> salary_col_firstName;
    public TableColumn<employeeData, String> salary_col_lastName;
    public TableColumn<employeeData, String> salary_col_position;
    public TableColumn<employeeData, String> salary_col_salary;
    @SuppressWarnings("exports")
    public Label salary_employeeID_fld;
    @SuppressWarnings("exports")
    public Label salary_firstName_lbl;
    @SuppressWarnings("exports")
    public AnchorPane salary_form;
    @SuppressWarnings("exports")
    public Label salary_lastName_lbl;
    @SuppressWarnings("exports")
    public Label salary_position_lbl;
    @SuppressWarnings("exports")
    public TextField salary_salary_fld;    
    @SuppressWarnings("exports")
    public Button salary_udpate_btn;
    @SuppressWarnings("exports")
    public Label username_lbl;
    @SuppressWarnings("exports")
    public ImageView addEmployee_image;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        home_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #3a4368, #28966c)");
        close.setOnAction(event -> close());
        minimize.setOnAction(event -> minimize());

        logout.setOnAction(event -> logout());
        home_btn.setOnAction(event -> homeView());
        addEmployee_btn.setOnAction(event -> addEmployeeView());
        salary_btn.setOnAction(event -> salaryView());

        addEmployee_tableView.setOnMouseClicked(event -> addEmployeeSelect());
        addEmployee_import_btn.setOnAction(event -> addEmployeeInsertImage());
        addEmployee_add_btn.setOnAction(event -> addEmployeeAdd());
        addEmployee_clear_btn.setOnAction(event -> addEmployeeReset());
        addEmployee_gender_cb.setOnAction(event -> addEmployeeGenderList());
        addEmployee_position_cb.setOnAction(event -> addEmployeePositionList());
        addEmployee_update_btn.setOnAction(event -> addEmployeeUpdate());
        addEmployee_delete_btn.setOnAction(event -> addEmployeeDelete());
        addEmployee_search.setOnKeyTyped(event -> addEmployeeSearch());

        salary_tableView.setOnMouseClicked(event -> salarySelect());
        salary_clear_btn.setOnAction(event -> salaryReset());
        salary_udpate_btn.setOnAction(event -> salaryUpdate());

        //displayUsername();        
        addEmployeeShowListData();
        addEmployeeGenderList();
        addEmployeePositionList();
        addEmployeeSearch();
        salaryShowListData();

        homeChart();
        homeEmployeeTotalPresent();
        homeTotalInactive();
        homeTotalEmployees();
    }

    public void close() {
        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(" Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to live?");
        Optional<ButtonType> option = alert.showAndWait();

        if(option.get().equals(ButtonType.OK)) {
            try {
                if (connect != null && !connect.isClosed()) {
                    connect.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }

    public void minimize() {
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void logout() {
        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(" Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> option = alert.showAndWait();

        if(option.get().equals(ButtonType.OK)) {
            logout.getScene().getWindow().hide();
            ModelLogin.getInstance().getViewsFactory().showLoginWindow();
        }
    }

    public void homeView() {
        homeChart();
        homeEmployeeTotalPresent();
        homeTotalInactive();
        homeTotalEmployees();
        
        home_form.setVisible(true);
        addEmployee_form.setVisible(false);
        salary_form.setVisible(false);
        
        home_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #3a4368, #28966c)");
        addEmployee_btn.setStyle(css);
        salary_btn.setStyle(css);
    }

    public void addEmployeeView() {
        home_form.setVisible(false);
        addEmployee_form.setVisible(true);
        salary_form.setVisible(false);

        home_btn.setStyle(css);
        addEmployee_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #3a4368, #28966c)");
        salary_btn.setStyle(css);
    }

    public void salaryView() {
        home_form.setVisible(false);
        addEmployee_form.setVisible(false);
        salary_form.setVisible(true);

        home_btn.setStyle(css);
        addEmployee_btn.setStyle(css);
        salary_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #3a4368, #28966c)");
    }

    public void displayUsername() {
        username_lbl.setText(getData.username);
    }









    // ADD EMPLOYEE

    public ObservableList<employeeData> addEmployeeListData() {
        ObservableList<employeeData> listData = FXCollections.observableArrayList();
        String sql = "select * from employee";

        connect = ConexaoDAO.connectDAO();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            employeeData employeeDt;

            while(result.next()) {
                employeeDt = new employeeData(result.getString("employee_id"), result.getString("firstName"), result.getString("lastName"), result.getString("gender"), result.getString("phone"), result.getString("position"), result.getString("image"), result.getString("date"));
                listData.add(employeeDt);
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

        return listData;
    }    

    private ObservableList<employeeData> addEmployeeList;
    public void addEmployeeShowListData() {
        addEmployeeList = addEmployeeListData();

        addEmployee_col_employeeID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        addEmployee_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        addEmployee_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addEmployee_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addEmployee_col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addEmployee_col_position.setCellValueFactory(new PropertyValueFactory<>("position"));
        addEmployee_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        addEmployee_tableView.setItems(addEmployeeList);
    }

    public void addEmployeeSelect() {
        employeeData employeeDt = addEmployee_tableView.getSelectionModel().getSelectedItem();
        int num = addEmployee_tableView.getSelectionModel().getSelectedIndex();

        if((num -1)< -1) {return;}

        addEmployee_employeeID_fld.setText(String.valueOf(employeeDt.getEmployeeID()));
        addEmployee_firstName_fld.setText(employeeDt.getFirstName());
        addEmployee_lastName_fld.setText(employeeDt.getLastName());
        addEmployee_phone_fld.setText(employeeDt.getPhone());

        getData.path = employeeDt.getImage();

        String uri = "file:"+employeeDt.getImage();
        image = new Image(uri, 112, 126, false, true);
        addEmployee_image.setImage(image);
    }    

    public void addEmployeeInsertImage() {
        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if(file != null) {
            getData.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 112, 126, false, true);
            addEmployee_image.setImage(image);
        }
    }

    public void addEmployeeAdd() {        
        String sql = "INSERT INTO employee(employee_id, firstName, lastName, gender, phone, position, image, date) VALUES(?, ?, ?, ?, ?, ?, ?, date('now'))";

        try{
            connect = ConexaoDAO.connectDAO();

            if(addEmployee_employeeID_fld.getText().isEmpty() || addEmployee_firstName_fld.getText().isEmpty() || addEmployee_lastName_fld.getText().isEmpty() || addEmployee_gender_cb.getSelectionModel().getSelectedItem() == null || addEmployee_phone_fld.getText().isEmpty() || addEmployee_position_cb.getSelectionModel().getSelectedItem() == null || getData.path == null || getData.path == "") {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields!");
                alert.showAndWait();
            } else {
                String check = " select employee_id from employee where employee_id = '"+addEmployee_employeeID_fld.getText()+"'";
                statement = connect.createStatement();
                result = statement.executeQuery(check);
                if(result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Employee ID: "+addEmployee_employeeID_fld.getText()+" was already exist!");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(sql);
            
                    prepare.setString(1, addEmployee_employeeID_fld.getText());
                    prepare.setString(2, addEmployee_firstName_fld.getText());
                    prepare.setString(3, addEmployee_lastName_fld.getText());
                    prepare.setString(4, (String)addEmployee_gender_cb.getSelectionModel().getSelectedItem());
                    prepare.setString(5, addEmployee_phone_fld.getText());
                    prepare.setString(6, (String)addEmployee_position_cb.getSelectionModel().getSelectedItem());

                    String uri = getData.path;
                    uri = uri.replace("\\", "\\\\");

                    prepare.setString(7, uri);
                    prepare.executeUpdate();

                    String insertInfo = "INSERT INTO employee_info(employee_id, firstName, lastName, position, salary, date) VALUES(?,?,?,?,?,date('now'))";

                    prepare = connect.prepareStatement(insertInfo);
                    prepare.setString(1, addEmployee_employeeID_fld.getText());
                    prepare.setString(2, addEmployee_firstName_fld.getText());
                    prepare.setString(3, addEmployee_lastName_fld.getText());
                    prepare.setString(4, (String)addEmployee_position_cb.getSelectionModel().getSelectedItem());
                    prepare.setDouble(5, 0.0);
                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    
                    addEmployeeShowListData();
                    salaryShowListData();
                    addEmployeeReset();
                }
            }
        } catch(SQLException error) {
            System.out.println(error.getMessage());
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

    public void addEmployeeReset() {
        addEmployee_employeeID_fld.setText("");
        addEmployee_firstName_fld.setText("");
        addEmployee_lastName_fld.setText("");
        addEmployee_gender_cb.getSelectionModel().clearSelection();
        addEmployee_phone_fld.setText("");
        addEmployee_position_cb.getSelectionModel().clearSelection();
        addEmployee_image.setImage(null);
        getData.path = "";
    }

    private String[] positionList = {"Marketer Coordinator", "Web Developer (Back End)", "Web Developer (Front End)", "App Developer"};
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void addEmployeePositionList() {
        List<String> listP = new ArrayList<>();

        for(String data: positionList) {
            listP.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listP);
        addEmployee_position_cb.setItems(listData);
    }

    private String[] genderList = {"Male", "Female", "Others"};
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void addEmployeeGenderList() {
        List<String> listG = new ArrayList<>();

        for(String data: genderList) {
            listG.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listG);
        addEmployee_gender_cb.setItems(listData);
    }

    public void addEmployeeUpdate() {
        String uri = getData.path;
        if(uri != null) {
            uri = uri.replace("\\", "\\\\");
        }

        String sql = "UPDATE employee SET firstName = '"
        +addEmployee_firstName_fld.getText()+"', lastName = '"
        +addEmployee_lastName_fld.getText()+"', gender = '"
        +addEmployee_gender_cb.getSelectionModel().getSelectedItem()+"', phone = '"
        +addEmployee_phone_fld.getText()+"', position = '"
        +addEmployee_position_cb.getSelectionModel().getSelectedItem()+"', image = '"
        +uri+"', date = date('now') WHERE employee_id = '"
        +addEmployee_employeeID_fld.getText()+"'";

        connect = ConexaoDAO.connectDAO();

        try {
            if(addEmployee_employeeID_fld.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields!");
                alert.showAndWait();
            } else {
                String check = " select employee_id from employee where employee_id = '"+addEmployee_employeeID_fld.getText()+"'";
                statement = connect.createStatement();
                result = statement.executeQuery(check);
                if(result.next()) {
                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to UPDATE Employee ID: "+addEmployee_employeeID_fld.getText()+"?");
                    Optional<ButtonType> option = alert.showAndWait();

                    if(option.get().equals(ButtonType.OK)) {
                        statement = connect.createStatement();                   
                        statement.executeUpdate(sql);

                        String updateInfo = "UPDATE employee_info SET firstName = '"
                            +addEmployee_firstName_fld.getText()+"', lastName = '"
                            +addEmployee_lastName_fld.getText()+"', position = '"
                            +addEmployee_position_cb.getSelectionModel().getSelectedItem()
                            +"', date = date('now') WHERE employee_id = '"
                            +addEmployee_employeeID_fld.getText()+"'";
                        statement = connect.createStatement();                   
                        statement.executeUpdate(updateInfo);
                        
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Updated!");
                        alert.showAndWait();
                        
                        addEmployeeShowListData();
                        salaryShowListData();
                        addEmployeeReset();
                    }
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Employee ID: "+addEmployee_employeeID_fld.getText()+" does not exist. Please select an employee from the table!");
                    alert.showAndWait();
                }              
            }
        } catch(SQLException error) {
            System.out.println(error.getMessage());
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

    public void addEmployeeDelete() {
        String sql = "DELETE FROM employee WHERE employee_id = '"+addEmployee_employeeID_fld.getText()+"'";

        connect = ConexaoDAO.connectDAO();

        try {
            if(addEmployee_employeeID_fld.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields!");
                alert.showAndWait();
            } else {
                String check = " select employee_id from employee where employee_id = '"+addEmployee_employeeID_fld.getText()+"'";
                statement = connect.createStatement();
                result = statement.executeQuery(check);
                if(result.next()) {
                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to DELETE Employee ID: "+addEmployee_employeeID_fld.getText()+"?");
                    Optional<ButtonType> option = alert.showAndWait();

                    if(option.get().equals(ButtonType.OK)) {
                        statement = connect.createStatement();                   
                        statement.executeUpdate(sql);

                        String deletedInfo = "DELETE FROM employee_info WHERE employee_id = '"+addEmployee_employeeID_fld.getText()+"'";
                        statement = connect.createStatement();                   
                        statement.executeUpdate(deletedInfo);
                        
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Deleted!");
                        alert.showAndWait();
                        
                        addEmployeeShowListData();
                        salaryShowListData();
                        addEmployeeReset();
                    }
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Employee ID: "+addEmployee_employeeID_fld.getText()+" does not exist. Please select an employee from the table!");
                    alert.showAndWait();
                }
            }
        } catch(SQLException error) {
            System.out.println(error.getMessage());
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

    public List<employeeData> searchEmployees(String searchKey) {
        List<employeeData> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee WHERE employee_id LIKE ? OR "
                   + "firstName LIKE ? OR lastName LIKE ? OR "
                   + "gender LIKE ? OR phone LIKE ? OR position LIKE ? OR date LIKE ?";
    
        connect = ConexaoDAO.connectDAO();
        try {
             prepare = connect.prepareStatement(sql);
    
            String likeKey = "%" + searchKey + "%"; // Para busca que contém
            for (int i = 1; i <= 7; i++) {
                prepare.setString(i, likeKey);
            }
    
            ResultSet rs = prepare.executeQuery();
    
            while (rs.next()) {
                employeeData employee = new employeeData();
                employee.setEmployeeID(rs.getString("employee_id"));
                employee.setFirstName(rs.getString("firstName"));
                employee.setLastName(rs.getString("lastName"));
                employee.setGender(rs.getString("gender"));
                employee.setPhone(rs.getString("phone"));
                employee.setPosition(rs.getString("position"));
                employee.setDate(rs.getString("date"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
        return employees;
    }

    public void addEmployeeSearch() {
        addEmployee_search.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                addEmployee_tableView.setItems(addEmployeeList); // Exibe a lista completa se a busca estiver vazia
            } else {
                List<employeeData> filteredEmployees = searchEmployees(newValue.toLowerCase());
                addEmployee_tableView.setItems(FXCollections.observableArrayList(filteredEmployees));
            }
        });
    }













    // EMPLOYEE SALARY

    public ObservableList<employeeData> salaryListData() {
        ObservableList<employeeData> listaData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM employee_info";

        connect = ConexaoDAO.connectDAO();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            employeeData employeeDt;

            while(result.next()) {
                employeeDt = new employeeData(result.getString("employee_id"), result.getString("firstname"), result.getString("lastName"), result.getString("position"), result.getDouble("salary"));

                listaData.add(employeeDt);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connect != null && !connect.isClosed()) {
                    connect.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return listaData;
    }

    private ObservableList<employeeData> salaryList;
    public void salaryShowListData() {
        salaryList = salaryListData();

        salary_col_employeeID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        salary_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        salary_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        salary_col_position.setCellValueFactory(new PropertyValueFactory<>("position"));
        salary_col_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        salary_tableView.setItems(salaryList);
    }

    public void salarySelect() {
        employeeData employeeDt = salary_tableView.getSelectionModel().getSelectedItem();
        int num = salary_tableView.getSelectionModel().getSelectedIndex();

        if((num -1)< -1) {return;}

        salary_employeeID_fld.setText(employeeDt.getEmployeeID());
        salary_firstName_lbl.setText(employeeDt.getFirstName());
        salary_lastName_lbl.setText(employeeDt.getLastName());
        salary_position_lbl.setText(employeeDt.getPosition());
        salary_salary_fld.setText(String.valueOf(employeeDt.getSalary()));
    }

    public void salaryReset() {
        salary_employeeID_fld.setText("");
        salary_firstName_lbl.setText("");
        salary_lastName_lbl.setText("");
        salary_position_lbl.setText("");
        salary_salary_fld.setText("");
    }

    public void salaryUpdate() {
        String sql = "UPDATE employee_info SET firstName = '"
        +salary_firstName_lbl.getText()+"', lastName = '"
        +salary_lastName_lbl.getText()+"', position = '"
        +salary_position_lbl.getText()+"', salary = '"
        +salary_salary_fld.getText()
        +"', date = date('now') WHERE employee_id = '"
        +salary_employeeID_fld.getText()+"'";

        connect = ConexaoDAO.connectDAO();
        try{
            if(salary_employeeID_fld.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select an employee from the table!");
                alert.showAndWait();            
            } else {
                if(salary_salary_fld.getText().isEmpty() || salary_salary_fld.getText().isBlank()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all blank fields!");
                    alert.showAndWait();
                } else {
                    String check = " select employee_id from employee_info where employee_id = '"+salary_employeeID_fld.getText()+"'";
                    statement = connect.createStatement();
                    result = statement.executeQuery(check);
                    if(result.next()) {
                        alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Are you sure you want to UPDATE Employee Salary with ID: "+addEmployee_employeeID_fld.getText()+"?");
                        Optional<ButtonType> option = alert.showAndWait();
    
                        if(option.get().equals(ButtonType.OK)) {
                            statement = connect.createStatement();                   
                            statement.executeUpdate(sql);
                            
                            alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Information Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Successfully Updated!");
                            alert.showAndWait();
                            
                            salaryShowListData();
                            salaryReset();
                        }
                    }
                }
            }            
        } catch(SQLException error) {
            System.out.println(error.getMessage());
            try {
                if (connect != null && !connect.isClosed()) {
                    connect.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } 
    }



    





    // HOME
    public void homeTotalEmployees() {
        String sql = "SELECT COUNT(id) FROM employee";

        connect = ConexaoDAO.connectDAO();
        int countData = 0;
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()) {
                countData = result.getInt("COUNT(id)");
            } 

            home_totalEmployees.setText(String.valueOf(countData));

        } catch(SQLException error) {
            System.out.println(error.getMessage());
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

    public void homeEmployeeTotalPresent() {
        String sql = "SELECT COUNT(id) FROM employee_info";

        connect = ConexaoDAO.connectDAO();
        int countData = 0;
        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            while(result.next()) {
                countData = result.getInt("COUNT(id)");
            }
            home_totalPresents.setText(String.valueOf(countData));
        } catch(SQLException error) {
            System.out.println(error.getMessage());
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

    public void homeTotalInactive() {
        String sql = "SELECT COUNT(id) FROM employee_info WHERE salary = '0.0'";

        connect = ConexaoDAO.connectDAO();
        int countData = 0;
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()) {
                countData = result.getInt("COUNT(id)");
            }
            home_totalInactiveEmployees.setText(String.valueOf(countData));
        } catch(SQLException error) {
            System.out.println(error.getMessage());
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

    public void homeChart() {
        home_chart.getData().clear();

        String sql = "SELECT date, COUNT(id) FROM employee GROUP BY date ORDER BY date ASC LIMIT 7";

        connect = ConexaoDAO.connectDAO();
        try{
            XYChart.Series<String, Number> chart = new XYChart.Series<>();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
            }
            home_chart.getData().add(chart);
        } catch(SQLException error) {
            System.out.println(error.getMessage());
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
