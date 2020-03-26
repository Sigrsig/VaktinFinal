package is.hi.hbv601.vaktin.Database;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import is.hi.hbv601.vaktin.Entities.WorkstationWithEmployees;

/***
 * One-to-many relationship between Workstation and Employee
 * Service for querying employees of workstation
 */
@Dao
public interface WorkstationWithEmployeesDao {

    @Transaction
    @Query("SELECT * FROM Workstation WHERE workstationId = :id")
    public WorkstationWithEmployees findWorkstationWithEmployeesById(long id);
}
