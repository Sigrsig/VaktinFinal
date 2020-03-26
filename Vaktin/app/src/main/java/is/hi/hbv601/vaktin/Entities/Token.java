package is.hi.hbv601.vaktin.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalDateTime;

import is.hi.hbv601.vaktin.Utilities.LocalDateConverter;
import is.hi.hbv601.vaktin.Utilities.LocalDateTimeConverter;

/***
 * Entity for Token
 * Primary key is id
 * Boolean alreadyInitialized to check if user has already fetched data from H-2 database
 * String today to check if a day has passed. Fetches data if a day has passed
 * String lastFetched to check if data has been modified since last fetch. Probably makes
 * string today pointless
 */
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

    @ColumnInfo(name = "last_fetched")
    public String lastFetched;

    public Token(String token) {
        this.token = token;
        this.alreadyInitialized = false;
        this.today = LocalDateConverter.toDateString(LocalDate.now());
        this.lastFetched = LocalDateTimeConverter.toDateString(LocalDateTime.now());
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

    public String getLastFetched() {
        return lastFetched;
    }

    public void setLastFetched(String lastFetched) {
        this.lastFetched = lastFetched;
    }
}
