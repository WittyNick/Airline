package by.gstu.airline.entity;

import java.util.List;

public class Crew {
    private int id;
    private String name;
    private List<Employee> employeeList;

    public Crew() {
    }

    public Crew(String name, List<Employee> employeeList) {
        this.name = name;
        this.employeeList = employeeList;
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

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
