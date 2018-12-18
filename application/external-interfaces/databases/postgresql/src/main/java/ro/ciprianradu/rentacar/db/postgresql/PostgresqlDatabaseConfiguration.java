package ro.ciprianradu.rentacar.db.postgresql;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * Configures the local PostgreSQL database. When the database is not yet created, set as environment variable <code>init-db</code> parameter to <code>true</code>.
 */
@Configuration
public class PostgresqlDatabaseConfiguration {

    private static final Logger log = LoggerFactory.getLogger(PostgresqlDatabaseConfiguration.class);

    @Bean
    public static DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(getDbUrlProperty());
        dataSource.setUsername(getDbUserProperty());
        dataSource.setPassword(getDbPasswordProperty());

        return dataSource;
    }

    private static String getDbUrlProperty() {
        String dbUrl = System.getProperty("DB_URL");

        if (dbUrl == null) {
            log.error("No Rent a Car database URL specified!");
        } else {
            log.info("Using Rent a Car database URL {}", dbUrl);
        }

        return dbUrl;
    }

    private static String getDbUserProperty() {
        String dbUser = System.getProperty("DB_USER");

        if (dbUser == null) {
            log.error("No Rent a Car database user specified!");
        } else {
            log.info("Using Rent a Car database user {}", dbUser);
        }

        return dbUser;
    }

    private static String getDbPasswordProperty() {
        String dbPassword = System.getProperty("DB_PASSWORD");

        if (dbPassword == null) {
            log.error("No Rent a Car database password specified!");
        } else {
            log.info("Using Rent a Car database password {}", dbPassword);
        }

        return dbPassword;
    }

    private static ResourceDatabasePopulator createResourceDatabasePopulator() {
        final ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(new ClassPathResource("schema.sql"));
        databasePopulator.addScript(new ClassPathResource("init-data.sql"));

        return databasePopulator;
    }

    @Bean
    @Autowired
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        final DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(createResourceDatabasePopulator());
        dataSourceInitializer.setEnabled(Boolean.parseBoolean(getInitDbProperty()));
        return dataSourceInitializer;
    }

    private String getInitDbProperty() {
        String initDbProperty = System.getProperty("init-db");

        if (initDbProperty == null || !"true".equals(initDbProperty)) {
            initDbProperty = "false";
        }
        return initDbProperty;
    }

    @Bean
    @Autowired
    public JdbcTemplate createJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
