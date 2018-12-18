package ro.ciprianradu.rentacar.db.h2.embedded;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * Configures the H2 embedded database
 */
@Configuration
public class H2EmbeddedDatabaseConfiguration {

    @Bean
    public JdbcTemplate createJdbcTemplate() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:db/h2/schema.sql")
            .addScript("classpath:db/h2/init-data.sql").build());

        return jdbcTemplate;
    }

}
