package fr.fms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)	permet d'activer la sécurité au niveau des méthodes qu'il faudra décorer ainsi pour activer @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired(required = true)
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal, password as credentials, active from User where username=?")
                .authoritiesByUsernameQuery("select users_username as principal, roles_name as role from User_Roles where users_username=?")
                .rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login");
        http.authorizeHttpRequests().antMatchers("/cities", "/film","/addCity","/saveCity","/deleteCity","/editCity","/cinema").hasRole("ADMIN");
        http.authorizeHttpRequests().antMatchers("/reserve").hasRole("USER");
        http.exceptionHandling().accessDeniedPage("/403");

    }
}
