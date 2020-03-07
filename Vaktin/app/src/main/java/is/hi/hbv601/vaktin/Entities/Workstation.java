package is.hi.hbv601.vaktin.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Workstation  {

    @PrimaryKey(autoGenerate = true)
    public long workstationId;

    @ColumnInfo(name = "workstation_name")
    public String workstationName;

    public Workstation(String workstationName) {
        this.workstationName = workstationName;
    }


    public String getWorkstationName() {
        return workstationName;
    }

    public void setWorkstationName(String name) {
        this.workstationName = name;
    }




    // Hér þarf að gera One-to-Many relationship
    //https://developer.android.com/training/data-storage/room/relationships

    /*public List<Employee> getStaff() {
        return staff;
    }

    public void setStaff(List<Employee> staff) {
        this.staff = staff;
    }*/


}
