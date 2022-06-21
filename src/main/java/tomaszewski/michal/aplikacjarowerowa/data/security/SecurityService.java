package tomaszewski.michal.aplikacjarowerowa.data.security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextChangedListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tomaszewski.michal.aplikacjarowerowa.data.entity.User;
import tomaszewski.michal.aplikacjarowerowa.data.entity.UserLogin;
import tomaszewski.michal.aplikacjarowerowa.data.repository.UserRepository;

@Service
public class SecurityService implements UserDetailsService {
    private UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void logout(){
        UI.getCurrent().getPage().setLocation("login");
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(),null,null);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
        UserLogin userLogin = new UserLogin(user);
        return userLogin;
    }
}
