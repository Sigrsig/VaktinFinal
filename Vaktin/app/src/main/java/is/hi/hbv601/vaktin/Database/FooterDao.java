package is.hi.hbv601.vaktin.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import is.hi.hbv601.vaktin.Entities.Comment;
import is.hi.hbv601.vaktin.Entities.Footer;
import is.hi.hbv601.vaktin.Entities.Workstation;

@Dao
public interface FooterDao {

    @Query("DELETE FROM Footer")
    public void nukeTable();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertFooter(Footer footer);

    @Query("SELECT * FROM Footer where id = 1")
    public Footer findFoooter();
}
