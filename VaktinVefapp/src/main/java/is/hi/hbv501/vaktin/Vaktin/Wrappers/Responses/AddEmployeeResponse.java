package is.hi.hbv501.vaktin.Vaktin.Wrappers.Responses;

import is.hi.hbv501.vaktin.Vaktin.Entities.Employee;

import java.util.List;

public class AddEmployeeResponse extends GenericResponse {

    Employee employee;

    public AddEmployeeResponse(Employee employee) {
        this.employee = employee;
    }

    public AddEmployeeResponse(String message, List<?> errors, Employee employee) {
        super(message, errors);
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
