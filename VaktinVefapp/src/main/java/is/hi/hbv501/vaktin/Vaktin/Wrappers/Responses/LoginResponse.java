package is.hi.hbv501.vaktin.Vaktin.Wrappers.Responses;

import is.hi.hbv501.vaktin.Vaktin.Entities.User;

import java.util.List;

public class LoginResponse extends GenericResponse {

    private User user;

    public LoginResponse(User user) {
        this.user = user;
    }

    public LoginResponse(String message, List<?> errors, User user) {
        super(message, errors);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
