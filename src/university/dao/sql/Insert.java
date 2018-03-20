package university.dao.sql;

import static university.dao.DaoImpl.*;

public class Insert {
    public static String insertLector = "INSERT INTO " + LECTORS_TABLE +
            "(lector_first_name, lector_last_name, salary, degree) VALUES" +
            "(?,?,?,?)";

    public static String insertDepartment = "INSERT INTO " + DEPARTMENTS_TABLE +
            "(department_name, head_of_department) VALUES" +
            "(?,?)";

    public static String insertLectorToDepartment = "INSERT INTO " + LECTORS_TO_DEPARTMENTS_TABLE +
            "(lector_id, department_id) VALUES" +
            "(?,?)";
}

