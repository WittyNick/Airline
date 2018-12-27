package by.gstu.airline.service;

import by.gstu.airline.dao.*;
import by.gstu.airline.entity.Crew;
import by.gstu.airline.entity.Employee;
import by.gstu.airline.entity.Member;

import java.util.List;

public enum Service {
    INSTANCE;
    private EmployeeDao employeeDao;
    private CrewDao crewDao;
    private MemberDao memberDao;
    private FlightDao flightDao;

    Service() {
        FactoryDao factoryDao = FactoryDao.getDaoFactory();
        employeeDao = factoryDao.getEmployeeDao();
        crewDao = factoryDao.getCrewDao();
        memberDao = factoryDao.getMemberDao();
        flightDao = factoryDao.getFlightDao();
    }

    public void create(Employee employee) {
        int id = employeeDao.create(employee);
        employee.setId(id);
    }

    public void createEmployeeList(List<Employee> employeeList) {
        for (Employee employee : employeeList) {
            create(employee);
        }
    }

    public Employee readEmployeeById(int id) {
        return employeeDao.readById(id);
    }

    public List<Employee> readAllEmployee() {
        return employeeDao.readAll();
    }

    public void update(Employee employee) {
        employeeDao.update(employee);
    }

    public void delete(Employee employee) {
        employeeDao.delete(employee);
    }

    public void deleteEmployeeList(List<Employee> employeeList) {
        for (Employee employee : employeeList) {
            delete(employee);
        }
    }





    public void create(Crew crew) {
        int id = crewDao.create(crew);
        crew.setId(id);
    }

    public void createCrewList(List<Crew> crewList) {
        for (Crew crew : crewList) {
            create(crew);
        }
    }

    public Crew readCrewById(int id) {
        return crewDao.readById(id);
    }

    public List<Employee> readAllCrew() {
        return employeeDao.readAll();
    }

    public void update(Crew crew) {
        crewDao.update(crew);
    }

    public void delete(Crew crew) {
        crewDao.delete(crew);
    }

    public void deleteCrewList(List<Crew> crewList) {
        for (Crew crew : crewList) {
            delete(crew);
        }
    }




    public void create(Member member) {
        int id = memberDao.create(member);
        member.setId(id);
    }

    public Member readMemberById(int id) {
        return memberDao.readById(id);
    }

    public List<Member> readAllMember() {
        return memberDao.readAll();
    }

    public void update(Member member) {
        memberDao.update(member);
    }

    public void delete(Member member) {
        memberDao.delete(member);
    }
}
