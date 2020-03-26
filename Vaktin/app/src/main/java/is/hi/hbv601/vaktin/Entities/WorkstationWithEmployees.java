package is.hi.hbv601.vaktin.Entities;


import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/***
 * Entity for WorkstationWithEmployee
 * Relation on workstationId and employeeWorkstationId
 */
public class WorkstationWithEmployees {

    @Embedded public Workstation workstation;

    @Relation(
            parentColumn = "workstationId",
            entityColumn = "employeeWorkstationId"
    )
    private List<Employee> staff;

    // Tómur smiður
    public WorkstationWithEmployees() {
    }

    public Workstation getWorkstation() {
        return workstation;
    }

    public void setWorkstation(Workstation workstation) {
        this.workstation = workstation;
    }

    public List<Employee> getStaff() {
        return staff;
    }

    public void setStaff(List<Employee> staff) {
        this.staff = staff;
    }
}
