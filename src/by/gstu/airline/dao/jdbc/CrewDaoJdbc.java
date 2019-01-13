package by.gstu.airline.dao.jdbc;

import by.gstu.airline.dao.CrewDao;
import by.gstu.airline.entity.Crew;
import by.gstu.airline.entity.Employee;
import by.gstu.airline.entity.Position;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CrewDaoJdbc extends GenericDaoJdbc<Crew> implements CrewDao {

    @Override
    protected String getCreateQuery() {
        return manager.getQuery("crew.create");
    }

    @Override
    protected String getSelectQuery() {
        return manager.getQuery("crew.select");
    }

    @Override
    protected String getSelectByIdQuery(int id) {
        return getSelectQuery() + " WHERE " + manager.getQuery("crew.select.table.id") + " = " + id;
    }

    @Override
    protected String getUpdateQuery() {
        return manager.getQuery("crew.update");
    }

    @Override
    protected String getDeleteQuery() {
        return manager.getQuery("crew.delete");
    }

    @Override
    protected void prepareStatementForCreate(PreparedStatement statement, Crew entity) throws SQLException {
        statement.setString(1, entity.getName());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Crew entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setInt(2, entity.getId());
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Crew entity) throws SQLException {
        statement.setInt(1, entity.getId());
    }

    // employeeList может быть пустым, тогда должен быть null
    @Override
    protected List<Crew> parseResultSet(ResultSet resultSet) throws SQLException {
        List<Crew> list = new ArrayList<>();
        Crew crew = new Crew();
        List<Employee> employeeList = new ArrayList<>();
        int crewIdTmp = -1;

        while (resultSet.next()) {
            int crewId = resultSet.getInt(manager.getQuery("crew.select.column.id"));
            if (crewIdTmp != crewId) {
                if (crewIdTmp > 0) {
                    list.add(crew);
                    crew = new Crew();
                    employeeList = new ArrayList<>();
                }
                crew.setId(crewId);
                crew.setName(resultSet.getString(manager.getQuery("crew.select.column.name")));
                crew.setEmployeeList(employeeList);
                crewIdTmp = crewId;

            }
            Employee employee = parseEmployee(resultSet);
            if (employee != null) {
                employeeList.add(employee);
            }
        }
        if (crewIdTmp > 0) {
            list.add(crew);
        }
        return list;
    }

    private Employee parseEmployee(ResultSet resultSet) throws SQLException {
        int employeeId = resultSet.getInt(manager.getQuery("crew.select.column.employee.id"));
        if (employeeId == 0) {
            return null;
        }
        Position[] positions = Position.values();
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setName(resultSet.getString(manager.getQuery("crew.select.column.employee.name")));
        employee.setSurname(resultSet.getString(manager.getQuery("crew.select.column.employee.surname")));
        employee.setPosition(positions[resultSet.getInt(manager.getQuery("crew.select.column.employee.position"))]);
        return employee;
    }
}
