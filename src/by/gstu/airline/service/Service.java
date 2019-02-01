package by.gstu.airline.service;

import by.gstu.airline.config.ConfigurationManager;
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
 * The class provides CRUD methods of logic of interaction with database.
 */
public enum Service {
    INSTANCE;
    private static final Logger LOG = LogManager.getLogger(Service.class);
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
    public synchronized void create(Employee employee) {
        int id = employeeDao.create(employee);
        employee.setId(id);
    }

    public synchronized Employee readEmployeeById(int id) {
        return employeeDao.readById(id);
    }

    public synchronized List<Employee> readAllEmployee() {
        return employeeDao.readAll();
    }

    public synchronized void update(Employee employee) {
        employeeDao.update(employee);
    }

    public synchronized void delete(Employee employee) {
        employeeDao.delete(employee);
    }

    // Crew
    public synchronized void create(Crew crew) {
        int id = crewDao.create(crew);
        crew.setId(id);
    }

    public synchronized List<Crew> readAllCrew() {
        return crewDao.readAll();
    }

    public synchronized Crew readCrewById(int id) {
        return crewDao.readById(id);
    }

    public synchronized void update(Crew crew) {
        deleteMemberByCrewId(crew.getId());
        crewDao.update(crew);
        for (Employee employee : crew.getEmployeeList()) {
            create(new Member(crew.getId(), employee.getId()));
        }
    }

    public synchronized void delete(Crew crew) {
        crewDao.delete(crew);
    }

    // Member
    public synchronized void create(Member member) {
        int id = memberDao.create(member);
        member.setId(id);
    }

    public synchronized void update(Member member) {
        memberDao.update(member);
    }

    public synchronized void deleteMemberByCrewId(int crewId) {
        memberDao.deleteByCrewId(crewId);
    }

    // Flight
    public synchronized void create(Flight flight) {
        int id = flightDao.create(flight);
        flight.setId(id);
    }

    public synchronized Flight readFlightById(int id) {
        return flightDao.readById(id);
    }

    public synchronized List<Flight> readAllFlight() {
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
                    LOG.error(e);
                }
                if (date1 == null || date2 == null) {
                    return 0;
                }
                return (int) (date1.getTime() - date2.getTime());
            }
        });
        return flightList;
    }

    public synchronized void delete(Flight flight) {
        if (flight.getCrew() != null && flight.getCrew().getId() > 0) {
            deleteMemberByCrewId(flight.getCrew().getId());
        }
        delete(flight.getCrew());

        flightDao.delete(flight);
    }

    public synchronized void update(Flight flight) {
        flightDao.update(flight);
    }
}
