package is.hi.hbv501.vaktin.Vaktin.Wrappers.Responses;

import is.hi.hbv501.vaktin.Vaktin.Entities.Workstation;

import java.util.List;

public class AddWorkstationResponse extends GenericResponse {

    Workstation workstation;

    public AddWorkstationResponse(Workstation workstation) {
        this.workstation = workstation;
    }

    public AddWorkstationResponse(String message, List<?> errors, Workstation workstation) {
        super(message, errors);
        this.workstation = workstation;
    }

    public Workstation getWorkstation() {
        return workstation;
    }

    public void setWorkstation(Workstation workstation) {
        this.workstation = workstation;
    }
}
