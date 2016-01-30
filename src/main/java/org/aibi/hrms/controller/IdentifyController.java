package org.aibi.hrms.controller;

import org.aibi.hrms.pojo.Identity;
import org.aibi.hrms.pojo.Person;
import org.aibi.hrms.pojo.Response;
import org.aibi.hrms.service.IdentityService;
import org.aibi.hrms.service.PersonService;
import org.aibi.hrms.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

    @RequestMapping(value = "/identity/brief", method = RequestMethod.GET)
    public List<?> getIdentityIdAndNameList(String name) {
        Integer limit = null;
        if (StringUtils.isEmpty(name)) limit = 30;
        return this.identityService.findBriefByName(name, limit);
    }

    @RequestMapping(value = "/identity/all", method = RequestMethod.GET)
    public List<Identity> getIdentityAll() {
        return this.identityService.find();
    }

    @RequestMapping(value = "/identity", method = RequestMethod.POST)
    public Response addIdentity(@RequestParam(value = "parentId") int parentId, @RequestParam(value = "childPersonName") String childPersonName) throws Exception {
        Identity parent = getIdentity(parentId);
        Identity child = new Identity();
        Person childPerson = personService.findByName(childPersonName);

        if (childPerson == null) {
            childPerson = new Person();
            childPerson.setName(childPersonName);
            childPerson.setId(personService.save(childPerson));
        }
        child.setPerson(childPerson);
        child.setTeam(teamService.findById(parent.getTeam().getId()));
        this.identityService.saveSubNode(parent, child);
        Response response = new Response();
        response.setStatus(Response.Status.OK);
        return  response;
    }


}
