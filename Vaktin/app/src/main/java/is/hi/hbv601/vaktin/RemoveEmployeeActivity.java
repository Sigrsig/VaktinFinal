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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import is.hi.hbv601.vaktin.Database.AppDatabase;
import is.hi.hbv601.vaktin.Database.TokenDao;
import is.hi.hbv601.vaktin.Entities.Employee;
import is.hi.hbv601.vaktin.Entities.Token;
import is.hi.hbv601.vaktin.Entities.Workstation;

public class RemoveEmployeeActivity extends AppCompatActivity {

    private List<Employee> mEmployee;
    private Context mContext;

    private LinearLayout mLinearLayout;
    private final String buttonString = "Eyða";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_employee);

        final AppDatabase db = AppDatabase.getAppDatabase(this);
        mContext = this;
        mEmployee = db.employeeDao().loadAllEmployees();

        mLinearLayout = (LinearLayout)findViewById(R.id.linear_layout);

        for (final Employee e : mEmployee) {
            final TextView textViewName = new TextView(this);
            textViewName.setText(e.getName());
            mLinearLayout.addView(textViewName);
            final TextView textViewTimeFrom = new TextView(this);
            textViewTimeFrom.setText(e.gettFrom());
            mLinearLayout.addView(textViewTimeFrom);
            final TextView textViewTimeTo = new TextView(this);
            textViewTimeTo.setText(e.gettTo());
            mLinearLayout.addView(textViewTimeTo);

            final Button button = new Button(this);
            button.setText(buttonString);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getThisContext(), PopUpRemoveEmployeeActivity.class);
                    i.putExtra("employeeId", e.getEmployeeId());
                    startActivity(i);
                    // Vista breytingar í REST



                }
            });

            mLinearLayout.addView(button);

        }
    }

    /***
     * So onClickListener can find context of this activity
     * @return
     */
    public Context getThisContext() {
        return this.mContext;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_remove_employee_menu, menu);
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
            case R.id.workstations:
                Toast.makeText(this, "Vinnustöðvar valdar", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, WorkstationsFrontPageActivity.class));
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
