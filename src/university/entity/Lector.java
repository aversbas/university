package university.entity;


import java.util.List;

public class Lector {
    private int id;
    private String name;
    private LectorDegree lectorDegree;
    private int salary;

    private List<Department> departments;

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LectorDegree getLectorDegree() {
        return lectorDegree;
    }

    public void setLectorDegree(LectorDegree lectorDegree) {
        this.lectorDegree = lectorDegree;
    }
}
