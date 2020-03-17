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

    @Query("DELETE FROM Comment where description like :description")
    public void deleteComment(String description);

    @Query("DELETE FROM Comment")
    public void nukeTable();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Comment> comments);

    @Query("SELECT * FROM Comment where description like :description")
    public Comment findCommentWithDescription(String description);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertComment(Comment comments);

    @Query("SELECT * FROM Comment")
    public List<Comment> findAllComment();
}
