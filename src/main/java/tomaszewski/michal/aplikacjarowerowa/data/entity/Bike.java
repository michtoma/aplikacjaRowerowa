package tomaszewski.michal.aplikacjarowerowa.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.annotation.Nullable;
import javax.persistence.*;
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
    @NonNull
    private String producer="";
    @NonNull
    private String model ="";
    @NonNull
    private double weight =0;
    @NonNull
    private String type = "";
    @Nullable
    private double wheelSize;

    @JsonIgnore
    @OneToMany(mappedBy = "bike")
    @Nullable
    private Set<Trip> trips = new LinkedHashSet<>();


    public Bike(@NonNull String producer, @NonNull String model, @NonNull double weight, @NonNull String type, @NonNull double wheelSize) {
        this.producer = producer;
        this.model = model;
        this.weight = weight;
        this.type = type;
        this.wheelSize = wheelSize;
    }
}

