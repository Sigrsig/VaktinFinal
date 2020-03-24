package is.hi.hbv501.vaktin.Vaktin.Wrappers.Request;

public class DeleteWorkstationRequest {

    String workstationName;

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
