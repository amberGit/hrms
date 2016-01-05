package org.aibi.hrms.dao;

import com.sun.prism.impl.Disposer;
import org.aibi.hrms.pojo.Record;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by John on 2015/12/26.
 */
@Repository
public class RecordDAO {
    @Autowired
    private SqlSession sqlSession;

    public List<Record> find(int rid) {
        return this.sqlSession.selectList("recordMapper.findAll", rid);
    }
    public void save(Record record) {
        this.sqlSession.insert("recordMapper.save", record);
    }
    public void update(Record record) {
        this.sqlSession.update("recordMapper.update", record);
    }
    public void delete(int id) {
        this.sqlSession.delete("recordMapper.delete", id);
    }
}
