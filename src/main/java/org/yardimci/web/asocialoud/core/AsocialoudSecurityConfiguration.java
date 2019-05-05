package org.yardimci.web.asocialoud.core;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class AsocialoudSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication().withUser(
                User.withDefaultPasswordEncoder().username("user").password("user").roles("USER").build());
        auth.inMemoryAuthentication().withUser(
                User.withDefaultPasswordEncoder().username("admin").password("admin").roles("USER", "ADMIN").build());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // zk specific -- maybe disable CSRF set by default via auto-configuration? --
        http.csrf().disable() // can be disabled safely; ZK unique desktop ID generation prevents Cross-Site Request Forgery attacks

                // application specific
                .authorizeRequests()
                .antMatchers("/zkau/web/**/js/**","/zkau/web/**/zul/css/**","/zkau/web/**/img/**").permitAll()

                .mvcMatchers("/","/login","/logout").permitAll()
                .mvcMatchers("secure/**").hasRole("USER")
                //.mvcMatchers("/profile").hasRole("USER")
                .mvcMatchers("/admin").hasRole("ADMIN")

                .antMatchers("/zkau/web/**/**.zul").denyAll() // calling a zul-page directly is not allowed -- should we put this in the auto-configuration to? --

                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }

}