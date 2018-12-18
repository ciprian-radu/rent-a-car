package ro.ciprianradu.rentacar.auth.client.configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableOAuth2Client // See https://stackoverflow.com/questions/42938782/spring-enableresourceserver-vs-enableoauth2sso
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(
                "/", "/index.html", "/about.html", "/cars.html", "/service.html", "/contact.html",
                "/registration.html", "/registration",
                "/css/**",
                "/fonts/**",
                "/img/**",
                "/js/**",
                "/scss/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login.html")
            .permitAll()
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login.html?logout")
            .permitAll();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JdbcUserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception {
        //set user detail service manually
        final JdbcUserDetailsManager jdbcUserDetailsManager = userDetailsManager(dataSource);
        auth.userDetailsService(jdbcUserDetailsManager);
        JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> configurer =
            new JdbcUserDetailsManagerConfigurer<>(jdbcUserDetailsManager);
        configurer.dataSource(dataSource);
        configurer.usersByUsernameQuery("select username, password, enabled from users where username=?")
            .authoritiesByUsernameQuery("select username, authority from authorities where username=?");
        //apply the configurer
        auth.apply(configurer);
    }

    private static ResourceDatabasePopulator createResourceDatabasePopulator() {
        final ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(new ClassPathResource("db/schema.sql"));
        databasePopulator.addScript(new ClassPathResource("db/init-data.sql"));

        return databasePopulator;
    }

    @Bean
    @Autowired
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        final DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(createResourceDatabasePopulator());
        dataSourceInitializer.setEnabled(initDb());
        return dataSourceInitializer;
    }

    private boolean initDb() {
        String initDbProperty = System.getProperty("init-db");

        if (initDbProperty == null || !"true".equals(initDbProperty)) {
            initDbProperty = "false";
        }

        return Boolean.parseBoolean(initDbProperty);
    }

}
