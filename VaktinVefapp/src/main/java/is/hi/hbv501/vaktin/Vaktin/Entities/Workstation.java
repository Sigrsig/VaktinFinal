package is.hi.hbv501.vaktin.Vaktin.Entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/***
 * Entity for Workstation
 * Creates an instance for every new Workstation
 * We might want to add a variable for capacity if we want to
 * limit the amount of people at each workstation?
 */
@Entity
@Table(name = "workstation")
public class Workstation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String workstationName;

    //private LocalDate date; // Might not use


    @OneToMany(mappedBy = "workstation")
    private List<Employee> staff = new ArrayList<>();

    public Workstation() { }

    /***
     * Constructor for Workstation
     * Parameter name is the only parameter in the constructor. List of staff
     * has 'one-to-many' relation to the Employee entity
     * @param name
     */
    public Workstation(String name) {
        this.workstationName = name;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWorkstationName() {
        return workstationName;
    }

    public void setWorkstationName(String name) {
        this.workstationName = name;
    }




    public List<Employee> getStaff() {
        return staff;
    }

    public void setStaff(List<Employee> staff) {
        this.staff = staff;
    }
}
