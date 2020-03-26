package is.hi.hbv601.vaktin.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/***
 * Entity for Workstation
 * Primary key is workstationId
 * One-to-many relationship between Workstation and Entity implemented in
 * WorkstationWithEmployee entity
 */
@Entity
public class Workstation  {


    @PrimaryKey(autoGenerate = true)
    public long workstationId;

    @ColumnInfo(name = "workstation_name")
    public String workstationName;

    public Workstation(String workstationName) {
        this.workstationName = workstationName;
    }

    public long getWorkstationId() {
        return workstationId;
    }

    public void setWorkstationId(long workstationId) {
        this.workstationId = workstationId;
    }

    public String getWorkstationName() {
        return workstationName;
    }

    public void setWorkstationName(String name) {
        this.workstationName = name;
    }


}
