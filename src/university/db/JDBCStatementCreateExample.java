package university.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class JDBCStatementCreateExample {
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/mynewdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public static void main(String[] argv) {

        try {

            createDbUserTable();

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }
    }

    private static void createDbUserTable() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

     /*   String createTableSQL = "CREATE TABLE Persons (" +
                "PersonID int," +
                "LastName varchar(255)," +
                "FirstName varchar(255)," +
                "Address varchar(255)," +
                "City varchar(255))";*/

        String createTableLectorsSQL = "CREATE TABLE LECTORS (" +
                "id int," +
                "LectorName varchar(255)," +
                "salary int," +
                "lectorDegree varchar(255)," +
                "departments varchar(255))";

        String createTableDepartmensSQL = "CREATE TABLE DEPARTMENS (" +
                "id int," +
                "LectorName varchar(255)," +
                "salary int," +
                "departmentName varchar(255)," +
                "headOfDepartment varchar(255))";

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            System.out.println(createTableLectorsSQL);
            statement.execute(createTableLectorsSQL);
            System.out.println(createTableDepartmensSQL);
            statement.execute(createTableDepartmensSQL);


            System.out.println("Table \"dbuser\" is created!");

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }

    }

    private static Connection getDBConnection() {

        Connection dbConnection = null;

        try {

            Class.forName(DB_DRIVER);

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            dbConnection = DriverManager.getConnection(
                    DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return dbConnection;

    }

}
