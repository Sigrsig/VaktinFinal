package is.hi.hbv501.vaktin.Vaktin.Wrappers.Responses;

import is.hi.hbv501.vaktin.Vaktin.Entities.Employee;
import is.hi.hbv501.vaktin.Vaktin.Entities.Footer;
import is.hi.hbv501.vaktin.Vaktin.Entities.Workstation;

import java.util.List;

public class WorkstationActivityResponse extends GenericResponse {

    List<Workstation> workstations;
    List<Employee> employees;
    Footer footer;

    public WorkstationActivityResponse(List<Workstation> workstations, List<Employee> employees, Footer footer) {
        this.workstations = workstations;
        this.employees = employees;
        this.footer = footer;
    }

    public WorkstationActivityResponse(String message, List<?> errors, List<Workstation> workstations, List<Employee> employees, Footer footer) {
        super(message, errors);
        this.workstations = workstations;
        this.employees = employees;
        this.footer = footer;
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

    public Footer getFooter() {
        return footer;
    }

    public void setFooter(Footer footer) {
        this.footer = footer;
    }
}
