package is.hi.hbv601.vaktin.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import is.hi.hbv601.vaktin.Entities.User;

@Dao
public interface UserDao {

    @Update
    public void updateUsers(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(User user);

    @Query("SELECT * FROM user WHERE username = :nombre")
    public User findByName(String nombre);
}
