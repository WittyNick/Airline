package by.gstu.airline.dao.jdbc;

import by.gstu.airline.config.ConfigurationManager;
import by.gstu.airline.dao.*;
import by.gstu.airline.dao.jdbc.connection.ProxyConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * Singleton.
 * The class produces DAO entities.
 */
public class DaoFactoryJdbc extends DaoFactory {
    private static final Logger LOG = LogManager.getLogger(DaoFactoryJdbc.class);
    private static DaoFactoryJdbc instance;
    private static ProxyConnectionPool proxyConnectionPool;
    private static ConfigurationManager manager = ConfigurationManager.INSTANCE;

    private DaoFactoryJdbc() {
    }

    @Override
    public FlightDao getFlightDao() {
        return new FlightDaoJdbc();
    }

    @Override
    public CrewDao getCrewDao() {
        return new CrewDaoJdbc();
    }

    @Override
    public EmployeeDao getEmployeeDao() {
        return new EmployeeDaoJdbc();
    }

    @Override
    public MemberDao getMemberDao() {
        return new MemberDaoJdbc();
    }

    /**
     * Returns instance of singleton DaoFactoryJdbc.
     *
     * @return instance of this class
     */
    public static synchronized DaoFactory getDaoFactory() {
        if (instance == null) {
            instance = new DaoFactoryJdbc();
        }
        return instance;
    }

    /**
     * Creates new instance of ProxyConnectionPool.
     *
     * @return new instance of ProxyConnectionPool
     */
    static ProxyConnectionPool getProxyConnectionPool() {
        if (proxyConnectionPool == null) {
            int poolSize = Integer.parseInt(manager.getProperty("db.connection.pool.size"));
            try {
                proxyConnectionPool = new ProxyConnectionPool(poolSize);
            } catch (SQLException e) {
                LOG.error(e);
            }
        }
        return proxyConnectionPool;
    }
}
