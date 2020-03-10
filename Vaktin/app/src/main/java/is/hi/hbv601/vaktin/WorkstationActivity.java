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
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import is.hi.hbv601.vaktin.Database.AppDatabase;
import is.hi.hbv601.vaktin.Database.WorkstationDao;
import is.hi.hbv601.vaktin.Entities.Workstation;
import okhttp3.Response;

public class WorkstationActivity extends AppCompatActivity {

    UserLocalStore mUserLocalStore;
    Button mSave_button;
    private EditText mEditText;
    private String url = "http://10.0.2.2:8080/addworkstation";

    AppDatabase mAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workstation);
        mSave_button = (Button) findViewById(R.id.vista_button);
        mEditText = findViewById(R.id.workstation_name);

        /***
         * Workstation Dao til að vista nýtt Workstation
         */
        mAppDatabase = AppDatabase.getAppDatabase(this);
        final WorkstationDao wd = mAppDatabase.workstationDao();

        mSave_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(mEditText);
                String workstationName = mEditText.getText().toString();
                //String workstationName = "draslllll";
                // Gá hvort vinnustöð er þegar til
                if (wd.findWorkstationWithName(workstationName) == null) {
                    Workstation tmpWorkstation = new Workstation(mEditText.getText().toString());
                    wd.insertWorkstation(tmpWorkstation);

                    /***
                     * Bæta workstation við ytri gagnagrunn
                     */
                    String result = null;
                    try {
                        result = new Api().postNewWorkstation(url, workstationName, mAppDatabase.tokenDao().findById(1).getToken());
                    }
                    catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                    catch (JSONException e) {
                        System.err.println(e.getMessage());
                    }

                    /***
                     * Held þetta sé óþarfa vesen
                     * Þurfum kannski ekki að prenta neitt
                     */
                    /*
                    String t = null;
                    try {
                        JSONObject jsonBody = new JSONObject(result);
                        t = jsonBody.getString("workstation").toString();
                    }
                    catch (JSONException e) {
                        System.err.println(e.getMessage());
                    }
                    */

                    Toast.makeText(WorkstationActivity.this, "Ný vinnustöð búin til", Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(WorkstationActivity.this, EditActivity.class);
                startActivity(i);

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
            case R.id.logout:
                Toast.makeText(this, "Log Out Selected", Toast.LENGTH_SHORT).show();
                mUserLocalStore.clearedUserData();
                mUserLocalStore.setUserLoggedIn(false);
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            case R.id.login_Button:
                Toast.makeText(this, "Log In Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
