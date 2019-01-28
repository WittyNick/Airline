package by.gstu.airline.util;

import by.gstu.airline.config.ConfigurationManager;

import java.sql.*;

/**
 * The class provides utility to work with database.
 */
public class Helper {
    private static final ConfigurationManager manager = ConfigurationManager.INSTANCE;

    public static void main(String[] args) {
//        createDatabase("airlineLite");
//        resetTables();

    }

    private static void changeDatabase(String sql) {
        Connection connection = getConnection(
                "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC",
                manager.getProperty("db.user"),
                manager.getProperty("db.password"));
        if (connection == null) {
            return;
        }
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("database change - success!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, statement);
        }
    }

    public static void resetTables() {
        Connection connection = getConnection(
                manager.getProperty("db.url"),
                manager.getProperty("db.user"),
                manager.getProperty("db.password"));
        if (connection == null) {
            return;
        }
        Statement statement = null;
        try {
            statement = connection.createStatement();
            deleteTables(statement);
            createTables(statement);
            System.out.println("database has been reset!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, statement);
        }
    }

    private static void createTables(Statement statement) throws SQLException {
        statement.executeUpdate(getCreateEmployeeQuery());
        statement.executeUpdate(getCreateCrewQuery());
        statement.executeUpdate(getCreateFlightsQuery());
        statement.executeUpdate(getCreateMemberQuery());
        System.out.println("create tables - success!");
    }

    private static void deleteTables(Statement statement) throws SQLException {
        statement.executeUpdate("DROP TABLE IF EXISTS employees");
        statement.executeUpdate("DROP TABLE IF EXISTS crews");
        statement.executeUpdate("DROP TABLE IF EXISTS flights");
        statement.executeUpdate("DROP TABLE IF EXISTS members");
        System.out.println("deleteEmployeeList tables - success!");
    }

    // flights
    private static String getCreateFlightsQuery() {
        return "CREATE TABLE IF NOT EXISTS flights (" +
                "id                INTEGER NOT NULL AUTO_INCREMENT, " +
                "flightNumber      INTEGER, " +
                "startPoint        VARCHAR(30), " +
                "destinationPoint  VARCHAR(30), " +
                "departureDateTime LONG, " +
                "arrivalDateTime   LONG, " +
                "plane             VARCHAR(10), " +
                "crew              INTEGER, " +
                "PRIMARY KEY (id))";
    }

    // crews
    private static String getCreateCrewQuery() {
        return "CREATE TABLE IF NOT EXISTS crews (" +
                "id   INTEGER NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(30), " +
                "PRIMARY KEY (id))";
    }

    // members
    private static String getCreateMemberQuery() {
        return "CREATE TABLE IF NOT EXISTS members (" +
                "id         INTEGER NOT NULL AUTO_INCREMENT, " +
                "crewId     INTEGER, " +
                "employeeId INTEGER, " +
                "PRIMARY KEY (id))";
    }

    // employees
    private static String getCreateEmployeeQuery() {
        return "CREATE TABLE IF NOT EXISTS employees (" +
                "id       INTEGER NOT NULL AUTO_INCREMENT, " +
                "name     VARCHAR(30), " +
                "surname  VARCHAR(30), " +
                "position INTEGER, " +
                "PRIMARY KEY (id))";
    }

    private static void createDatabase(String databaseName) {
        changeDatabase("CREATE DATABASE IF NOT EXISTS " + databaseName);
    }

    private static void deleteDatabase(String databaseName) {
        changeDatabase("DROP DATABASE IF EXISTS " + databaseName);
    }

    private static Connection getConnection(String url, String user, String password) {
        Connection connection = null;
        try {
            Class.forName(manager.getProperty("db.driver"));
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static void close(Connection connection, Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // TRUNCATE TABLE employees;

    //    ALTER DATABASE database_name
    //    CHARACTER SET utf8mb4
    //    COLLATE utf8mb4_unicode_ci;
}
