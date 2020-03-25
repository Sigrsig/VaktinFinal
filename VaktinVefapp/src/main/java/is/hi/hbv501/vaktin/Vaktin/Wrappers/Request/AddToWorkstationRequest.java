package is.hi.hbv501.vaktin.Vaktin.Wrappers.Request;

import is.hi.hbv501.vaktin.Vaktin.Entities.Employee;
import is.hi.hbv501.vaktin.Vaktin.Entities.Workstation;

import java.io.Serializable;

public class AddToWorkstationRequest implements Serializable {

    private String employeeName;
    private String workstationName;

    public AddToWorkstationRequest() { }

    public AddToWorkstationRequest(String employeeName, String workstationName) {
        this.employeeName = employeeName;
        this.workstationName = workstationName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getWorkstationName() {
        return workstationName;
    }

    public void setWorkstationName(String workstationName) {
        this.workstationName = workstationName;
    }
}
