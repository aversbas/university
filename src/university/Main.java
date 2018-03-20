package university;

import university.dao.Dao;
import university.dao.DaoImpl;
import university.entity.Department;
import university.entity.Lector;
import university.entity.LectorDegree;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static university.dao.DaoImpl.DEPARTMENTS_TABLE;
import static university.dao.DaoImpl.LECTORS_TABLE;
import static university.dao.sql.Create.*;
import static university.dao.sql.Drop.*;
import static university.dao.sql.Insert.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Dao dao = new DaoImpl();

        dropTables(dao);
        createTables(dao);

        List<Lector> lectors = createLectors();
        insertLectors(dao, lectors);
        dao.selectAllLectors("SELECT * FROM " + LECTORS_TABLE);

        List<Department> departments = createDepartments(lectors);
        insertDepartments(dao, departments);
        dao.selectAllDepartments("SELECT * FROM " + DEPARTMENTS_TABLE);

        insertLectorsToDepartments(dao, lectors, departments);


        dao.getHeadOfDepartment("Electronics");
        dao.showDepartmentStatistics("Electronics");
        dao.showAverageSalaryForDepartment("Electronics");
        dao.showCountOfEmployeeForDepartment("Electronics");
        dao.globalSearchByTemplate("Ivan");
        dao.globalSearchByTemplate("Petr");
    }

    private static List<Lector> createLectors() {
        Lector lector1 = new Lector();
        lector1.setFirstName("Ivan");
        lector1.setLastName("Petrov");
        lector1.setSalary(100);
        lector1.setDegree(LectorDegree.ASSISTANT.toString());

        Lector lector2 = new Lector();
        lector2.setFirstName("Sergey");
        lector2.setLastName("Ivanov");
        lector2.setSalary(200);
        lector2.setDegree(LectorDegree.ASSOCIATE_PROFESSOR.toString());

        Lector lector3 = new Lector();
        lector3.setFirstName("Vasiliy");
        lector3.setLastName("Pupkin");
        lector3.setSalary(300);
        lector3.setDegree(LectorDegree.PROFESSOR.toString());

        Lector lector4 = new Lector();
        lector4.setFirstName("Grigoriy");
        lector4.setLastName("Skovoroda");
        lector4.setSalary(300);
        lector4.setDegree(LectorDegree.PROFESSOR.toString());
        return Arrays.asList(lector1, lector2, lector3, lector4);
    }

    private static List<Department> createDepartments(List<Lector> lectors) {
        Department department1 = new Department();
        department1.setName("Electronics");
        if (lectors.get(0) != null) {
            department1.setHeadOfDepartment(lectors.get(0));
        }
        return Arrays.asList(department1);
    }


    private static void insertLectorsToDepartments(Dao dao, List<Lector> lectors, List<Department> departments) throws SQLException {
        for (Lector lector : lectors) {
            dao.insertRecord(insertLectorToDepartment, Arrays.asList(lector.getId(), departments.get(0).getId()));
        }
    }

    private static void insertDepartments(Dao dao, List<Department> departments) throws SQLException {
        for (Department department : departments) {
            int id = dao.insertRecord(insertDepartment, department);
            department.setId(id);
        }

    }

    private static List<Lector> insertLectors(Dao dao, List<Lector> lectros) throws SQLException {
        for (Lector lector : lectros) {
            int id = dao.insertRecord(insertLector, lector);
            lector.setId(id);
        }
        return lectros;
    }

    private static void createTables(Dao dao) throws SQLException {
        dao.createTable(createLectorsTable);
        dao.createTable(createDepartmentsTable);
        dao.createTable(createLectorsToDepartments);
    }

    private static void dropTables(Dao dao) throws SQLException {
        dao.dropTable(dropLectorsToDepartmentsTable);
        dao.dropTable(dropLectorsTable);
        dao.dropTable(dropDepartmentsTable);
    }
}
