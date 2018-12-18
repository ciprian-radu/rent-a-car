package ro.ciprianradu.rentacar.gateways;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 *
 */
class JdbcTemplateFactory {

    private JdbcTemplateFactory() {
        // Static Factory
    }

    public static JdbcTemplate createJdbcTemplate() {
        return new JdbcTemplate(new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:db/h2/schema.sql")
            .addScript("classpath:db/h2/test-data.sql").build());
    }

}
