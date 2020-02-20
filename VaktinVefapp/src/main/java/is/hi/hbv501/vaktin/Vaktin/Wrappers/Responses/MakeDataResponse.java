package is.hi.hbv501.vaktin.Vaktin.Wrappers.Responses;

import is.hi.hbv501.vaktin.Vaktin.Entities.Employee;
import is.hi.hbv501.vaktin.Vaktin.Entities.Workstation;

import java.util.List;

public class MakeDataResponse extends GenericResponse {

    List<Workstation> workstations;
    List<Employee> employees;

    public MakeDataResponse(List<Workstation> workstations, List<Employee> employees) {
        this.workstations = workstations;
        this.employees = employees;
    }

    public MakeDataResponse(String message, List<?> errors, List<Workstation> workstations, List<Employee> employees) {
        super(message, errors);
        this.workstations = workstations;
        this.employees = employees;
    }

    public List<Workstation> getWorkstations() {
        return workstations;
    }

    public void setWorkstations(List<Workstation> workstations) {
        this.workstations = workstations;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
