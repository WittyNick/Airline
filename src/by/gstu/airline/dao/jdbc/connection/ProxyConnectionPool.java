package by.gstu.airline.dao.jdbc.connection;

import by.gstu.airline.config.ConfigurationManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProxyConnectionPool {
    private ConfigurationManager manager = ConfigurationManager.INSTANCE;
    private BlockingQueue<ProxyConnection> connectionQueue;

    public ProxyConnectionPool(final int poolSize) throws SQLException {
        if (poolSize < 1) {
            throw new IllegalArgumentException("pool size less than 1");
        }
        connectionQueue = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            ProxyConnection connection = createConnection();
            connectionQueue.offer(connection);
        }
    }

    public ProxyConnection getConnection() throws InterruptedException {
        return connectionQueue.take();
    }

    public void closeConnection(ProxyConnection connection) {
        connectionQueue.offer(connection);
    }

    private ProxyConnection createConnection() throws SQLException {
        try {
            Class.forName(manager.getProperty("db.driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = DriverManager.getConnection(manager.getProperty("db.url"),
                manager.getProperty("db.user"), manager.getProperty("db.password"));
        return new ProxyConnection(connection);
    }
}
