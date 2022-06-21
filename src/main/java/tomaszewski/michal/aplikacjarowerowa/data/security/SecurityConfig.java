package tomaszewski.michal.aplikacjarowerowa.data.security;

import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import tomaszewski.michal.aplikacjarowerowa.data.entity.UserLogin;
import tomaszewski.michal.aplikacjarowerowa.data.repository.UserRepository;
import tomaszewski.michal.aplikacjarowerowa.data.service.ApiService;
import tomaszewski.michal.aplikacjarowerowa.view.LoginView;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends VaadinWebSecurityConfigurerAdapter {
    private SecurityService securityService;

    public SecurityConfig(SecurityService securityService) {
        this.securityService = securityService;
    }

    private UserRepository userRepository;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, LoginView.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)  {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/images/**");
        super.configure(web);
    }

    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.securityService);
        return daoAuthenticationProvider;
    }
    @Bean
    PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//
//
//
//        return new InMemoryUserDetailsManager(User.withUsername("Michal").password("{noop}pass").roles("ADMIN").build(),User.withUsername("User").password("{noop}pass").roles("USER").build());
//
//        //TODO: wczytywanie z BD użytkowników z ich uprawnieniami
//    }


}
