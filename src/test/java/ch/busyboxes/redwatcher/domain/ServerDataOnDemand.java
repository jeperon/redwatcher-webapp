package ch.busyboxes.redwatcher.domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import ch.busyboxes.redwatcher.repository.ServerRepository;

@Component
@Configurable
public class ServerDataOnDemand {

    private final Random rnd = new SecureRandom();

    private List<Server> data;

    @Autowired
    ServerRepository serverRepository;

    public Server getNewTransientServer(int index) {
        Server obj = new Server();
        setName(obj, index);
        return obj;
    }

    public Server getRandomServer() {
        init();
        Server obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return serverRepository.findOne(id);
    }

    public Server getSpecificServer(int index) {
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

    public void init() {
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
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage())
                            .append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            serverRepository.flush();
            data.add(obj);
        }
    }

    public boolean modifyServer(Server obj) {
        return false;
    }

    public void setName(Server obj, int index) {
        String name = "name_" + index;
        obj.setName(name);
    }

}
