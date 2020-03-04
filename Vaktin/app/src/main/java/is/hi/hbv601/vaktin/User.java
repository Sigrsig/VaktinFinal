package is.hi.hbv601.vaktin;

public class User {
    private long id;
    public String uName;
    public String password;

    public User(String uName, String password){
        this.id = -1;
        this.uName = uName;
        this.password = password;
    }
}

