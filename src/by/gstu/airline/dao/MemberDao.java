package by.gstu.airline.dao;

import by.gstu.airline.entity.Member;

public interface MemberDao extends GenericDao<Member> {
    void deleteByCrewId(int crewId);
}
