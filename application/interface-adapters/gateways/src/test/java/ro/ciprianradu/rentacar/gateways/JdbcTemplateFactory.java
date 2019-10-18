package ro.ciprianradu.rentacar.gateways;

import java.net.URL;
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
        final URL schemaUrl = JdbcTemplateFactory.class.getResource("/db/h2/schema.sql");
        final URL testDataUrl = JdbcTemplateFactory.class.getResource("/db/h2/schema.sql");
        return new JdbcTemplate(new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
            .addScript("file:///" + schemaUrl.getPath())
            .addScript("file:///" + testDataUrl.getPath()).build());
    }

}
