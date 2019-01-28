package by.gstu.airline.dao.jdbc;

import by.gstu.airline.dao.MemberDao;
import by.gstu.airline.dao.jdbc.connection.ProxyConnection;
import by.gstu.airline.entity.Member;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class provides CRUD methods for Member object.
 */
public class MemberDaoJdbc extends GenericDaoJdbc<Member> implements MemberDao {
    private static final Logger LOG = LogManager.getLogger(MemberDaoJdbc.class);

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

    /**
     * Parse ResultSet object to get Member entities.
     *
     * @param resultSet parsed ResultSet object
     * @return ArrayList of Member objects
     * @throws SQLException when resultSent does not contains required value
     */
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

    /**
     * Delete Member object from database by crew id.
     *
     * @param crewId Crew id to delete Member object
     */
    @Override
    public void deleteByCrewId(int crewId) {
        String sql = manager.getQuery("member.delete.by.crew.id");
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, crewId);
            if (statement.executeUpdate() > 0) {
                LOG.trace("crew deleted successfully");
            }
        } catch (SQLException | InterruptedException e) {
            LOG.error(e);
        } finally {
            close(connection, statement);
        }
    }
}
