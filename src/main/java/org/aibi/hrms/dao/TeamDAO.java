package org.aibi.hrms.dao;

import org.aibi.hrms.pojo.Team;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by John on 2015/12/27.
 */
@Repository
public class TeamDAO {
    @Autowired
    private SqlSession sqlSession;

    public void save(Team team) {
        this.sqlSession.insert("TeamMapper.save", team);
    }
    public void update(Team team) {
        this.sqlSession.update("TeamMapper.update", team);
    }
    public void delete(int id) {
        this.sqlSession.delete("TeamMapper.delete", id);
    }
    public Team findById(int id) {
        return this.sqlSession.selectOne("TeamMapper.findById", id);
    }
    public List<Team> find() {
        return this.sqlSession.selectList("TeamMapper.findAll");
    }
}
