package is.hi.hbv601.vaktin.Database;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import is.hi.hbv601.vaktin.Entities.WorkstationWithEmployees;

@Dao
public interface WorkstationWithEmployeesDao {

    @Transaction
    @Query("SELECT * FROM Workstation WHERE workstationId = :id")
    public WorkstationWithEmployees findWorkstationWithEmployeesById(long id);
}
