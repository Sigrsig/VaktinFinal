package is.hi.hbv601.vaktin.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import is.hi.hbv601.vaktin.Entities.Token;

@Dao
public interface TokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertToken(Token t);

    @Query("SELECT * FROM Token WHERE id = :id")
    public Token findById(long id);
}
