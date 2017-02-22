package ch.busyboxes.redwatcher.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "ch.busyboxes.redwatcher.domain")
@Import(RedWatcherJpaTestConfiguration.class)
public class RedWatcherTestConfiguration {

}
