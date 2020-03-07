package is.hi.hbv601.vaktin.Entities;


import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

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



}
