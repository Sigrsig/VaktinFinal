package is.hi.hbv501.vaktin.Vaktin.Services.Implementation;

import is.hi.hbv501.vaktin.Vaktin.Entities.User;
import is.hi.hbv501.vaktin.Vaktin.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getuName(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

}
