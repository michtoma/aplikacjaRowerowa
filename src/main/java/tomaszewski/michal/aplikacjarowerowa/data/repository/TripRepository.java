package tomaszewski.michal.aplikacjarowerowa.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tomaszewski.michal.aplikacjarowerowa.data.entity.Trip;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
    @Query("select c from Trip c " +
            "where lower(c.date) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.date) like lower(concat('%', :searchTerm, '%'))")
   List<Trip> search(@Param("searchTerm") String searchTerm);


}
