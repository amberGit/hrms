package org.aibi.hrms.dao;

import org.aibi.hrms.pojo.Identity;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by John on 2015/12/27.
 */
@Repository
public class IdentityDAO {
    @Autowired
    private SqlSession sqlSession;

    public List<Identity> find() {
        return this.sqlSession.selectList("IdentityMapper.findAll");
    }

    public  Identity findRoot() {
        return  this.sqlSession.selectOne("IdentityMapper.findRoot");
    }

    public List<Identity> findSubById(Integer id, int layer) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("layer", layer);
        return sqlSession.selectList("IdentityMapper.findSubById", params);
    }

    public Identity findById(int id) {
        return this.sqlSession.selectOne("IdentityMapper.findById", id);
    }

    public void updateLft(int rgt) {
        this.sqlSession.update("IdentityMapper.update_lft", rgt);
    }

    public void updateRgt(int rgt) {
        this.sqlSession.update("IdentityMapper.update_rgt", rgt);
    }

    public void save(Identity identity) {
        this.sqlSession.insert("IdentityMapper.save", identity);
    }
}
