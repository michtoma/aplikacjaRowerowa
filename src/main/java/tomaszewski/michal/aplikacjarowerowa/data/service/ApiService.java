package tomaszewski.michal.aplikacjarowerowa.data.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import tomaszewski.michal.aplikacjarowerowa.data.entity.Trip;
import tomaszewski.michal.aplikacjarowerowa.data.entity.Bike;
import tomaszewski.michal.aplikacjarowerowa.data.entity.User;
import tomaszewski.michal.aplikacjarowerowa.data.repository.BikeRepository;
import tomaszewski.michal.aplikacjarowerowa.data.repository.TripRepository;
import tomaszewski.michal.aplikacjarowerowa.data.repository.UserRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@Controller
public class ApiService extends WebMvcConfigurerAdapter {
    private final TripRepository tripRepository;
    public ApiService(TripRepository tripRepository, BikeRepository bikeRepository, UserRepository userRepository){
        this.tripRepository =tripRepository;

        this.bikeRepository = bikeRepository;
        this.userRepository = userRepository;
    }
    private final BikeRepository bikeRepository;
    private final UserRepository userRepository;
    public List<Trip> findAllTrips(String filterText){
        if(filterText == null|| filterText.isEmpty()){
            return tripRepository.findAll();
        }else{
            return tripRepository.search(filterText);
        }
    }
    public List<Bike> findAllBikes(String filterText){
        if(filterText == null|| filterText.isEmpty()){
            return bikeRepository.findAll();
        }else{
            return bikeRepository.search(filterText);
        }
    }
    public List<User> findAllUsers(String filterText){
        if(filterText == null|| filterText.isEmpty()){
            return userRepository.findAll();
        }else{
            return userRepository.search(filterText);
        }
    }
    public List<Bike> findAllBikes(){

            return bikeRepository.findAll();

    }
    public List<User> findAllUser(){

        return userRepository.findAll();

    }
    public List<Trip> findAllTrips(){

        return tripRepository.findAll();

    }

    public long countTrip(){
        return tripRepository.count();
    }
    public long countUser(){
        return userRepository.count();
    }
    public long countBike(){
        return bikeRepository.count();
    }
    public void deleteTrip(Trip trip){
        tripRepository.delete(trip);
    }
    public void deleteBike(Bike bike){
        bikeRepository.delete(bike);
    }
    public void deleteUser(User user){
        userRepository.delete(user);
    }

    public void saveTrip(Trip trip){
        if(trip==null){
            System.err.println("Trip is null");
        return;

        }
        tripRepository.save(trip);
    }
    public void saveUser(User user){
        if(user==null){
            System.err.println("User is null");
            return;

        }
        userRepository.save(user);
    }
    public void saveBike(Bike bike){
        if(bike==null){
            System.err.println("Bike is null");
            return;

        }
        bikeRepository.save(bike);
    }
    public Date getTripDate(Trip trip){
        return trip.getDate();
    }
    public double averageSpeed(Trip trip){
        double distance = trip.getDistance();
        double time = trip.getTime();
        double avsp = distance/1000 / time * 60;
        return avsp;
    }
    public int getUserAge(User user){
       return  LocalDate.now().getYear()-user.getBirthDate().getYear()-1900;
    }

public String tripDateFormated(Trip trip){
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    LocalDate date = trip.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return date.format(dateTimeFormatter);
}
}
