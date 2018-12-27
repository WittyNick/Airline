package by.gstu.airline.dao;

import by.gstu.airline.entity.Member;

import java.util.List;

public interface MemberDao extends GenericDao<Member> {
    List<Member> find();
}
