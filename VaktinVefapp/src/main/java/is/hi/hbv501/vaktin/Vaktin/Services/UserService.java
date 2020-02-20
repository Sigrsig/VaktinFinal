package is.hi.hbv501.vaktin.Vaktin.Services;

import is.hi.hbv501.vaktin.Vaktin.Entities.User;
import java.util.List;

public interface UserService {

    User save(User user);
    void delete(User user);
    List<User> findAll();
    User findByUName(String uName);
    User login(User user);
}
