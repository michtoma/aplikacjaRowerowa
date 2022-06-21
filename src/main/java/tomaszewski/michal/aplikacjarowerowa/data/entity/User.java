package tomaszewski.michal.aplikacjarowerowa.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vaadin.flow.templatemodel.Encode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String username ="";
    @NotBlank
    private String password ="";

    private String firstName = "";
    private String lastName ="";
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date birthDate;

    @NotNull
    private double weight;
    @NotNull
    private String role;
    public void setPassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    @NotNull


    @JsonIgnoreProperties({"trip"})
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @Nullable
    private Set<Trip> trip = new LinkedHashSet<>();

    public User(String username, String password, String firstName, String lastName, Date birthDate, double weight, String role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.weight = weight;
        this.role = role;

    }




}

