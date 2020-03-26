package is.hi.hbv601.vaktin.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import is.hi.hbv601.vaktin.Entities.Footer;

/***
 * Service for Footer entity
 */
@Dao
public interface FooterDao {

    @Query("DELETE FROM Footer")
    public void nukeTable();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertFooter(Footer footer);

    @Query("SELECT * FROM Footer where id = 1")
    public Footer findFoooter();
}
