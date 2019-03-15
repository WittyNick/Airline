package by.gstu.airline.service;

import by.gstu.airline.dao.*;
import by.gstu.airline.entity.Crew;
import by.gstu.airline.entity.Flight;
import by.gstu.airline.entity.Member;
import by.gstu.airline.entity.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Singleton on enum
 * The class provides CRUD methods of logic of interaction with database.
 */
public enum Service {
    INSTANCE;
    private static final Logger log = LogManager.getLogger(Service.class);
    private final DaoFactory daoFactory = DaoFactory.getDaoFactory();

    // Employee
    public synchronized void create(Employee employee) {
        EmployeeDao employeeDao = daoFactory.getEmployeeDao();
        int id = employeeDao.create(employee);
        employee.setId(id);
    }

    public synchronized List<Employee> readAllEmployee() {
        EmployeeDao employeeDao = daoFactory.getEmployeeDao();
        return employeeDao.readAll();
    }

    public synchronized void update(Employee employee) {
        EmployeeDao employeeDao = daoFactory.getEmployeeDao();
        employeeDao.update(employee);
    }

    public synchronized void delete(Employee employee) {
        EmployeeDao employeeDao = daoFactory.getEmployeeDao();
        employeeDao.delete(employee);
    }

    // Crew
    public synchronized void create(Crew crew) {
        CrewDao crewDao = daoFactory.getCrewDao();
        int id = crewDao.create(crew);
        crew.setId(id);
    }

    public synchronized Crew readCrewById(int id) {
        CrewDao crewDao = daoFactory.getCrewDao();
        return crewDao.readById(id);
    }

    public synchronized void update(Crew crew) {
        CrewDao crewDao = daoFactory.getCrewDao();
        deleteMemberByCrewId(crew.getId());
        crewDao.update(crew);
        for (Employee employee : crew.getEmployeeList()) {
            create(new Member(crew.getId(), employee.getId()));
        }
    }

    public synchronized void delete(Crew crew) {
        CrewDao crewDao = daoFactory.getCrewDao();
        crewDao.delete(crew);
    }

    // Member
    public synchronized void create(Member member) {
        MemberDao memberDao = daoFactory.getMemberDao();
        int id = memberDao.create(member);
        member.setId(id);
    }

    public synchronized void update(Member member) {
        MemberDao memberDao = daoFactory.getMemberDao();
        memberDao.update(member);
    }

    public synchronized void deleteMemberByCrewId(int crewId) {
        MemberDao memberDao = daoFactory.getMemberDao();
        memberDao.deleteByCrewId(crewId);
    }

    // Flight
    public synchronized void create(Flight flight) {
        FlightDao flightDao = daoFactory.getFlightDao();
        int id = flightDao.create(flight);
        flight.setId(id);
    }

    public synchronized Flight readFlightById(int id) {
        FlightDao flightDao = daoFactory.getFlightDao();
        return flightDao.readById(id);
    }

    public List<Flight> readAllFlight() {
        FlightDao flightDao = daoFactory.getFlightDao();
        List<Flight> flightList;
        synchronized (this) {
            flightList = flightDao.readAll();
        }
        sortFlights(flightList);
        return flightList;
    }

    public synchronized void delete(Flight flight) {
        FlightDao flightDao = daoFactory.getFlightDao();
        if (flight.getCrew() != null && flight.getCrew().getId() > 0) {
            deleteMemberByCrewId(flight.getCrew().getId());
        }
        delete(flight.getCrew());
        flightDao.delete(flight);
    }

    public synchronized void update(Flight flight) {
        FlightDao flightDao = daoFactory.getFlightDao();
        flightDao.update(flight);
    }

    private void sortFlights(List<Flight> flightList) {
        Collections.sort(flightList, new Comparator<Flight>() {
            @Override
            public int compare(Flight flight1, Flight flight2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = format.parse(flight1.getDepartureDate() + ' ' + flight1.getDepartureTime());
                    date2 = format.parse(flight2.getDepartureDate() + ' ' + flight2.getDepartureTime());
                } catch (ParseException e) {
                    log.error(e);
                }
                if (date1 == null || date2 == null) {
                    return 0;
                }
                return (int) (date1.getTime() - date2.getTime());
            }
        });
    }
}
