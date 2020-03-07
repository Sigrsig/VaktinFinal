package is.hi.hbv601.vaktin.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import is.hi.hbv601.vaktin.Entities.Workstation;

@Dao
public interface WorkstationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Workstation> workstations);

    @Query("SELECT * FROM Workstation where workstation_name like :name")
    public Workstation findWorkstationWithName(String name);


}
