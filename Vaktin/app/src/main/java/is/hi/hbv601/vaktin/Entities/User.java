package is.hi.hbv601.vaktin.Entities;

@Entity
public class User {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name="password")
    public String password;

    @ColumnInfo(name="token")
    public String token;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.token = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

