package is.hi.hbv601.vaktin.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/***
 * Entity for Comment
 * Primary key is description string instead of auto-generated ID. Makes communication
 * between Room and RestController easier. Could have made a seperate ID for H-2 database
 * instead
 */
@Entity
public class Comment {

    /*
    @PrimaryKey(autoGenerate = true)
    public long id;
     */

    @PrimaryKey
    @ColumnInfo(name ="description")
    @NonNull
    private String description;

    public Comment(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
