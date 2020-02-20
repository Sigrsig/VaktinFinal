package is.hi.hbv501.vaktin.Vaktin.Repositories;

import is.hi.hbv501.vaktin.Vaktin.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/***
 * Repository for Employee
 *
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee save(Employee employee);
    void delete(Employee employee);
    List<Employee> findAll();
    Optional<Employee> findById(Long id);
    Employee findByName(String name);

}
