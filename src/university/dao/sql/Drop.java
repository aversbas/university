package university.dao.sql;

import static university.dao.DaoImpl.*;

public class Drop {
    public static String dropLectorsTable = "DROP TABLE IF EXISTS " + LECTORS_TABLE + " ;";
    public static String dropDepartmentsTable = "DROP TABLE IF EXISTS " + DEPARTMENTS_TABLE + " ;";
    public static String dropLectorsToDepartmentsTable = "DROP TABLE IF EXISTS " + LECTORS_TO_DEPARTMENTS_TABLE + " ;";
}
