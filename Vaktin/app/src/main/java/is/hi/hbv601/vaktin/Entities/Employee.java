package is.hi.hbv601.vaktin.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity
public class Employee {

    @PrimaryKey(autoGenerate = true)
    private long employeeId;

    private long employeeWorkstationId;


    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "role")
    private String role;

    @ColumnInfo(name = "tFrom")
    private LocalDateTime tFrom;

    @ColumnInfo(name = "tTo")
    private LocalDateTime tTo;

    public Employee(String name, String role, LocalDateTime tFrom, LocalDateTime tTo) {
        this.name = name;
        this.role = role;
        this.tFrom = tFrom;
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

    public LocalDateTime gettFrom() {
        return tFrom;
    }

    public void settFrom(LocalDateTime tFrom) {
        this.tFrom = tFrom;
    }

    public LocalDateTime gettTo() {
        return tTo;
    }

    public void settTo(LocalDateTime tTo) {
        this.tTo = tTo;
    }
}
