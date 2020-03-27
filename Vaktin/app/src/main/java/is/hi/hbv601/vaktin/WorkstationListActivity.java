package is.hi.hbv601.vaktin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import is.hi.hbv601.vaktin.Database.AppDatabase;
import is.hi.hbv601.vaktin.Database.EmployeeDao;
import is.hi.hbv601.vaktin.Database.TokenDao;
import is.hi.hbv601.vaktin.Entities.Employee;
import is.hi.hbv601.vaktin.Entities.Token;
import is.hi.hbv601.vaktin.Entities.Workstation;
import is.hi.hbv601.vaktin.Entities.WorkstationWithEmployees;
import is.hi.hbv601.vaktin.Utilities.LocalDateTimeConverter;
import is.hi.hbv601.vaktin.Utilities.TimeSorter;

public class WorkstationListActivity extends AppCompatActivity {

    private TextView mTextView;
    private AppDatabase db;
    private long id;
    private ArrayList<Employee> employees;
    private LinearLayout mLinearLayout;
    private String buttonString = "Bæta við";
    private Button mDeleteWs;

    private final String url = "http://10.0.2.2:8080/";

    private ArrayList<Employee> findAllSorted() {
        ArrayList<Employee> resultList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        for (Employee emp : employees) {

            LocalDate empDate = LocalDateTimeConverter.toDate(emp.gettFrom()).toLocalDate();
            if (empDate.equals(today)) {
                resultList.add(emp);
            }
            else if (empDate.equals(tomorrow) && LocalDateTimeConverter.toDate(emp.gettFrom()).getHour() <= 3) {
                resultList.add(emp);
            }
        }
        resultList.sort(new TimeSorter());
        return resultList;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workstationlist);

        mTextView = (TextView)findViewById(R.id.textView);
        mLinearLayout = (LinearLayout)findViewById(R.id.linear_layout);
        mDeleteWs = (Button)findViewById(R.id.Delete_ws);

        // Sækja rétt id
        // Þarf að grípa ef -1?
        Intent i = getIntent();
        id = i.getLongExtra("workstationId", -1);

        // Tenging við Room og finna rétta vinnustöð, sækja starfsmenn sem
        // ekki er búið að setja á vinnustöð
        db = AppDatabase.getAppDatabase(this);
        final Workstation workstation = db.workstationDao().findWorkstationWithId(id);
        employees = (ArrayList)db.employeeDao().loadAllEmployeesWithNoWorkstation();


        mTextView.setText(workstation.getWorkstationName());

        mDeleteWs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WorkstationListActivity.this, PopupWsActivity.class);
                i.putExtra("workstationId", id);
                startActivity(i);

            }
        });

        /***
         *  Get ekki skipt layout 50/50
         */
        ArrayList<Employee> unassignedEmployees = findAllSorted();
        for (final Employee e : unassignedEmployees) {
            TextView textViewName = new TextView(this);
            textViewName.setText(e.getName());
            mLinearLayout.addView(textViewName);
            TextView textViewTimeFrom = new TextView(this);
            textViewTimeFrom.setText(e.gettFrom());
            mLinearLayout.addView(textViewTimeFrom);
            TextView textViewTimeTo = new TextView(this);
            textViewTimeTo.setText(e.gettTo());
            mLinearLayout.addView(textViewTimeTo);

            Button button = new Button(this);
            button.setText(buttonString);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Vista breytingar í REST
                    Workstation workstation = db.workstationDao().findWorkstationWithId(id);
                    new Api().addEmployeeToWorkstation(url + "addtoworkstation", e, workstation, db.tokenDao().findById(1).getToken());

                    e.setEmployeeWorkstationId(id);
                    db.employeeDao().insertEmployee(e);
                    Intent i = new Intent(WorkstationListActivity.this, WorkstationListActivity.class);
                    i.putExtra("workstationId", id);
                    startActivity(i);

                }
            });

            mLinearLayout.addView(button);

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.workstationsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Toast.makeText(this, "Edit Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, EditActivity.class));
                return true;
            case R.id.main:
                Toast.makeText(this, "Upphafssíða valin", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.logout:
                Toast.makeText(this, "Log Out Selected", Toast.LENGTH_SHORT).show();


                // Eyða token
                AppDatabase db = AppDatabase.getInstance();
                TokenDao td = db.tokenDao();
                Token tmpToken = td.findById(1);
                tmpToken.setToken(null);
                td.insertToken(tmpToken);

                /***
                 * Eyða Room. Finnum betri lausn síðar
                 */
                td.nukeTable();
                db.employeeDao().nukeTable();
                db.workstationDao().nukeTable();
                db.userDao().nukeTable();
                db.commentDao().nukeTable();
                db.footerDao().nukeTable();

                startActivity(new Intent(this, LoginActivity.class));

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }


}
