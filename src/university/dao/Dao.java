package university.dao;

import java.sql.SQLException;

public interface Dao {
    void dropTable(String sql) throws SQLException;

    void createTable(String sql) throws SQLException;

    int insertRecord(String sql, Object entity) throws SQLException;

    void selectAllLectors(String sql) throws SQLException;

    void selectAllDepartments(String sql) throws SQLException;

    void getHeadOfDepartment(String departmentName) throws SQLException;

    void showDepartmentStatistics(String departmentName) throws SQLException;

    void showAverageSalaryForDepartment(String departmentName) throws SQLException;

    void showCountOfEmployeeForDepartment(String departmentName) throws SQLException;

    void globalSearchByTemplate(String template) throws SQLException;
}
