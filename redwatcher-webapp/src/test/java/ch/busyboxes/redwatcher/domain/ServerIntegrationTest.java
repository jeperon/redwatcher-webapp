package ch.busyboxes.redwatcher.domain;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = { "classpath:/META-INF/spring-test/testContext*.xml", "classpath:/META-INF/spring/applicationContext-*.xml" })
@RooIntegrationTest(entity = Server.class)
public class ServerIntegrationTest {

	@Test
	public void testMarkerMethod() {
	}
}
