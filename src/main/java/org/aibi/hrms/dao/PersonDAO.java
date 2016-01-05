package org.aibi.hrms.dao;

import org.aibi.hrms.pojo.Person;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by John on 2015/12/23.
 */
@Repository
public class PersonDAO {
    @Autowired
    private SqlSession sqlSession;

    public Person findById(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return  sqlSession.selectOne("PersonMapper.findById", params);
    }

    public List<Person> findAll() {
        return sqlSession.selectList("PersonMapper.findAll");
    }
    public void save(Person person) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("person", person);
        sqlSession.insert("PersonMapper.save", params);
    }

    public  Person update(Person person) {
        sqlSession.update("PersonMapper.update", person);
        return person;
    }

    public void delete(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        sqlSession.delete("PersonMapper.delete", params);
    }

}
