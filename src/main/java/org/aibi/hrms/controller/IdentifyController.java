package org.aibi.hrms.controller;

import org.aibi.hrms.pojo.Identity;
import org.aibi.hrms.pojo.Team;
import org.aibi.hrms.service.IdentityService;
import org.aibi.hrms.service.PersonService;
import org.aibi.hrms.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by John on 2015/12/14.
 */
@RestController
public class IdentifyController {

    @Autowired
    private IdentityService identityService;

    @Autowired
    private PersonService personService;

    @Autowired
    private TeamService teamService;

    @RequestMapping(value = "/identity", method = RequestMethod.GET)
    public Identity getIdentity(int id) {
        return this.identityService.findById(id);
    }

    @RequestMapping(value = "/identity/all", method = RequestMethod.GET)
    public List<Identity> getIdentityAll() {
        return this.identityService.find();
    }

    @RequestMapping(value = "/identity", method = RequestMethod.PUT)
    public void addIdentity(int id, int pid) throws Exception {
        Identity parent = getIdentity(id);
        Identity child = new Identity();
        child.setPerson(personService.findById(pid));
        child.setTeam(teamService.findById(1));
        this.identityService.saveUnderSomeone(parent, child);
    }
}
