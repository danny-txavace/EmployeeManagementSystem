/**
 * @auhtor Ramadan ismaeL
 */

package employee_management_system.Models;

import java.sql.*;

public class ConexaoDAO {
    static Connection conexao;
    //static String driver = "org.sqlite.JDBC";
    private static final String url = "jdbc:sqlite:employee.db";

    @SuppressWarnings("exports")
    public static Connection connectDAO() {
        try {
            //Class.forName(driver);
            conexao = DriverManager.getConnection(url);
            System.out.println("Conectado!");
            return conexao;
        } /*catch(ClassNotFoundException erro) {
            System.out.println("Driver do banco de dados não localizado !");
            return null;
        }*/ catch(SQLException error) {
            error.printStackTrace();
        } catch(Exception error) {
            try {
                if (conexao != null && !conexao.isClosed()) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            error.printStackTrace();
        }
        return null;
    }
}
