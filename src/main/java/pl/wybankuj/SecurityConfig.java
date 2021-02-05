package pl.wybankuj;//package pl.wybankuj;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin").password("{noop}Admin2020").roles("ADMIN");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/bank/**").hasRole("ADMIN")
//                .antMatchers("/agency/**").hasRole("ADMIN")
//                .antMatchers("/loan/**").hasRole("ADMIN")
//                .antMatchers("/mortgage/**").hasRole("ADMIN")
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .anyRequest().permitAll()
//                .and().formLogin().loginPage("/login")
//                .and().logout().logoutSuccessUrl("/admin").permitAll()
//                .and().csrf().disable();
//    }
//}
