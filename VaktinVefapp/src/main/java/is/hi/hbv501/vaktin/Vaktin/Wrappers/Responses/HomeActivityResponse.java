package is.hi.hbv501.vaktin.Vaktin.Wrappers.Responses;

import is.hi.hbv501.vaktin.Vaktin.Entities.Comment;
import is.hi.hbv501.vaktin.Vaktin.Entities.Employee;
import is.hi.hbv501.vaktin.Vaktin.Entities.Footer;
import is.hi.hbv501.vaktin.Vaktin.Entities.Workstation;
import org.springframework.validation.ObjectError;

import java.time.LocalDate;
import java.util.List;

public class HomeActivityResponse extends GenericResponse {

    private List<Comment> comments;
    private List<Workstation> workstations;
    private List<Employee> employeesTomorrow;
    private List<Employee> employeesToday;
    private Footer footer;
    private LocalDate today;

    public HomeActivityResponse(String message, List<?> errors) {
        this(message, errors, null, null, null, null, null, null);
    }

    public HomeActivityResponse(List<Comment> comments, List<Workstation> workstations, List<Employee> employeesTomorrow, List<Employee> employeesToday, Footer footer, LocalDate today) {
        this.comments = comments;
        this.workstations = workstations;
        this.employeesTomorrow = employeesTomorrow;
        this.employeesToday = employeesToday;
        this.footer = footer;
        this.today = today;
    }

    public HomeActivityResponse(String message, List<?> errors, List<Comment> comments, List<Workstation> workstations, List<Employee> employeesTomorrow, List<Employee> employeesToday, Footer footer, LocalDate today) {
        super(message, errors);
        this.comments = comments;
        this.workstations = workstations;
        this.employeesTomorrow = employeesTomorrow;
        this.employeesToday = employeesToday;
        this.footer = footer;
        this.today = today;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Workstation> getWorkstations() {
        return workstations;
    }

    public void setWorkstations(List<Workstation> workstations) {
        this.workstations = workstations;
    }

    public List<Employee> getEmployeesTomorrow() {
        return employeesTomorrow;
    }

    public void setEmployeesTomorrow(List<Employee> employeesTomorrow) {
        this.employeesTomorrow = employeesTomorrow;
    }

    public List<Employee> getEmployeesToday() {
        return employeesToday;
    }

    public void setEmployeesToday(List<Employee> employeesToday) {
        this.employeesToday = employeesToday;
    }

    public Footer getFooter() {
        return footer;
    }

    public void setFooter(Footer footer) {
        this.footer = footer;
    }

    public LocalDate getToday() {
        return today;
    }

    public void setToday(LocalDate today) {
        this.today = today;
    }
}
