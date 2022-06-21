package tomaszewski.michal.aplikacjarowerowa;

import com.vaadin.flow.server.PWA;
import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
@SpringBootApplication
public class AplikacjaRowerowaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AplikacjaRowerowaApplication.class, args);
    }


}
