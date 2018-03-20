package university.dao.sql;

import static university.dao.DaoImpl.*;

public class Create {
    public static String createLectorsTable = "CREATE TABLE " + LECTORS_TABLE + " \n" +
            "( id SERIAL UNIQUE NOT NULL,\n" +
            " lector_first_name varchar(255),\n" +
            " lector_last_name varchar(255),\n" +
            " salary int,\n" +
            " degree varchar(255),\n" +
            " CONSTRAINT lector_pk PRIMARY KEY (id) \n" +
            " );";

    public static String createDepartmentsTable = "CREATE TABLE " + DEPARTMENTS_TABLE + " \n" +
            "( id SERIAL UNIQUE NOT NULL,\n" +
            " department_name varchar(255),\n" +
            " head_of_department int,\n" +
            " CONSTRAINT department_pk PRIMARY KEY (id) \n" +
            " );";

    public static String createLectorsToDepartments = "CREATE TABLE " + LECTORS_TO_DEPARTMENTS_TABLE + " \n" +
            "( lector_id INT NOT NULL,\n" +
            "  department_id INT NOT NULL,\n" +
            "  CONSTRAINT lectors_to_departments_pk PRIMARY KEY (lector_id, department_id),\n" +
            "  CONSTRAINT lector_r_fk FOREIGN KEY (lector_id) REFERENCES " + LECTORS_TABLE + " (id), \n" +
            "  CONSTRAINT department_r_fk FOREIGN KEY (department_id) REFERENCES " + DEPARTMENTS_TABLE + " (id) \n" +
            " );";
}
