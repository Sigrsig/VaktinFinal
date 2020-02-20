package is.hi.hbv501.vaktin.Vaktin.Repositories;

import is.hi.hbv501.vaktin.Vaktin.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/***
 * Repository for User
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);
    void delete(User user);
    List<User> findAll();
    User findByUName(String uName);
}