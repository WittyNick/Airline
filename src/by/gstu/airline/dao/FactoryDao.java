package by.gstu.airline.dao;

import by.gstu.airline.dao.jdbc.FactoryDaoJdbc;

public abstract class FactoryDao {
    public abstract CrewDao getCrewDao();
    public abstract FlightDao getFlightDao();
    public abstract EmployeeDao getEmployeeDao();
    public abstract MemberDao getMemberDao();

    public static FactoryDao getDaoFactory() {
        return FactoryDaoJdbc.getDaoFactory();
    }
}
