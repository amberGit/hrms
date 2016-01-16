package org.aibi.hrms.controller;

import org.aibi.hrms.pojo.Identity;
import org.aibi.hrms.service.IdentityService;
import org.aibi.hrms.service.PersonService;
import org.aibi.hrms.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "/identity/root", method = RequestMethod.GET)
    public Identity getRoot() {
        return this.identityService.findRoot();
    }
    @RequestMapping(value = "/identity/rootTree", method = RequestMethod.GET)
    public List<Identity> findRootTreeByLevel(int level) {
        return this.identityService.findRootTreeByLevel(level);
    }

    @RequestMapping(value = "/identity/sub", method = RequestMethod.GET)
    public List<Identity> getSubIdentity(@RequestParam(value = "id") Integer id, @RequestParam(value = "layer") int layer) {
        return this.identityService.findSubById(id, layer);
    }

    @RequestMapping(value = "/identity/all", method = RequestMethod.GET)
    public List<Identity> getIdentityAll() {
        return this.identityService.find();
    }

    @RequestMapping(value = "/identity", method = RequestMethod.PUT)
    public void addIdentity(int parentId, int childPersonId) throws Exception {
        Identity parent = getIdentity(parentId);
        Identity child = new Identity();
        child.setPerson(personService.findById(childPersonId));
        child.setTeam(teamService.findById(parent.getTeam().getId()));
        this.identityService.saveSubNode(parent, child);
    }
}
