package is.hi.hbv501.vaktin.Vaktin.Wrappers.Request;

import java.io.Serializable;

public class DeleteWorkstationRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    String workstationName;

    public DeleteWorkstationRequest() { }

    public DeleteWorkstationRequest(String workstationName) {
        this.workstationName = workstationName;
    }

    public String getWorkstationName() {
        return workstationName;
    }

    public void setWorkstationName(String workstationName) {
        this.workstationName = workstationName;
    }
}
