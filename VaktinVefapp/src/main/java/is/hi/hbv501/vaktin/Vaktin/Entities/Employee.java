
package is.hi.hbv501.vaktin.Vaktin.Entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/***
 * Entity for Employee
 * Keeps an instance of one Employee with name of employee, time
 * the shift starts and time the shift ends
 */
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String role;
    private LocalDateTime tFrom; // Time from
    private LocalDateTime tTo; // Time to

    private String tFromString; // Extracts value from form
    private String tToString; // Extracts value from form
    private String dateString; // Extracts value from form
    private String toStringTimeFromParsed; // Parses to display format
    private String toStringTimeToParsed; // Parses to display format


    @ManyToOne
    private Workstation workstation;

    public Employee() {

    }

    /***
     * Constructor
     *
     * @param name String
     * @param tFrom String
     * @param tTo String
     * @param role char
     */
    public Employee(String name, LocalDateTime tFrom, LocalDateTime tTo, String role) {
        this.name = name;
        this.tFrom = tFrom;
        this.tTo = tTo;
        this.role = role;
        parseToStringTimeFrom(tFrom);
        parseToStringTimeTo(tTo);
    }

    private void parseToStringTimeFrom(LocalDateTime tFrom) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        this.toStringTimeFromParsed = tFrom.format(formatter);
    }

    private void parseToStringTimeTo(LocalDateTime tTo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        this.toStringTimeToParsed = tTo.format(formatter);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime gettFrom() {
        return tFrom;
    }

    public int getDayOfMonth() {
        return tFrom.getDayOfMonth();
    }

    public int getMonth() {
        return tFrom.getMonthValue();
    }

    public int getHour() {
        return tFrom.getHour();
    }

    public int getHourTo() {
        return tTo.getHour();
    }

    public int getHourFrom() {
        return tFrom.getHour();
    }

    public void settFrom(LocalDateTime tFrom) {
        this.tFrom = tFrom;
        parseToStringTimeFrom(tFrom);
    }

    public LocalDateTime gettTo() {
        return tTo;
    }

    public void settTo(LocalDateTime tTo) {
        this.tTo = tTo;
        parseToStringTimeTo(tTo);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String gettFromString() {
        return tFromString;
    }

    public void settFromString(String tFromString) {
        this.tFromString = tFromString;
    }

    public String gettToString() {
        return tToString;
    }

    public void settToString(String tToString) {
        this.tToString = tToString;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getToStringTimeFromParsed() {
        return toStringTimeFromParsed;
    }

    public void setToStringTimeFromParsed(String toStringTimeFromParsed) {
        this.toStringTimeFromParsed = toStringTimeFromParsed;
    }

    public String getToStringTimeToParsed() {
        return toStringTimeToParsed;
    }

    public void setToStringTimeToParsed(String toStringTimeToParsed) {
        this.toStringTimeToParsed = toStringTimeToParsed;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Workstation getWorkstation() {
        return workstation;
    }

    public void setWorkstation(Workstation workstation) {
        this.workstation = workstation;
    }
}

