package is.hi.hbv601.vaktin.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import is.hi.hbv601.vaktin.Entities.Employee;
import is.hi.hbv601.vaktin.Entities.User;

@Dao
public interface EmployeeDao {

    @Query("DELETE FROM Employee")
    public void nukeTable();

    @Insert
    public void insertAll(List<Employee> employees);

    @Query("SELECT * FROM Employee")
    public List<Employee> loadAllEmployees();


}
