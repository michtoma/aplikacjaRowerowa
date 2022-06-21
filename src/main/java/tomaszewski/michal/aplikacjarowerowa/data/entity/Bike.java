package tomaszewski.michal.aplikacjarowerowa.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String producer="";
    @NotNull
    private String model ="";
    @NotNull
    private double weight =0;
    @NotNull
    private String type = "";
    @Nullable
    private double wheelSize;

    @JsonIgnore
    @OneToMany(mappedBy = "bike")
    @Nullable
    private Set<Trip> trips = new LinkedHashSet<>();


}

