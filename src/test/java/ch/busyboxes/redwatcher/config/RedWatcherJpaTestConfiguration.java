package ch.busyboxes.redwatcher.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Database application configuration
 */
@Configuration
@EnableJpaRepositories(basePackages = "ch.busyboxes.redwatcher.repository", transactionManagerRef = "transactionManager")
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
public class RedWatcherJpaTestConfiguration {

    @Value("${database.driverClassName}")
    private String driverClassName;

    @Value("${database.url}")
    private String url;

    @Value("${database.username}")
    private String username;

    @Value("${database.password}")
    private String password;

    private boolean testOnBorrow = true;
    private boolean testOnReturn = true;
    private boolean testWhileIdle = true;
    private long timeBetweenEvictionRunsMillis = 1800000;
    private int numTestsPerEvictionRun = 3;
    private long minEvictableIdleTimeMillis = 1800000;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setPersistenceUnitName("persistenceUnit");
        entityManagerFactory.setDataSource(dataSource());

        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("eclipselink.target-database", "org.eclipse.persistence.platform.database.HSQLPlatform");
        jpaProperties.setProperty("eclipselink.ddl-generation", "create-tables");
        jpaProperties.setProperty("eclipselink.logging.level.sql", "FINE");
        jpaProperties.setProperty("eclipselink.logging.parameters", "true");
        entityManagerFactory.setJpaProperties(jpaProperties);
        return entityManagerFactory;
    }

}
