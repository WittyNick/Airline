package by.gstu.airline.service;

import by.gstu.airline.config.ConfigurationManager;
import by.gstu.airline.dao.*;
import by.gstu.airline.entity.Crew;
import by.gstu.airline.entity.Flight;
import by.gstu.airline.entity.Member;
import by.gstu.airline.entity.Employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public enum Service {
    INSTANCE;
    private EmployeeDao employeeDao;
    private CrewDao crewDao;
    private MemberDao memberDao;
    private FlightDao flightDao;

    Service() {
        DaoFactory daoFactory = DaoFactory.getDaoFactory();
        employeeDao = daoFactory.getEmployeeDao();
        crewDao = daoFactory.getCrewDao();
        memberDao = daoFactory.getMemberDao();
        flightDao = daoFactory.getFlightDao();
    }

    // Employee
    public void create(Employee employee) {
        int id = employeeDao.create(employee);
        employee.setId(id);
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

    // Crew
    public void create(Crew crew) {
        int id = crewDao.create(crew);
        crew.setId(id);
    }

    public List<Crew> readAllCrew() {
        return crewDao.readAll();
    }

    public Crew readCrewById(int id) {
        return crewDao.readById(id);
    }

    public void update(Crew crew) {
        deleteMemberByCrewId(crew.getId());
        crewDao.update(crew);
        for (Employee employee : crew.getEmployeeList()) {
            create(new Member(crew.getId(), employee.getId()));
        }
    }

    public void delete(Crew crew) {
        crewDao.delete(crew);
    }

    // Member
    public void create(Member member) {
        int id = memberDao.create(member);
        member.setId(id);
    }

    public void update(Member member) {
        memberDao.update(member);
    }

    public void deleteMemberByCrewId(int crewId) {
        memberDao.deleteByCrewId(crewId);
    }

    // Flight
    public void create(Flight flight) {
        int id = flightDao.create(flight);
        flight.setId(id);
    }

    public Flight readFlightById(int id) {
        return flightDao.readById(id);
    }

    public List<Flight> readAllFlight() {
        final ConfigurationManager manager = ConfigurationManager.INSTANCE;
        List<Flight> flightList = flightDao.readAll();
        Collections.sort(flightList, new Comparator<Flight>() {
            @Override
            public int compare(Flight flight1, Flight flight2) {
                String pattern = manager.getText("date.format") + ' ' + manager.getText("time.format");
                SimpleDateFormat format = new SimpleDateFormat(pattern);

                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = format.parse(flight1.getDepartureDate() + ' ' + flight1.getDepartureTime());
                    date2 = format.parse(flight2.getDepartureDate() + ' ' + flight2.getDepartureTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (date1 == null || date2 == null) {
                    return 0;
                }
                return (int) (date1.getTime() - date2.getTime());
            }
        });
        return flightList;
    }

    public void delete(Flight flight) {
        if (flight.getCrew() != null && flight.getCrew().getId() > 0) {
            deleteMemberByCrewId(flight.getCrew().getId());
        }

        // удалить бригаду
        delete(flight.getCrew());

        // удалить рейс
        flightDao.delete(flight);
    }

    public void update(Flight flight) {
        flightDao.update(flight);
    }
}
