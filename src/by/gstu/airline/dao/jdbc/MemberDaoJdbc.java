package by.gstu.airline.dao.jdbc;

import by.gstu.airline.dao.MemberDao;
import by.gstu.airline.dao.jdbc.connection.ProxyConnection;
import by.gstu.airline.entity.Crew;
import by.gstu.airline.entity.Employee;
import by.gstu.airline.entity.Member;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDaoJdbc extends GenericDaoJdbc<Member> implements MemberDao {

    @Override
    protected String getCreateQuery() {
        return manager.getQuery("member.create");
    }

    @Override
    protected String getSelectQuery() {
        return manager.getQuery("member.select");
    }

    @Override
    protected String getUpdateQuery() {
        return manager.getQuery("member.update");
    }

    @Override
    protected String getDeleteQuery() {
        return manager.getQuery("member.delete");
    }

    @Override
    protected void prepareStatementForCreate(PreparedStatement statement, Member entity) throws SQLException {
        statement.setInt(1, entity.getCrewId());
        statement.setInt(2, entity.getEmployeeId());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Member entity) throws SQLException {
        statement.setInt(1, entity.getCrewId());
        statement.setInt(2, entity.getEmployeeId());
        statement.setInt(3, entity.getId());
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement statement, Member entity) throws SQLException {
        statement.setInt(1, entity.getId());
    }

    @Override
    protected List<Member> parseResultSet(ResultSet resultSet) throws SQLException {
        List<Member> list = new ArrayList<>();
        while (resultSet.next()) {
            Member member = new Member();
            member.setId(resultSet.getInt(manager.getQuery("member.select.column.id")));
            member.setCrewId(resultSet.getInt(manager.getQuery("member.select.column.crewId")));
            member.setEmployeeId(resultSet.getInt(manager.getQuery("member.select.column.employeeId")));
            list.add(member);
        }
        return list;
    }

    @Override
    public void deleteByCrewId(int crewId) {
        String sql = manager.getQuery("member.delete.by.crew.id");
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, crewId);
            if (statement.executeUpdate() >= 1) {
//                System.out.println("deleted successfully");
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            close(connection, statement);
        }
    }
}
