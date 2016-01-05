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

    public Identity findById(int id) {
        return this.identityDAO.findById(id);
    }

    @Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void saveUnderSomeone(Identity parent, Identity child) throws Exception{
        this.identityDAO.updateLft(parent.getRgt());
        this.identityDAO.updateRgt(parent.getRgt());
        child.setLft(parent.getRgt());
        child.setRgt(parent.getRgt() + 1);
        child.setLayer(parent.getLayer() + 1);
        this.identityDAO.save(child);
    }
}