package is.hi.hbv601.vaktin.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

import is.hi.hbv601.vaktin.Utilities.LocalDateTimeConverter;

@Entity
public class Employee {

    @PrimaryKey(autoGenerate = true)
    public long employeeId;

    public long employeeWorkstationId;


    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "role")
    public String role;

    @ColumnInfo(name = "tFrom")
    public String tFrom;

    @ColumnInfo(name = "tTo")
    public String tTo;

    public Employee(String name, String role, String tFrom, String tTo) {
        this.name = name;
        this.role = role;
        this.tFrom = tFrom;
        this.tTo = tTo;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public void settFrom(String tFrom) {
        this.tFrom = tFrom;
    }

    public void settTo(String tTo) {
        this.tTo = tTo;
    }

    public long getEmployeeWorkstationId() {
        return employeeWorkstationId;
    }

    public void setEmployeeWorkstationId(long employeeWorkstationId) {
        this.employeeWorkstationId = employeeWorkstationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String gettFrom() {
        return tFrom;
    }

    public String gettTo() {
        return tTo;
    }


}
