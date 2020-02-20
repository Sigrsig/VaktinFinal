package is.hi.hbv501.vaktin.Vaktin.Services;

import is.hi.hbv501.vaktin.Vaktin.Entities.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/***
 * Might not need this Service
 */
public interface EmployeeService {

    Employee save(Employee employee);
    void delete(Employee employee);
    List<Employee> findAll();
    Optional<Employee> findById(Long id);
    Employee findByName(String name);

    boolean validateTimeFrom(String tFrom);
    boolean validateTimeTo(String tTo);
    boolean validateName(String name);
    boolean validateDate(String date);

    void parseToLocalDateTime(Employee employee);
    void parseToLocalDateTimeWithDate(Employee employee);

    ArrayList<Employee> findAllSortedTomorrow();
    ArrayList<Employee> findAllSortedToday();
    ArrayList<Employee> findAllTodayAndTomorrow();

}
