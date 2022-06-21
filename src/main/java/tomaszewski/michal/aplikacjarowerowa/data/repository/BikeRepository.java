package tomaszewski.michal.aplikacjarowerowa.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tomaszewski.michal.aplikacjarowerowa.data.entity.Bike;

import java.util.List;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Integer> {
    @Query("select c from Bike c " +
            "where lower(c.producer) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.model) like lower(concat('%', :searchTerm, '%'))")
    List<Bike> search(@Param("searchTerm") String searchTerm);
}
