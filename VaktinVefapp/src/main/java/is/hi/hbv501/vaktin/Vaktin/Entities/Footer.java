
package is.hi.hbv501.vaktin.Vaktin.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Footer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate date;
    private String shiftManager;
    private String shiftManagerNumber; // Telephone number
    private String headDoctor;
    private String headDoctorNumber; // Telephone number

    public Footer() {}

    /***
     * Constructor for Footer
     * @param date
     * @param shiftManager
     * @param shiftManagerNumber
     * @param headDoctor
     * @param headDoctorNumber
     */
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

