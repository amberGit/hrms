package org.aibi.hrms.controller;

import org.aibi.hrms.pojo.Greeting;
import org.aibi.hrms.pojo.Person;
import org.aibi.hrms.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by John on 2015/12/13.
 */
@RestController
public class GreetingController {
    private static final String template = "Hello, %s";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/greeting",method = RequestMethod.GET)
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping(value = "/allPerson", method = RequestMethod.GET)
    public List<Person> getAllPerson() {
        return personService.find();
    }

    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public Person getPerson(@RequestParam(value = "id") int id) {
        return  personService.findById(id);
    }

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public Person addPerson(@RequestParam(value = "name") String name) {
        Person person = new Person();
        person.setName(name);
        personService.save(person);
        return person;
    }

    @RequestMapping(value = "/person", method = RequestMethod.PUT)
    public Person updatePerson(@RequestParam(value = "id") int id, @RequestParam(value = "name") String name) {
        Person person = new Person();
        person.setName(name);
        person.setId(id);
        return personService.update(person);
    }

    @RequestMapping(value = "/person", method = RequestMethod.DELETE)
    public void delete(@RequestParam(value = "id") int id) {
        personService.delete(id);
    }
}
