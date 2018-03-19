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
    private static final String LECTORS_TABLE_NAME = "LECTORS";
    private static final String DEPARTMENTS_TABLE_NAME = "DEPARTMENTS";
    private static final String LECTORS_TO_DEPARTMENTS_TABLE_NAME = "LECTORS_TO_DEPARTMENTS";

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

     /*   String createTableLectorsSQL = "CREATE TABLE LECTORS (" +
                "id int," +
                "LectorName varchar(255)," +
                "salary int," +
                "lectorDegree varchar(255)," +
                "createDepartmentsTable varchar(255))";

        String createTableDepartmensSQL = "CREATE TABLE DEPARTMENS (" +
                "id int," +
                "LectorName varchar(255)," +
                "salary int," +
                "departmentName varchar(255)," +
                "headOfDepartment varchar(255))";*/

        String dropLectorsTable = "DROP TABLE IF EXISTS `" + LECTORS_TABLE_NAME + "` ;";
        String dropDepartmentsTable = "DROP TABLE IF EXISTS `" + DEPARTMENTS_TABLE_NAME + "` ;";

        String createLectorsTable = "CREATE TABLE " + LECTORS_TABLE_NAME + " \n" +
                "( aid INT NOT NULL,\n" +
                "  bid INT NOT NULL,\n" +
                "  CONSTRAINT a_pk PRIMARY KEY (aid) \n" +
                " );";


        String createDepartmentsTable = "CREATE TABLE " + DEPARTMENTS_TABLE_NAME + " \n" +
                "( bid INT NOT NULL,\n" +
                "  aid INT NOT NULL,\n" +
                "  CONSTRAINT b_pk PRIMARY KEY (bid) \n" +
                " );";

        String createLectorsToDepartments = "CREATE TABLE " + LECTORS_TO_DEPARTMENTS_TABLE_NAME + " \n" +
                "( aid INT NOT NULL,\n" +
                "  bid INT NOT NULL,\n" +
                "  CONSTRAINT r_pk PRIMARY KEY (aid, bid),\n" +
                "  CONSTRAINT a_r_fk FOREIGN KEY (aid) REFERENCES " + LECTORS_TABLE_NAME + " (aid),  \n" +
                "  CONSTRAINT b_r_fk FOREIGN KEY (bid) REFERENCES " + DEPARTMENTS_TABLE_NAME + " (bid)\n" +
                " );";



        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

          /*  System.out.println(createTableLectorsSQL);
            statement.execute(createTableLectorsSQL);
            System.out.println(createTableDepartmensSQL);
            statement.execute(createTableDepartmensSQL);*/

          statement.execute(dropLectorsTable);
            System.out.println(LECTORS_TABLE_NAME + " drop sucsessfull");

            statement.execute(dropDepartmentsTable);
            System.out.println(DEPARTMENTS_TABLE_NAME + " drop sucsessfull");

            System.out.println(createLectorsTable);
            statement.execute(createLectorsTable);
            System.out.println(LECTORS_TABLE_NAME + " create sucsassful");

            System.out.println(createDepartmentsTable);
            statement.execute(createDepartmentsTable);
            System.out.println(DEPARTMENTS_TABLE_NAME + " create sucsassful");

            System.out.println(createLectorsToDepartments);
            statement.execute(createLectorsToDepartments);
            System.out.println(LECTORS_TO_DEPARTMENTS_TABLE_NAME + " create sucsassful");


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
