package is.hi.hbv601.vaktin.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import is.hi.hbv601.vaktin.Entities.Workstation;

@Dao
public interface WorkstationDao {

    @Query("DELETE FROM Workstation")
    public void nukeTable();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Workstation> workstations);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertWorkstation(Workstation workstation);

    @Query("SELECT * FROM Workstation")
    public List<Workstation> findAllWorkstations();

    @Query("SELECT * FROM Workstation where workstation_name like :name")
    public Workstation findWorkstationWithName(String name);

    @Query("SELECT * FROM Workstation where workstationId = :id")
    public Workstation findWorkstationWithId(long id);




}
