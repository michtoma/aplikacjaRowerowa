package tomaszewski.michal.aplikacjarowerowa.data.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tomaszewski.michal.aplikacjarowerowa.data.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select c from User c " +
            "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<User> search(@Param("searchTerm") String searchTerm);

    User findByUsername(String username);
}
