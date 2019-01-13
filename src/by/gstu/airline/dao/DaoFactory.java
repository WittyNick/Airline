package by.gstu.airline.dao;

import by.gstu.airline.dao.jdbc.DaoFactoryJdbc;

public abstract class DaoFactory {
    public abstract CrewDao getCrewDao();
    public abstract FlightDao getFlightDao();
    public abstract EmployeeDao getEmployeeDao();
    public abstract MemberDao getMemberDao();

    public static DaoFactory getDaoFactory() {
        return DaoFactoryJdbc.getDaoFactory();
    }
}
