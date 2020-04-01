package is.hi.hbv601.vaktin.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import is.hi.hbv601.vaktin.Entities.Employee;
import is.hi.hbv601.vaktin.Entities.User;

/***
 *  Service for Employee entity
 */
@Dao
public interface EmployeeDao {

    @Query("DELETE FROM Employee")
    public void nukeTable();

    @Delete
    public void deleteEmployee(Employee employee);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Employee> employees);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertEmployee(Employee employee);

    @Query("SELECT * FROM Employee")
    public List<Employee> loadAllEmployees();

    @Query("SELECT * FROM Employee WHERE employeeWorkstationId = -1")
    public List<Employee> loadAllEmployeesWithNoWorkstation();

    @Query("SELECT * FROM Employee WHERE name = :name")
    public Employee findByName(String name);

    @Query("SELECT * FROM Employee WHERE employeeId = :id")
    public Employee findById(long id);


}
