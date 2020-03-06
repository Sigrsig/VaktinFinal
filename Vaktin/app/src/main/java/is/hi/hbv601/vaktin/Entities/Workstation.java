package is.hi.hbv601.vaktin.Entities;


import java.util.List;

@Entity
public class Workstation  {
    @PrimaryKey
    public long id;
    public String workstationName;

    public Workstation() { }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWorkstationName() {
        return workstationName;
    }

    public void setWorkstationName(String name) {
        this.workstationName = name;
    }

    // Hér þarf að gera One-to-Many relationship
    //https://developer.android.com/training/data-storage/room/relationships

    /*public List<Employee> getStaff() {
        return staff;
    }

    public void setStaff(List<Employee> staff) {
        this.staff = staff;
    }*/


}
