package ch.busyboxes.redwatcher.domain;

import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ch.busyboxes.redwatcher.repository.ServerRepository;

@Configurable
@ContextConfiguration(locations = { "classpath:/META-INF/spring-test/testContext*.xml", "classpath:/META-INF/spring/applicationContext-*.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ServerIntegrationTest {

    @Autowired
    ServerDataOnDemand dod;

    @Autowired
    ServerRepository serverRepository;

    @Test
    public void testCount() {
        Assert.assertNotNull("Data on demand for 'Server' failed to initialize correctly", dod.getRandomServer());
        long count = serverRepository.count();
        Assert.assertTrue("Counter for 'Server' incorrectly reported there were no entries", count > 0);
    }

    @Test
    public void testDelete() {
        Server obj = dod.getRandomServer();
        Assert.assertNotNull("Data on demand for 'Server' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Server' failed to provide an identifier", id);
        obj = serverRepository.findOne(id);
        serverRepository.delete(obj);
        serverRepository.flush();
        Assert.assertNull("Failed to remove 'Server' with identifier '" + id + "'", serverRepository.findOne(id));
    }

    @Test
    public void testFind() {
        Server obj = dod.getRandomServer();
        Assert.assertNotNull("Data on demand for 'Server' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Server' failed to provide an identifier", id);
        obj = serverRepository.findOne(id);
        Assert.assertNotNull("Find method for 'Server' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Server' returned the incorrect identifier", id, obj.getId());
    }

    @Test
    public void testFindAll() {
        Assert.assertNotNull("Data on demand for 'Server' failed to initialize correctly", dod.getRandomServer());
        long count = serverRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'Server', as there are " + count
                + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test",
                count < 250);
        List<Server> result = serverRepository.findAll();
        Assert.assertNotNull("Find all method for 'Server' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Server' failed to return any data", result.size() > 0);
    }

    @Test
    public void testFindEntries() {
        Assert.assertNotNull("Data on demand for 'Server' failed to initialize correctly", dod.getRandomServer());
        long count = serverRepository.count();
        if (count > 20)
            count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Server> result = serverRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
        Assert.assertNotNull("Find entries method for 'Server' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Server' returned an incorrect number of entries", count, result.size());
    }

    @Test
    public void testFlush() {
        Server obj = dod.getRandomServer();
        Assert.assertNotNull("Data on demand for 'Server' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Server' failed to provide an identifier", id);
        obj = serverRepository.findOne(id);
        Assert.assertNotNull("Find method for 'Server' illegally returned null for id '" + id + "'", obj);
        boolean modified = dod.modifyServer(obj);
        Integer currentVersion = obj.getVersion();
        serverRepository.flush();
        Assert.assertTrue("Version for 'Server' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion)
                || !modified);
    }

    @Test
    public void testSave() {
        Assert.assertNotNull("Data on demand for 'Server' failed to initialize correctly", dod.getRandomServer());
        Server obj = dod.getNewTransientServer(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Server' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Server' identifier to be null", obj.getId());
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
        Assert.assertNotNull("Expected 'Server' identifier to no longer be null", obj.getId());
    }

    @Test
    public void testSaveUpdate() {
        Server obj = dod.getRandomServer();
        Assert.assertNotNull("Data on demand for 'Server' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Server' failed to provide an identifier", id);
        obj = serverRepository.findOne(id);
        boolean modified = dod.modifyServer(obj);
        Integer currentVersion = obj.getVersion();
        Server merged = serverRepository.save(obj);
        serverRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Server' failed to increment on merge and flush directive",
                (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
}
