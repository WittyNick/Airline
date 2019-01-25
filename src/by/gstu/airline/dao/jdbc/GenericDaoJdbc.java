package by.gstu.airline.dao.jdbc;

import by.gstu.airline.config.ConfigurationManager;
import by.gstu.airline.dao.GenericDao;
import by.gstu.airline.dao.jdbc.connection.ProxyConnection;
import by.gstu.airline.dao.jdbc.connection.ProxyConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class GenericDaoJdbc<T> implements GenericDao<T> {
    private static final Logger LOG = LogManager.getLogger(ConfigurationManager.class);
    final ConfigurationManager manager = ConfigurationManager.INSTANCE;
    final ProxyConnectionPool connectionPool;

    GenericDaoJdbc() {
        connectionPool = DaoFactoryJdbc.getProxyConnectionPool();
    }

    protected abstract String getCreateQuery();

    protected abstract String getSelectQuery();

    protected String getSelectByIdQuery(int id) { // overridden in subclasses
        return getSelectQuery() + " WHERE id = " + id;
    }

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    protected abstract void prepareStatementForCreate(PreparedStatement statement, T entity) throws SQLException;

    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T entity) throws SQLException;

    protected abstract void prepareStatementForDelete(PreparedStatement statement, T entity) throws SQLException;

    protected abstract List<T> parseResultSet(ResultSet resultSet) throws SQLException;

    @Override
    public int create(T entity) {
        int id = 0;
        String sql = getCreateQuery();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepareStatementForCreate(statement, entity);
            if (statement.executeUpdate() > 0) {
                LOG.trace("entity was created successfully");
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }
        } catch (SQLException | InterruptedException e) {
            LOG.error(e);
        } finally {
            close(connection, statement);
        }
        return id;
    }

    @Override
    public T readById(int id) {
        String sql = getSelectByIdQuery(id);
        List<T> list = read(sql);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<T> readAll() {
        String sql = getSelectQuery();
        return read(sql);
    }

    @Override
    public void update(T entity) {
        String sql = getUpdateQuery();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(sql);
            prepareStatementForUpdate(statement, entity);
            if (statement.executeUpdate() > 0) {
                LOG.trace("entity was updated successfully");
            }
        } catch (SQLException | InterruptedException e) {
            LOG.error(e);
        } finally {
            close(connection, statement);
        }
    }

    @Override
    public void delete(T entity) {
        String sql = getDeleteQuery();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(sql);
            prepareStatementForDelete(statement, entity);
            if (statement.executeUpdate() > 0) {
                LOG.trace("entity was deleted successfully");
            }
        } catch (SQLException | InterruptedException e) {
            LOG.error(e);
        } finally {
            close(connection, statement);
        }
    }

    private List<T> read(String selectQuery) {
        List<T> list = null;
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = statement.executeQuery();
            list = parseResultSet(resultSet);
        } catch (SQLException | InterruptedException e) {
            LOG.error(e);
        } finally {
            close(connection, statement);
        }
        return list;
    }

    void close(ProxyConnection connection, PreparedStatement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connectionPool.closeConnection(connection);
            }
        } catch (SQLException e) {
            LOG.error(e);
        }
    }
}