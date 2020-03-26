package is.hi.hbv601.vaktin.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

/***
 * Entity for Footer
 * Primary key is id
 */
@Entity
public class Footer {

    @PrimaryKey
    private long id;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "shift_manager")
    private String shiftManager;

    @ColumnInfo(name = "shift_manager_number")
    private String shiftManagerNumber;

    @ColumnInfo(name = "head_doctor")
    private String headDoctor;

    @ColumnInfo(name = "head_doctor_number")
    private String headDoctorNumber;

    public Footer(String date, String shiftManager, String shiftManagerNumber, String headDoctor, String headDoctorNumber) {
        this.id=1;
        this.date = date;
        this.shiftManager = shiftManager;
        this.shiftManagerNumber = shiftManagerNumber;
        this.headDoctor = headDoctor;
        this.headDoctorNumber = headDoctorNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
