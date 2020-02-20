package is.hi.hbv501.vaktin.Vaktin.Services.Implementation;

import is.hi.hbv501.vaktin.Vaktin.Repositories.UserRepository;
import is.hi.hbv501.vaktin.Vaktin.Services.UserService;
import is.hi.hbv501.vaktin.Vaktin.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }


    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findByUName(String uName) {
        return repository.findByUName(uName);
    }

    @Override
    public User login(User user) {
        User exists = findByUName(user.uName);
        if(exists != null){
            if(exists.password.equals(user.password)){
                return user;
            }
        }
        return null;
    }
}