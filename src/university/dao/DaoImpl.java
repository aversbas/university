package university.dao;

import university.entity.Department;
import university.entity.Lector;

import java.sql.*;
import java.util.List;

import static university.dao.sql.Insert.*;

public class DaoImpl implements Dao {
    public static final String DB_DRIVER = "org.postgresql.Driver";
    public static final String DB_CONNECTION = "jdbc:postgresql://127.0.0.1:5432/testdb/mynewdb";
    public static final String DB_USER = "postgres";
    public static final String DB_PASSWORD = "postgres";

    public static final String LECTORS_TABLE = "LECTORS";
    public static final String DEPARTMENTS_TABLE = "DEPARTMENTS";
    public static final String LECTORS_TO_DEPARTMENTS_TABLE = "LECTORS_TO_DEPARTMENTS";


    public void dropTable(String sql) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            System.out.println("DROP:\n" + sql);
            statement.execute(sql);
            System.out.println("Table dropped successful!" + "\n");
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public void createTable(String sql) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            System.out.println("CREATE:\n" + sql);
            statement.execute(sql);
            System.out.println("Table created successful!" + "\n");
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public int insertRecord(String sql, Object entity) throws SQLException {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        if (entity instanceof Lector) {
            preparedStatement = lectorPreparedStatement((Lector) entity);
        } else if (entity instanceof Department) {
            preparedStatement = departmentPreparedStatement((Department) entity);
        } else if (entity instanceof List) {
            // In case of inserting data into relations table (many to many)
            preparedStatement = lectorToDepartmentPreparedStatement(((List<Integer>) entity).get(0), ((List<Integer>) entity).get(1));
        } else return 0;

        try {
            dbConnection = getDBConnection();

            System.out.println("INSERT:\n" + sql);
            int id = preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            System.out.println("Record was inserted successful!" + "\n");
            return id;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return 0;
    }

    public void selectAllLectors(String sql) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            System.out.println("\nSELECT:");
            System.out.println(sql);

            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String firstName = rs.getString("lector_first_name");
                String lastName = rs.getString("lector_last_name");
                String salary = rs.getString("SALARY");
                String degree = rs.getString("DEGREE");

                System.out.println("-----------------");
                System.out.println("id : " + id);
                System.out.println("lector_first_name : " + firstName);
                System.out.println("lector_last_name : " + lastName);
                System.out.println("salary: " + salary);
                System.out.println("degree: " + degree);
            }
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

    public void selectAllDepartments(String sql) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            System.out.println("\nSELECT:");
            System.out.println(sql);

            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("DEPARTMENT_NAME");
                int headOfDepartment = rs.getInt("HEAD_OF_DEPARTMENT");

                System.out.println("-----------------");
                System.out.println("id : " + id);
                System.out.println("DEPARTMENT_NAME : " + name);
                System.out.println("HEAD_OF_DEPARTMENT : " + headOfDepartment);
            }
            System.out.println();
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

    public void getHeadOfDepartment(String departmentName) throws SQLException {
        String sql = "SELECT L.lector_first_name, L.lector_last_name " +
                "FROM LECTORS L " +
                "LEFT JOIN DEPARTMENTS D " +
                "ON D.head_of_department = L.id " +
                "WHERE D.DEPARTMENT_NAME = '" + departmentName + "'";

        Connection dbConnection = null;
        Statement statement = null;
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            System.out.println("\nHEAD OF DEPARTMENT:");
            System.out.println(sql);

            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String lectorFirstName = rs.getString("lector_first_name");
                String lectorLastName = rs.getString("lector_last_name");

                System.out.println("----------------");
                System.out.println("Head of department is : " + lectorFirstName + " " + lectorLastName + "\nfor department : " + departmentName);
                System.out.println("----------------");
            }
            System.out.println();
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

    public void showDepartmentStatistics(String departmentName) throws SQLException {
        String sql = "SELECT COUNT(L.id), L.degree " +
                "FROM LECTORS_TO_DEPARTMENTS LTD " +
                "INNER JOIN LECTORS L " +
                "ON LTD.lector_id = L.id " +
                "INNER JOIN DEPARTMENTS D " +
                "ON LTD.department_id = D.id " +
                "WHERE D.DEPARTMENT_NAME = '" + departmentName + "'" +
                "GROUP BY L.degree";

        Connection dbConnection = null;
        Statement statement = null;
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            System.out.println("\nSTATISTICS:");
            System.out.println(sql);

            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString("degree") + " - " + rs.getInt(1));
            }
            System.out.println();
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

    public void showAverageSalaryForDepartment(String departmentName) throws SQLException {
        String sql = "SELECT SUM(L.salary) " +
                "FROM LECTORS_TO_DEPARTMENTS LTD " +
                "INNER JOIN LECTORS L " +
                "ON LTD.lector_id = L.id " +
                "INNER JOIN DEPARTMENTS D " +
                "ON LTD.department_id = D.id " +
                "WHERE D.DEPARTMENT_NAME = '" + departmentName + "'";

        Connection dbConnection = null;
        Statement statement = null;
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            System.out.println("\nAVERAGE SALARY FOR DEPARTMENT:");
            System.out.println(sql);

            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                System.out.println("The average salary of " + departmentName + " is " + rs.getString(1));
            }
            System.out.println();
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

    public void showCountOfEmployeeForDepartment(String departmentName) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            String sql = "SELECT COUNT(L.id) " +
                    "FROM LECTORS_TO_DEPARTMENTS LTD " +
                    "INNER JOIN LECTORS L " +
                    "ON LTD.lector_id = L.id " +
                    "INNER JOIN DEPARTMENTS D " +
                    "ON LTD.department_id = D.id " +
                    "WHERE D.DEPARTMENT_NAME = '" + departmentName + "'";

            System.out.println("\nCOUNT OF EMPLOYEE FOR DEPARTMENT:");
            System.out.println(sql);

            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                System.out.println("Count of employee for " + departmentName + " is " + rs.getString(1));
            }
            System.out.println();
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

    public void globalSearchByTemplate(String template) throws SQLException {
        String sql = "SELECT L.lector_first_name, L.lector_last_name " +
                "FROM LECTORS L " +
                "WHERE  L.lector_first_name LIKE '%" + template + "%' " +
                "OR L.lector_last_name LIKE '%" + template + "%' ";

        Connection dbConnection = null;
        Statement statement = null;
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();


            System.out.println("\nSEARCH BY TEMPLATE:");
            System.out.println(sql);

            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2));
            }
            System.out.println();
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

    //TODO
    public static PreparedStatement lectorPreparedStatement(Lector lector) throws SQLException {
        PreparedStatement preparedStatement = getDBConnection().prepareStatement(insertLector, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, lector.getFirstName());
        preparedStatement.setString(2, lector.getLastName());
        preparedStatement.setInt(3, lector.getSalary());
        preparedStatement.setString(4, lector.getDegree());
        return preparedStatement;
    }


    public static PreparedStatement departmentPreparedStatement(Department department) throws SQLException {
        PreparedStatement preparedStatement = getDBConnection().prepareStatement(insertDepartment);
        preparedStatement.setString(1, department.getName());
        preparedStatement.setInt(2, department.getHeadOfDepartment().getId());
        return preparedStatement;
    }


    public static PreparedStatement lectorToDepartmentPreparedStatement(int lectorId, int departmentId) throws SQLException {
        PreparedStatement preparedStatement = getDBConnection().prepareStatement(insertLectorToDepartment);
        preparedStatement.setInt(1, lectorId);
        preparedStatement.setInt(2, departmentId);
        return preparedStatement;
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
