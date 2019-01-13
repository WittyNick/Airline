package by.gstu.airline.dao.jdbc;

import by.gstu.airline.dao.FlightDao;
import by.gstu.airline.entity.Crew;
import by.gstu.airline.entity.Employee;
import by.gstu.airline.entity.Flight;
import by.gstu.airline.entity.Position;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlightDaoJdbc extends GenericDaoJdbc<Flight> implements FlightDao {

    @Override
    protected String getCreateQuery() {
        return manager.getQuery("flight.create");
    }

    @Override
    protected String getSelectQuery() {
        return manager.getQuery("flight.select");
    }

    @Override
    protected String getSelectByIdQuery(int id) {
        return getSelectQuery() + " WHERE " + manager.getQuery("flight.select.table.id") + " = " + id;
    }

    @Override
    protected String getUpdateQuery() {
        return manager.getQuery("flight.update");
    }

    @Override
    protected String getDeleteQuery() {
        return manager.getQuery("flight.delete");
    }

    @Override
    protected void prepareStatementForCreate(PreparedStatement statement, Flight entity) throws SQLException {
        statement.setInt(1, entity.getFlightNumber());
        statement.setString(2, entity.getStartPoint());
        statement.setString(3, entity.getDestinationPoint());
        statement.setLong(4, stringToDate(entity.getDepartureDate(), entity.getDepartureTime()).getTime());
        statement.setLong(5, stringToDate(entity.getArrivalDate(), entity.getArrivalTime()).getTime());
        statement.setString(6, entity.getPlane());
        if (entity.getCrew() != null) {
            statement.setInt(7, entity.getCrew().getId());
        } else {
            statement.setInt(7, 0);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Flight entity) throws SQLException {
        statement.setInt(1, entity.getFlightNumber());
        statement.setString(2, entity.getStartPoint());
        statement.setString(3, entity.getDestinationPoint());
        statement.setLong(4, stringToDate(entity.getDepartureDate(), entity.getDepartureTime()).getTime());
        statement.setLong(5, stringToDate(entity.getArrivalDate(), entity.getArrivalTime()).getTime());
        statement.setString(6, entity.getPlane());
        if (entity.getCrew() != null) {
            statement.setInt(7, entity.getCrew().getId());
        } else {
            statement.setInt(7, 0);
        }
        statement.setInt(8, entity.getId());
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Flight entity) throws SQLException {
        statement.setInt(1, entity.getId());
    }

    @Override
    protected List<Flight> parseResultSet(ResultSet resultSet) throws SQLException {
        List<Flight> list = new ArrayList<>();
        Flight flight = new Flight();
        List<Employee> employeeList = new ArrayList<>();
        int flightIdTmp = -1;
        while (resultSet.next()) {
            int flightId = resultSet.getInt(manager.getQuery("flight.select.column.id"));
            if (flightIdTmp != flightId) {
                if (flightIdTmp > 0) {
                    list.add(flight);
                    flight = new Flight();
                    employeeList = new ArrayList<>();
                }
                flight.setId(flightId);
                flight.setFlightNumber(resultSet.getInt(manager.getQuery("flight.select.column.flightNumber")));
                flight.setStartPoint(resultSet.getString(manager.getQuery("flight.select.column.startPoint")));
                flight.setDestinationPoint(resultSet.getString(manager.getQuery("flight.select.column.destinationPoint")));
                Date departureDateTime = new Date(resultSet.getLong(manager.getQuery("flight.select.column.departureDateTime")));
                Date arrivalDateTime = new Date(resultSet.getLong(manager.getQuery("flight.select.column.arrivalDateTime")));
                flight.setDepartureDate(toDateString(departureDateTime));
                flight.setDepartureTime(toTimeString(departureDateTime));
                flight.setArrivalDate(toDateString(arrivalDateTime));
                flight.setArrivalTime(toTimeString(arrivalDateTime));

                flight.setPlane(resultSet.getString(manager.getQuery("flight.select.column.plane")));

                int crewId = resultSet.getInt(manager.getQuery("flight.select.column.crew.id"));

                if (crewId > 0) {
                    Crew crew = new Crew();
                    crew.setId(crewId);
                    crew.setName(resultSet.getString(manager.getQuery("flight.select.column.crew.name")));
                    crew.setEmployeeList(employeeList);
                    flight.setCrew(crew);
                }
                flightIdTmp = flightId;
            }
            Employee employee = parseEmployee(resultSet);
            if (employee != null) {
                employeeList.add(employee);
            }
        }
        if (flightIdTmp > 0) {
            list.add(flight);
        }
        return list;
    }

    private Employee parseEmployee(ResultSet resultSet) throws SQLException {
        int employeeId = resultSet.getInt(manager.getQuery("flight.select.column.employee.id"));
        if (employeeId == 0) {
            return null;
        }
        Position[] positions = Position.values();
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setName(resultSet.getString(manager.getQuery("flight.select.column.employee.name")));
        employee.setSurname(resultSet.getString(manager.getQuery("flight.select.column.employee.surname")));
        employee.setPosition(positions[resultSet.getInt(manager.getQuery("flight.select.column.employee.position"))]);
        return employee;
    }

    private Date stringToDate(String date, String time) {
        String pattern = manager.getText("date.format") + ' ' + manager.getText("time.format");
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date result = null;
        try {
            result = format.parse(date + ' ' + time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String toDateString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(manager.getText("date.format"));
        return dateFormat.format(date);
    }

    private String toTimeString(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat(manager.getText("time.format"));
        return timeFormat.format(date);
    }
}
