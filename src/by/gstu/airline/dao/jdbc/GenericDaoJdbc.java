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

/**
 * The abstract generic class that provides CRUD methods to interact with database.
 *
 * @param <T> type of entity for CRUD operations
 */
public abstract class GenericDaoJdbc<T> implements GenericDao<T> {
    private static final Logger log = LogManager.getLogger(ConfigurationManager.class);
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

    /**
     * Insert entity to database.
     *
     * @param entity entity for insertion
     * @return autogenerated id
     */
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
            if (statement.executeUpdate() == 1) {
                log.trace("entity was created successfully");
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }
        } catch (SQLException | InterruptedException e) {
            log.error(e);
        } finally {
            close(connection, statement);
        }
        return id;
    }

    /**
     * Select entity by id from database.
     *
     * @param id entity id for selection
     * @return entity
     */
    @Override
    public T readById(int id) {
        String sql = getSelectByIdQuery(id);
        List<T> list = read(sql);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * Select all entities from database.
     *
     * @return List of selected entities
     */
    @Override
    public List<T> readAll() {
        String sql = getSelectQuery();
        return read(sql);
    }

    /**
     * Update entity with certain id in database.
     *
     * @param entity entity to update
     */
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
                log.trace("entity was updated successfully");
            }
        } catch (SQLException | InterruptedException e) {
            log.error(e);
        } finally {
            close(connection, statement);
        }
    }

    /**
     * Delete entity from database.
     *
     * @param entity entity to delete
     */
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
                log.trace("entity was deleted successfully");
            }
        } catch (SQLException | InterruptedException e) {
            log.error(e);
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
            log.error(e);
        } finally {
            close(connection, statement);
        }
        return list;
    }

    /**
     * Return ProxyConnection to connection pool and close PreparedStatement.
     *
     * @param connection ProxyConnection to return to connection pool
     * @param statement PrepareStatement to close
     */
    void close(ProxyConnection connection, PreparedStatement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connectionPool.closeConnection(connection);
            }
        } catch (SQLException e) {
            log.error(e);
        }
    }
}