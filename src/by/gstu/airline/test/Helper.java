package by.gstu.airline.test;

import by.gstu.airline.config.ConfigurationManager;
import by.gstu.airline.entity.Crew;
import by.gstu.airline.entity.Employee;
import by.gstu.airline.entity.Position;
import by.gstu.airline.service.Service;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class Helper {
    private static final ConfigurationManager manager = ConfigurationManager.INSTANCE;

    public static void main(String[] args) {
        resetTables();
        test();
    }

    private static void test() {
        Service service = Service.INSTANCE;

        List<Employee> employeeList = Arrays.asList(
                new Employee("Name1", "Surname1", Position.PILOT),
                new Employee("Name2", "Surname2", Position.STEWARDESS)
        );
//        service.create(employeeList);
//        service.deleteEmployeeList(employeeList);

        Crew crew1 = new Crew("Ветерок1", employeeList);
        Crew crew2 = new Crew("Ветерок2", null);
    }


    public static void changeDatabase(String sql) {
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

    public static void createTables(Statement statement) throws SQLException {
        statement.executeUpdate(getCreateEmployeeQuery());
        statement.executeUpdate(getCreateCrewQuery());
        statement.executeUpdate(getCreateFlightsQuery());
        statement.executeUpdate(getCreateMemberQuery());
        System.out.println("create tables - success!");
    }

    public static void deleteTables(Statement statement) throws SQLException {
        statement.executeUpdate("DROP TABLE IF EXISTS employees");
        statement.executeUpdate("DROP TABLE IF EXISTS crews");
        statement.executeUpdate("DROP TABLE IF EXISTS flights");
        statement.executeUpdate("DROP TABLE IF EXISTS crew_employee");
        System.out.println("deleteEmployeeList tables - success!");
    }

    // flights
    private static String getCreateFlightsQuery() {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS flights (");
        sql.append("id                INTEGER NOT NULL AUTO_INCREMENT, ");
        sql.append("flightNumber      INTEGER, ");
        sql.append("startPoint        VARCHAR(30), ");
        sql.append("departureDateTime LONG, ");
        sql.append("arrivalDateTime   LONG, ");
        sql.append("plane             VARCHAR(10), ");
        sql.append("crew              INTEGER, ");
        sql.append("PRIMARY KEY (id))");
        return sql.toString();
    }

    // crews
    private static String getCreateCrewQuery() {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS crews (");
        sql.append("id   INTEGER NOT NULL AUTO_INCREMENT, ");
        sql.append("name VARCHAR(30), ");
        sql.append("PRIMARY KEY (id))");
        return sql.toString();
    }

    // members
    private static String getCreateMemberQuery() {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS members (");
        sql.append("id         INTEGER NOT NULL AUTO_INCREMENT, ");
        sql.append("crewId     INTEGER, ");
        sql.append("employeeId INTEGER, ");
        sql.append("PRIMARY KEY (id))");
        return sql.toString();
    }

    // employees
    private static String getCreateEmployeeQuery() {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS employees (");
        sql.append("id       INTEGER NOT NULL AUTO_INCREMENT, ");
        sql.append("name     VARCHAR(30), ");
        sql.append("surname  VARCHAR(30), ");
        sql.append("position INTEGER, ");
        sql.append("PRIMARY KEY (id))");
        return sql.toString();
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
}
