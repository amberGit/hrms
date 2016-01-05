package org.aibi.hrms.service;

import org.aibi.hrms.dao.TeamDAO;
import org.aibi.hrms.pojo.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by John on 2015/12/27.
 */
@Service
public class TeamService {
    @Autowired
    private TeamDAO teamDAO;

    public void save(Team team) {
        this.teamDAO.save(team);
    }

    public void update(Team team) {
        this.teamDAO.update(team);
    }

    public void delete(int id) {
        this.teamDAO.delete(id);
    }
    public Team findById(int id) {
        return this.teamDAO.findById(id);
    }
    public List<Team> find() {
        return this.teamDAO.find();
    }
 }
