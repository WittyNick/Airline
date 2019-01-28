package by.gstu.airline.dao.jdbc;

import by.gstu.airline.dao.EmployeeDao;
import by.gstu.airline.entity.Employee;
import by.gstu.airline.entity.Position;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class provides CRUD methods for Employee object.
 */
public class EmployeeDaoJdbc extends GenericDaoJdbc<Employee> implements EmployeeDao {

    @Override
    protected String getCreateQuery() {
        return manager.getQuery("employee.create");
    }

    @Override
    protected String getSelectQuery() {
        return manager.getQuery("employee.select");
    }

    @Override
    protected String getUpdateQuery() {
        return manager.getQuery("employee.update");
    }

    @Override
    protected String getDeleteQuery() {
        return manager.getQuery("employee.delete");
    }

    @Override
    protected void prepareStatementForCreate(PreparedStatement statement, Employee entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
        statement.setInt(3, entity.getPosition().ordinal());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Employee entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
        statement.setInt(3, entity.getPosition().ordinal());
        statement.setInt(4, entity.getId());
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Employee entity) throws SQLException {
        statement.setInt(1, entity.getId());
    }

    /**
     * Parse ResultSet object to get Employee entities.
     *
     * @param resultSet parsed ResultSet object
     * @return ArrayList of Employee entities
     * @throws SQLException when resultSent does not contains required value
     */
    @Override
    protected List<Employee> parseResultSet(ResultSet resultSet) throws SQLException {
        List<Employee> list = new ArrayList<>();
        Position[] positions = Position.values();
        while (resultSet.next()) {
            Employee employee = new Employee();
            employee.setId(resultSet.getInt(manager.getQuery("employee.select.column.id")));
            employee.setName(resultSet.getString(manager.getQuery("employee.select.column.name")));
            employee.setSurname(resultSet.getString(manager.getQuery("employee.select.column.surname")));
            employee.setPosition(positions[resultSet.getInt(manager.getQuery("employee.select.column.position"))]);
            list.add(employee);
        }
        return list;
    }
}
