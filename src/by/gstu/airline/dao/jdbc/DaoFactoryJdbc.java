package by.gstu.airline.dao.jdbc;

import by.gstu.airline.config.ConfigurationManager;
import by.gstu.airline.dao.*;
import by.gstu.airline.dao.jdbc.connection.ProxyConnectionPool;

import java.sql.SQLException;

public class DaoFactoryJdbc extends DaoFactory {
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

    public static synchronized DaoFactory getDaoFactory() {
        if (instance == null) {
            instance = new DaoFactoryJdbc();
        }
        return instance;
    }

    static ProxyConnectionPool getProxyConnectionPool() {
        if (proxyConnectionPool == null) {
            int poolSize = Integer.parseInt(manager.getProperty("db.connection.pool.size"));
            try {
                proxyConnectionPool = new ProxyConnectionPool(poolSize);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return proxyConnectionPool;
    }
}
