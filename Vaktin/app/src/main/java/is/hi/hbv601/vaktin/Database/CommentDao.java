package is.hi.hbv601.vaktin.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import is.hi.hbv601.vaktin.Entities.Comment;

@Dao
public interface CommentDao {

    @Query("DELETE FROM Comment")
    public void nukeTable();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Comment> comments);
}
