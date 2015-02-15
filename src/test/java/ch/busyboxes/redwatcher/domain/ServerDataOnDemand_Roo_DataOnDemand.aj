// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ch.busyboxes.redwatcher.domain;

import ch.busyboxes.redwatcher.domain.Server;
import ch.busyboxes.redwatcher.domain.ServerDataOnDemand;
import ch.busyboxes.redwatcher.repository.ServerRepository;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect ServerDataOnDemand_Roo_DataOnDemand {
    
    declare @type: ServerDataOnDemand: @Component;
    
    private Random ServerDataOnDemand.rnd = new SecureRandom();
    
    private List<Server> ServerDataOnDemand.data;
    
    @Autowired
    ServerRepository ServerDataOnDemand.serverRepository;
    
    public Server ServerDataOnDemand.getNewTransientServer(int index) {
        Server obj = new Server();
        setName(obj, index);
        return obj;
    }
    
    public void ServerDataOnDemand.setName(Server obj, int index) {
        String name = "name_" + index;
        obj.setName(name);
    }
    
    public Server ServerDataOnDemand.getSpecificServer(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Server obj = data.get(index);
        Long id = obj.getId();
        return serverRepository.findOne(id);
    }
    
    public Server ServerDataOnDemand.getRandomServer() {
        init();
        Server obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return serverRepository.findOne(id);
    }
    
    public boolean ServerDataOnDemand.modifyServer(Server obj) {
        return false;
    }
    
    public void ServerDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = serverRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Server' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Server>();
        for (int i = 0; i < 10; i++) {
            Server obj = getNewTransientServer(i);
            try {
                serverRepository.save(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            serverRepository.flush();
            data.add(obj);
        }
    }
    
}
