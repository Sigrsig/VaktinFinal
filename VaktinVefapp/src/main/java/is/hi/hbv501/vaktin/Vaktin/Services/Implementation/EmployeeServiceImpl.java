package is.hi.hbv501.vaktin.Vaktin.Services.Implementation;

import is.hi.hbv501.vaktin.Vaktin.Entities.Employee;
import is.hi.hbv501.vaktin.Vaktin.Repositories.EmployeeRepository;
import is.hi.hbv501.vaktin.Vaktin.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * Service implementation for Employee
 *
 * Has a private class TimeSorter that implements Comparator
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;


    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteById(long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee findByNameAndTFromAndTTo(String name, LocalDateTime tFrom, LocalDateTime tTo) {
        return employeeRepository.findByNameAndTFromAndTTo(name, tFrom, tTo);
    }

    /***
     * TimeSorter class for sorting employees by hour arrived
     */
    private class TimeSorter implements Comparator<Employee> {

        @Override
        public int compare(Employee a, Employee b) {
            return a.gettFrom().compareTo(b.gettFrom());
        }
    }

    /***
     * Method findAllSortedToday
     * Filters out employees who are working this calendar day
     * @return ArrayList<Employee>
     */
    public ArrayList<Employee> findAllSortedToday() {
        ArrayList<Employee> tempEmployees = (ArrayList)findAll();
        LocalDate date = LocalDate.now();
        ArrayList<Employee> resultList = new ArrayList<>();
        for (Employee emp : tempEmployees) {
            LocalDate empDate = emp.gettFrom().toLocalDate();
            if (empDate.equals(date)) {
                resultList.add(emp);
            }
        }
        return resultList;
    }

    /***
     * Method findAllSortedTomorrow
     * Filters out employees who are working tomorrow
     * @return ArrayList<Employee>
     */
    public ArrayList<Employee> findAllSortedTomorrow() {
        ArrayList<Employee> tempEmployees = (ArrayList)findAll();
        LocalDate date = LocalDate.now();
        ArrayList<Employee> resultList = new ArrayList<>();
        for (Employee emp : tempEmployees) {
            LocalDate empDate = emp.gettFrom().toLocalDate();
            if (empDate.equals(date.plusDays(1))) {
                resultList.add(emp);
            }
        }
        resultList.sort(new TimeSorter());
        return resultList;
    }

    /***
     * Method findAllSortedToday
     * Filters out employees who are working today and tomorrow
     * @return ArrayList<Employee>
     */
    public ArrayList<Employee> findAllTodayAndTomorrow() {
        ArrayList<Employee> tempEmployees = (ArrayList)employeeRepository.findAll();
        ArrayList<Employee> resultList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        for (Employee emp : tempEmployees) {
            LocalDate empDate = emp.gettFrom().toLocalDate();
            if (empDate.equals(today) && emp.gettFrom().getHour() > 3) {
                resultList.add(emp);
            }
            else if (empDate.equals(tomorrow) && emp.gettFrom().getHour() <= 3 && emp.gettTo().getHour() <= 8) {
                resultList.add(emp);
            }
        }
        return resultList;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee findByName(String name) {
        return employeeRepository.findByName(name);
    }

    @Override
    public boolean validateTimeFrom(String tFrom) {
        Pattern p = Pattern.compile("^([0-9]{1}|[0-9]{2}):[0-9]{2}$");
        Matcher matchTimeFrom = p.matcher(tFrom);
        boolean boolTimeFrom = matchTimeFrom.matches();

        return boolTimeFrom;
    }

    @Override
    public boolean validateTimeTo(String tTo) {
        Pattern p = Pattern.compile("^([0-9]{1}|[0-9]{2}):[0-9]{2}$");
        Matcher matchTimeTo = p.matcher(tTo);
        boolean boolTimeTo = matchTimeTo.matches();

        return boolTimeTo;
    }

    @Override
    public boolean validateName(String name) {
        return name.length() > 0;
    }

    @Override
    public boolean validateDate(String date) {
        Pattern p = Pattern.compile("^([0-9]{1}|[0-9]{2})/([0-9]{2}|[0-9]{1})$");
        Matcher matchTimeTo = p.matcher(date);
        boolean boolDate = matchTimeTo.matches();

        return boolDate;
    }



    @Override
    public void parseToLocalDateTime(Employee employee) {
        String timeFrom = employee.gettFromString();
        /***
         * Ef Mætir hefur lengd 5
         */
        if (timeFrom.length() == 5) {
            int hourFrom = Integer.parseInt(timeFrom.substring(0, 2));
            /***
             * Ef klukkutími er 24 eða 00
             */
            if (hourFrom == 24 || hourFrom == 00) {
                String minutes = employee.gettFromString().substring(3);
                int intMinutes = Integer.parseInt(minutes);
                employee.settFrom(LocalDateTime.now().withHour(0).withMinute(intMinutes).plusDays(1));
            }
            /***
             * Setja klukkutíma í intHour og mínútur í intMintues
             */
            else {
                String hour = employee.gettFromString().substring(0, 2);
                String minutes = employee.gettFromString().substring(3);
                int intHour = Integer.parseInt(hour);
                int intMinutes = Integer.parseInt(minutes);
                employee.settFrom(LocalDateTime.now().withHour(intHour).withMinute(intMinutes));
            }
        /***
         * Ef lengd á Mættur er 4 stafir
         */
        } else if (timeFrom.length() == 4) {
            int hourFrom = Integer.parseInt(timeFrom.substring(0, 1));
            /***
             * Ef klukkutími er 0
             */
            if (hourFrom == 0) {
                String minutes = employee.gettFromString().substring(2);
                int intMinutes = Integer.parseInt(minutes);
                employee.settFrom(LocalDateTime.now().withHour(0).withMinute(intMinutes).plusDays(1));
            }
            /***
             * Setja klukkutíma í intHour og mínútur í intMinutes
             */
            else {
                String hour = employee.gettFromString().substring(0, 1);
                String minutes = employee.gettFromString().substring(2);
                int intHour = Integer.parseInt(hour);
                int intMinutes = Integer.parseInt(minutes);
                employee.settFrom(LocalDateTime.now().withHour(intHour).withMinute(intMinutes));
            }
        }


        String timeTo = employee.gettToString();
        /***
         * Ef lengd á Klárar er 5
         */
        if (timeTo.length() == 5) {
            int hourTo = Integer.parseInt(timeTo.substring(0, 2));
            /***
             * Ef klukkutími í Klárar er 24 eða 00
             */
            if (hourTo == 24 || hourTo == 00) {
                String minutes = employee.gettToString().substring(3);
                int intMinutes = Integer.parseInt(minutes);
                employee.settTo(LocalDateTime.now().withHour(0).withMinute(intMinutes).plusDays(1));
            }
            /***
             * Setja klukkutíma í Klárar í intHour og mínútur í intMinutes
             */
            else {
                String hour = employee.gettToString().substring(0, 2);
                String minutes = employee.gettToString().substring(3);
                int intHour = Integer.parseInt(hour);
                int intMinutes = Integer.parseInt(minutes);
                int dayOfWeek = LocalDateTime.now().getDayOfWeek().getValue();
                System.out.println("dayOfWeek " + dayOfWeek);
                System.out.println("dayOfWeek != 7 && dayOfWeek < employee.gettFrom().getDayOfWeek().getValue() " + (dayOfWeek != 7 && dayOfWeek < employee.gettFrom().getDayOfWeek().getValue()));
                System.out.println("dayOfWeek == 7 && employee.gettFrom().getDayOfWeek().getValue() == 1 " + (dayOfWeek == 7 && employee.gettFrom().getDayOfWeek().getValue() == 1));
                checkDayOfWeek(employee, intHour, intMinutes, dayOfWeek);
            }
            /***
             *   Ef lengd á Klárar er 4 stafir
             */
        } else if (timeTo.length() == 4) {
            int hourTo = Integer.parseInt(timeTo.substring(0,1));
            /***
             * Ef klukkutími í Klárar er 0
             */
            if (hourTo == 0) {
                String minutes = employee.gettToString().substring(2);
                int intMinutes = Integer.parseInt(minutes);
                employee.settTo(LocalDateTime.now().withHour(0).withMinute(intMinutes));
            }
            /***
             * Setja klukkutíma í Klárar í intHour og mínútur i intMinutes
             */
            else {
                String hour = employee.gettToString().substring(0, 1);
                String minutes = employee.gettToString().substring(2);
                int intHour = Integer.parseInt(hour);
                int intMinutes = Integer.parseInt(minutes);
                int dayOfWeek = LocalDateTime.now().getDayOfWeek().getValue();
                checkDayOfWeek(employee, intHour, intMinutes, dayOfWeek);

            }
        }

        save(employee);
    }

    @Override
    public void parseToLocalDateTimeWithDate(Employee employee) {
        String date = employee.getDateString();

        /***
         * If date has form 1/1 or 1/11
         */
        int dayOfMonth = -1;
        int month = -1;
        if (date.indexOf("/") == 1) {
            dayOfMonth = Integer.parseInt(date.substring(0,1));
            month = Integer.parseInt(date.substring(2));

        }
        else {
            dayOfMonth = Integer.parseInt(date.substring(0,2));
            month = Integer.parseInt(date.substring(3));
        }


        String timeFrom = employee.gettFromString();
        /***
         * Ef Mætir hefur lengd 5
         */
        if (timeFrom.length() == 5) {
            int hourFrom = Integer.parseInt(timeFrom.substring(0, 2));
            /***
             * Setja klukkutíma í intHour og mínútur í intMintues
             */

            String hour = employee.gettFromString().substring(0, 2);
            String minutes = employee.gettFromString().substring(3);
            int intHour = Integer.parseInt(hour);
            int intMinutes = Integer.parseInt(minutes);
            employee.settFrom(LocalDateTime.now().withDayOfMonth(dayOfMonth).withMonth(month).withMinute(intMinutes).withHour(intHour));


        /***
         * Ef lengd á Mættur er 4 stafir
         */
        } else if (timeFrom.length() == 4) {
            int hourFrom = Integer.parseInt(timeFrom.substring(0, 1));

            /***
             * Setja klukkutíma í intHour og mínútur í intMinutes
             */

            String hour = employee.gettFromString().substring(0, 1);
            String minutes = employee.gettFromString().substring(2);
            int intHour = Integer.parseInt(hour);
            int intMinutes = Integer.parseInt(minutes);
            employee.settFrom(LocalDateTime.now().withDayOfMonth(dayOfMonth).withMonth(month).withMinute(intMinutes).withHour(intHour));

        }


        String timeTo = employee.gettToString();
        /***
         * Ef lengd á Klárar er 5
         */
        if (timeTo.length() == 5) {
            int hourTo = Integer.parseInt(timeTo.substring(0, 2));

            /***
             * Setja klukkutíma í Klárar í intHour og mínútur í intMinutes
             */

            String hour = employee.gettToString().substring(0, 2);
            String minutes = employee.gettToString().substring(3);
            int intHour = Integer.parseInt(hour);
            int intMinutes = Integer.parseInt(minutes);
            int dayOfWeek = LocalDateTime.now().getDayOfWeek().getValue();
            employee.settTo(LocalDateTime.now().withDayOfMonth(dayOfMonth).withMonth(month).withMinute(intMinutes).withHour(intHour));

            /***
             *   Ef lengd á Klárar er 4 stafir
             */
        } else if (timeTo.length() == 4) {
            int hourTo = Integer.parseInt(timeTo.substring(0,1));
            /***
             * Setja klukkutíma í Klárar í intHour og mínútur i intMinutes
             */

            String hour = employee.gettToString().substring(0, 1);
            String minutes = employee.gettToString().substring(2);
            int intHour = Integer.parseInt(hour);
            int intMinutes = Integer.parseInt(minutes);
            int dayOfWeek = LocalDateTime.now().getDayOfWeek().getValue();
            employee.settTo(LocalDateTime.now().withDayOfMonth(dayOfMonth).withMonth(month).withMinute(intMinutes).withHour(intHour));

        }

        save(employee);

    }

    /***
     * Checks if employee is working the following day or not
     * @param employee
     * @param intHour
     * @param intMinutes
     * @param dayOfWeek
     */
    private void checkDayOfWeek(Employee employee, int intHour, int intMinutes, int dayOfWeek) {
        if ( (dayOfWeek != 7 && dayOfWeek < employee.gettFrom().getDayOfWeek().getValue()) || (dayOfWeek == 7 && employee.gettFrom().getDayOfWeek().getValue() == 1)) {
            employee.settTo(LocalDateTime.now().withHour(intHour).withMinute(intMinutes).plusDays(1));
        }
        else {
            employee.settTo(LocalDateTime.now().withHour(intHour).withMinute(intMinutes));
        }
    }

}

//   (LocalDateTime.now().getDayOfWeek().getValue() != 7 && LocalDateTime.now().getDayOfWeek().getValue() < employee.gettFrom().getDayOfWeek().getValue()) || (LocalDateTime.now().getDayOfWeek().getValue() == 7 && employee.gettFrom().getDayOfWeek().getValue() == 1)
