package is.hi.hbv601.vaktin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import is.hi.hbv601.vaktin.Database.AppDatabase;
import is.hi.hbv601.vaktin.Entities.Employee;
import is.hi.hbv601.vaktin.Utilities.LocalDateTimeConverter;

public class EmployeeActivity extends AppCompatActivity {

    UserLocalStore mUserLocalStore;
    Button mSave_button;
    EditText mName;
    EditText mTo;
    EditText mFrom;
    EditText mType;
    private AppDatabase db;

    private final String url = "http://10.0.2.2:8080/addemployee";

    private void saveEmployee(Employee employee) {
        db.employeeDao().insertEmployee(employee);


            new Api().addEmployee(url, employee, db.tokenDao().findById(1).getToken());



    }

    public void createEmployee(String name, String role, String timeFrom, String timeTo) {

        /***
         * Ef Mætir hefur lengd 5
         */
        LocalDateTime timeFromLocalDate = null;
        if (timeFrom.length() == 5) {
            int hourFrom = Integer.parseInt(timeFrom.substring(0, 2));
            /***
             * Setja klukkutíma í intHour og mínútur í intMintues
             */

            String hour = timeFrom.substring(0, 2);
            String minutes = timeFrom.substring(3);
            int intHour = Integer.parseInt(hour);
            int intMinutes = Integer.parseInt(minutes);
            timeFromLocalDate = (LocalDateTime.now().withMinute(intMinutes).withHour(intHour));


            /***
             * Ef lengd á Mættur er 4 stafir
             */
        } else if (timeFrom.length() == 4) {
            int hourFrom = Integer.parseInt(timeFrom.substring(0, 1));

            /***
             * Setja klukkutíma í intHour og mínútur í intMinutes
             */

            String hour = timeFrom.substring(0, 1);
            String minutes = timeFrom.substring(2);
            int intHour = Integer.parseInt(hour);
            int intMinutes = Integer.parseInt(minutes);
            timeFromLocalDate = (LocalDateTime.now().withMinute(intMinutes).withHour(intHour));

        }

        /***
         * Ef lengd á Klárar er 5
         */
        LocalDateTime timeToLocalDate = null;
        if (timeTo.length() == 5) {
            int hourTo = Integer.parseInt(timeTo.substring(0, 2));

            /***
             * Setja klukkutíma í Klárar í intHour og mínútur í intMinutes
             */

            String hour = timeTo.substring(0, 2);
            String minutes = timeTo.substring(3);
            int intHour = Integer.parseInt(hour);
            int intMinutes = Integer.parseInt(minutes);
            int dayOfWeek = LocalDateTime.now().getDayOfWeek().getValue();
            timeToLocalDate = (LocalDateTime.now().withMinute(intMinutes).withHour(intHour));

            /***
             *   Ef lengd á Klárar er 4 stafir
             */
        } else if (timeTo.length() == 4) {
            int hourTo = Integer.parseInt(timeTo.substring(0,1));
            /***
             * Setja klukkutíma í Klárar í intHour og mínútur i intMinutes
             */

            String hour = timeTo.substring(0, 1);
            String minutes = timeTo.substring(2);
            int intHour = Integer.parseInt(hour);
            int intMinutes = Integer.parseInt(minutes);
            int dayOfWeek = LocalDateTime.now().getDayOfWeek().getValue();
            timeToLocalDate = (LocalDateTime.now().withMinute(intMinutes).withHour(intHour));

        }

        Employee employee = new Employee(name,role, LocalDateTimeConverter.toDateString(timeFromLocalDate),LocalDateTimeConverter.toDateString(timeToLocalDate));
        saveEmployee(employee);

    }

    public boolean validateTimeFrom(String tFrom) {
        Pattern p = Pattern.compile("^([0-9]{1}|[0-9]{2}):[0-9]{2}$");
        Matcher matchTimeFrom = p.matcher(tFrom);
        boolean boolTimeFrom = matchTimeFrom.matches();

        return boolTimeFrom;
    }

    public boolean validateTimeTo(String tTo) {
        Pattern p = Pattern.compile("^([0-9]{1}|[0-9]{2}):[0-9]{2}$");
        Matcher matchTimeTo = p.matcher(tTo);
        boolean boolTimeTo = matchTimeTo.matches();

        return boolTimeTo;
    }

    public boolean validateName(String name) {
        return name.length() > 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        mName = (EditText) findViewById(R.id.eName);
        mType = (EditText) findViewById(R.id.eType);
        mTo = (EditText) findViewById(R.id.eTo);
        mFrom = (EditText) findViewById(R.id.eFrom);

        db = AppDatabase.getAppDatabase(this);

        mSave_button = (Button) findViewById(R.id.vista_button);

        mSave_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString();
                String role = mType.getText().toString();
                String timeFrom = mFrom.getText().toString();
                String timeTo = mTo.getText().toString();

                if (validateName(name) && validateTimeFrom(timeFrom) && validateTimeTo(timeTo)) {
                    createEmployee(name, role, timeFrom, timeTo);
                    //Employee tmpEmployee = new Employee(name, role, timeFrom, timeTo);
                    //saveEmployee(tmpEmployee);

                    Toast.makeText(EmployeeActivity.this, "Ný starfsmaður vistaður", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(EmployeeActivity.this, EditActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(EmployeeActivity.this, "Rangt valið", Toast.LENGTH_SHORT).show();
                }


            }
        });

        mUserLocalStore = new UserLocalStore(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.main:
                Toast.makeText(this, "Main page Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.workstations:
                Toast.makeText(this, "Vinnustöðvar valdar", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, WorkstationsFrontPageActivity.class));
                return true;
            case R.id.logout:
                Toast.makeText(this, "Log Out Selected", Toast.LENGTH_SHORT).show();
                mUserLocalStore.clearedUserData();
                mUserLocalStore.setUserLoggedIn(false);
                startActivity(new Intent(this, LoginActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
