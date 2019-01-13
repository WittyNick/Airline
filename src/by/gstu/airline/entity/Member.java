package by.gstu.airline.entity;

public class Member {
    private int id;
    private int crewId;
    private int employeeId;

    public Member() {
    }

    public Member(int crewId, int employeeId) {
        this.crewId = crewId;
        this.employeeId = employeeId;
    }

    public Member(Crew crew, Employee employee) {
        this(crew.getId(), employee.getId());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCrewId() {
        return crewId;
    }

    public void setCrewId(int crewId) {
        this.crewId = crewId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", crewId=" + crewId +
                ", employeeId=" + employeeId +
                '}';
    }
}
