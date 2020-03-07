package is.hi.hbv601.vaktin.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity
public class Footer {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "date")
    private LocalDate date;

    @ColumnInfo(name = "shift_manager")
    private String shiftManager;

    @ColumnInfo(name = "shift_manager_number")
    private String shiftManagerNumber;

    @ColumnInfo(name = "head_doctor")
    private String headDoctor;

    @ColumnInfo(name = "head_doctor_number")
    private String headDoctorNumber;

    public Footer(LocalDate date, String shiftManager, String shiftManagerNumber, String headDoctor, String headDoctorNumber) {
        this.date = date;
        this.shiftManager = shiftManager;
        this.shiftManagerNumber = shiftManagerNumber;
        this.headDoctor = headDoctor;
        this.headDoctorNumber = headDoctorNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getShiftManager() {
        return shiftManager;
    }

    public void setShiftManager(String shiftManager) {
        this.shiftManager = shiftManager;
    }

    public String getShiftManagerNumber() {
        return shiftManagerNumber;
    }

    public void setShiftManagerNumber(String shiftManagerNumber) {
        this.shiftManagerNumber = shiftManagerNumber;
    }

    public String getHeadDoctor() {
        return headDoctor;
    }

    public void setHeadDoctor(String headDoctor) {
        this.headDoctor = headDoctor;
    }

    public String getHeadDoctorNumber() {
        return headDoctorNumber;
    }

    public void setHeadDoctorNumber(String headDoctorNumber) {
        this.headDoctorNumber = headDoctorNumber;
    }
}
