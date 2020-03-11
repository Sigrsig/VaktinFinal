package is.hi.hbv601.vaktin.Utilities;

import java.util.Comparator;

import is.hi.hbv601.vaktin.Entities.Employee;

/***
 * TimeSorter class for sorting employees by hour arrived
 */
public class TimeSorter implements Comparator<Employee> {

    @Override
    public int compare(Employee a, Employee b) {
        return a.gettFrom().compareTo(b.gettFrom());
    }
}