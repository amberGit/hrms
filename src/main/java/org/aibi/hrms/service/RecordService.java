package org.aibi.hrms.service;

import org.aibi.hrms.dao.RecordDAO;
import org.aibi.hrms.pojo.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by John on 2015/12/27.
 */
@Service
public class RecordService {
    @Autowired
    private RecordDAO recordDAO;
    public List<Record> find(int rid) {
        return this.recordDAO.find(rid);
    }
    public void save(Record record) {
        this.recordDAO.save(record);
    }
    public void delete(int id) {
        this.recordDAO.delete(id);
    }
    public void update(Record record) {
        this.recordDAO.update(record);
    }
}
