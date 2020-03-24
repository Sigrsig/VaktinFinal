package is.hi.hbv601.vaktin.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
