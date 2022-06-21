package tomaszewski.michal.aplikacjarowerowa.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private Date date;
    @NotNull
    private String name;
    @ManyToOne
    @JoinColumn(name="trip_id")
    @JsonIgnoreProperties
    @NotNull
    private User user;
    @ManyToOne
    @JoinColumn(name="bikes_id")
    @JsonIgnoreProperties
    @NotNull
    private Bike bike;
    @NotNull
    private double distance;
    @NotNull
    private double time;

    public Date getDate() {
        return date;
    }

    public Trip(Date date, String name, User user, Bike bike, double distance, double time) {
        this.date = date;
        this.name = name;
        this.user = user;
        this.bike = bike;
        this.distance = distance;
        this.time = time;
    }
}

