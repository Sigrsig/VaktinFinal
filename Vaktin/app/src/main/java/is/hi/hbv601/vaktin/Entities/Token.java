package is.hi.hbv601.vaktin.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalDateTime;

import is.hi.hbv601.vaktin.Utilities.LocalDateConverter;
import is.hi.hbv601.vaktin.Utilities.LocalDateTimeConverter;

@Entity(tableName = "Token")
public class Token {

    @PrimaryKey
    private long id = 1;

    @ColumnInfo(name = "token")
    private String token;

    @ColumnInfo(name = "already_initialized")
    public boolean alreadyInitialized;

    @ColumnInfo(name = "today")
    public String today;

    public Token(String token) {
        this.token = token;
        this.alreadyInitialized = false;
        this.today = LocalDateConverter.toDateString(LocalDate.now());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isAlreadyInitialized() {
        return alreadyInitialized;
    }

    public void setAlreadyInitialized(boolean alreadyInitialized) {
        this.alreadyInitialized = alreadyInitialized;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }
}
