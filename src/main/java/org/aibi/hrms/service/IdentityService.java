package org.aibi.hrms.service;

import org.aibi.hrms.dao.IdentityDAO;
import org.aibi.hrms.pojo.Identity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by John on 2015/12/27.
 */
@Service
public class IdentityService {
    @Autowired
    private IdentityDAO identityDAO;

    public List<Identity> find() {
        return this.identityDAO.find();
    }

    public Identity findRoot() {
        return this.identityDAO.findRoot();
    }

    public List<Identity> findRootTreeByLevel(int layer) {
        return this.identityDAO.findSubById(null,layer);
    }

    public Identity findById(int id) {
        return this.identityDAO.findById(id);
    }

    public List<Identity> findSubById(int id, int layer) {
        return this.identityDAO.findSubById(id, layer);
    }

    public List<?> findBriefByName(String name, Integer limit) {
        return this.identityDAO.findBriefByName(name, limit);
    }

    @Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void saveSubNode(Identity parent, Identity child) throws Exception{
        this.identityDAO.updateLft(parent.getRgt());
        this.identityDAO.updateRgt(parent.getRgt());
        child.setLft(parent.getRgt());
        child.setRgt(parent.getRgt() + 1);
        child.setLayer(parent.getLayer() + 1);
        this.identityDAO.save(child);
    }
}
