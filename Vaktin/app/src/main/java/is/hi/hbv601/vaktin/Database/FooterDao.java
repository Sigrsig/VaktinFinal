package is.hi.hbv601.vaktin.Database;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface FooterDao {

    @Query("DELETE FROM Footer")
    public void nukeTable();
}
