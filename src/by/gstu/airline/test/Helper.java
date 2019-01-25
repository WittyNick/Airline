package by.gstu.airline.test;

import by.gstu.airline.config.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Locale;

public class Helper {
    private static final ConfigurationManager manager = ConfigurationManager.INSTANCE;

    public static void main(String[] args) {
//        System.out.println(new Locale("ru", "RU"));
//        createDatabase("airlineLite");
//        resetTables();
//        test();
    }

    private static void test() {
//        Service service = Service.INSTANCE;
        Locale ru = new Locale("ru", "RU");
        manager.changeLocale(ru);

//        Employee pilot1 = new Employee("Иван", "Драго", Position.PILOT);
//        Employee stewardess1 = new Employee("Татьяна", "Минеева", Position.STEWARDESS);
//        Employee stewardess2 = new Employee("Наталия", "Правдина", Position.STEWARDESS);
//        Employee navigator1 = new Employee("Джейсон", "Борн", Position.NAVIGATOR);
//        Employee communicator1 = new Employee("Игорь", "Крутой", Position.COMMUNICATOR);
//        service.create(pilot1);
//        service.create(stewardess1);
//        service.create(stewardess2);
//        service.create(navigator1);
//        service.create(communicator1);

//        Crew crew1 = new Crew("Ветерок1");
//        service.create(crew1);
//
//        service.create(new Member(crew1, pilot1));
//        service.create(new Member(crew1, stewardess1));
//
//        Flight flight1 = new Flight(
//                104,
//                "Минск",
//                "Москва",
//                "31.12.2018",
//                "23:00",
//                "01.01.2019",
//                "01:00",
//                "SuperJet"
//        );
//        Flight flight2 = new Flight(
//                204,
//                "Moscow",
//                "Minsk",
//                "01.01.2019",
//                "02:00",
//                "01.01.2019",
//                "04:00",
//                "Ty-154"
//        );
//        service.create(flight1);
//        service.create(flight2);
//
//        flight2.setCrew(crew1);
//        service.update(flight2);
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
        statement.executeUpdate("DROP TABLE IF EXISTS members");
        System.out.println("deleteEmployeeList tables - success!");
    }

    // flights
    private static String getCreateFlightsQuery() {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS flights (");
        sql.append("id                INTEGER NOT NULL AUTO_INCREMENT,             ");
        sql.append("flightNumber      INTEGER,                                     ");
        sql.append("startPoint        VARCHAR(30),                                 ");
        sql.append("destinationPoint  VARCHAR(30),                                 ");
        sql.append("departureDateTime LONG,                                        ");
        sql.append("arrivalDateTime   LONG,                                        ");
        sql.append("plane             VARCHAR(10),                                 ");
        sql.append("crew              INTEGER,                                     ");
        sql.append("PRIMARY KEY (id))                                              ");
        return sql.toString();
    }

    // crews
    private static String getCreateCrewQuery() {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS crews (");
        sql.append("id   INTEGER NOT NULL AUTO_INCREMENT,                        ");
        sql.append("name VARCHAR(30),                                            ");
        sql.append("PRIMARY KEY (id))                                            ");
        return sql.toString();
    }

    // members
    private static String getCreateMemberQuery() {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS members (");
        sql.append("id         INTEGER NOT NULL AUTO_INCREMENT,                    ");
        sql.append("crewId     INTEGER,                                            ");
        sql.append("employeeId INTEGER,                                            ");
        sql.append("PRIMARY KEY (id))                                              ");
        return sql.toString();
    }

    // employees
    private static String getCreateEmployeeQuery() {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS employees (");
        sql.append("id       INTEGER NOT NULL AUTO_INCREMENT,                        ");
        sql.append("name     VARCHAR(30),                                            ");
        sql.append("surname  VARCHAR(30),                                            ");
        sql.append("position INTEGER,                                                ");
        sql.append("PRIMARY KEY (id))                                                ");
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

    // TRUNCATE TABLE employees;

    //    ALTER DATABASE database_name
    //    CHARACTER SET utf8mb4
    //    COLLATE utf8mb4_unicode_ci;
}
