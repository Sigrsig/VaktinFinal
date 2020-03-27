package is.hi.hbv601.vaktin;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.IOException;

import is.hi.hbv601.vaktin.Database.AppDatabase;
import is.hi.hbv601.vaktin.Entities.Employee;
import is.hi.hbv601.vaktin.Entities.Workstation;
import is.hi.hbv601.vaktin.Entities.WorkstationWithEmployees;


public class PopupWsActivity extends AppCompatActivity {
    Button mDeleteButton;
    Button mNoButton;

    private final String url = "http://10.0.2.2:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_popup_workst);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.5));

        mDeleteButton = (Button) findViewById(R.id.delete_buttonWs);
        mNoButton = (Button) findViewById(R.id.no_buttonWs);

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppDatabase db = AppDatabase.getInstance();

                Intent i = getIntent();

                // Delete from REST
                try {
                    Workstation tmpWorkstation = db.workstationDao().findWorkstationWithId(i.getLongExtra("workstationId", -1));
                    new Api().deleteWorkstation(url + "delete", tmpWorkstation.getWorkstationName(), db.tokenDao().findById(1).getToken());
                }
                catch (IOException e) {
                    System.err.println(e.getMessage());
                    System.err.println(e.getStackTrace()[0].getLineNumber());
                }
                catch (JSONException e) {
                    System.err.println(e.getMessage());
                    System.err.println(e.getStackTrace()[0].getLineNumber());
                }

                WorkstationWithEmployees workstationWithEmployees = db.mWorkstationWithEmployeesDao().findWorkstationWithEmployeesById(i.getLongExtra("workstationId", -1));
                for (Employee e : workstationWithEmployees.getStaff()) {
                    e.setEmployeeWorkstationId(-1);
                    db.employeeDao().insertEmployee(e);
                }
                db.workstationDao().deleteWorkstationWithName(i.getLongExtra("workstationId", -1));



                Intent s = new Intent(PopupWsActivity.this, WorkstationsFrontPageActivity.class);
                startActivity(s);
            }
        });


        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PopupWsActivity.this, WorkstationsFrontPageActivity.class);
                startActivity(i);
            }
        });
    }
}
