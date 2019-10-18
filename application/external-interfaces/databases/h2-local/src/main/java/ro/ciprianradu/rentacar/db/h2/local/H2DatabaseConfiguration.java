package ro.ciprianradu.rentacar.db.h2.local;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * Configures the local H2 database. When the database is not yet created, set as environment
 * variable <code>init-db</code> parameter to <code>true</code>.
 */
@Configuration
public class H2DatabaseConfiguration {

    @Bean
    public static DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:file:~/rent-a-car");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }

    private static ResourceDatabasePopulator createResourceDatabasePopulator() {
        final ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(new ClassPathResource("db/h2/schema.sql"));
        databasePopulator.addScript(new ClassPathResource("db/h2/init-data.sql"));

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
