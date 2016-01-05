package org.aibi.hrms.service;

import org.aibi.hrms.dao.PersonDAO;
import org.aibi.hrms.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by John on 2015/12/15.
 */
@Service
public class PersonService {

    @Autowired
    private PersonDAO personDAO;

    public List<Person> find() {
        return this.personDAO.findAll();
    }

    public Person findById(int id) {
        return this.personDAO.findById(id);
    }

    public void save(Person person) {
        this.personDAO.save(person);
    }

    public Person update(Person person) {
        return this.personDAO.update(person);
    }

    public void delete(int id) {
        this.personDAO.delete(id);
    }
}
