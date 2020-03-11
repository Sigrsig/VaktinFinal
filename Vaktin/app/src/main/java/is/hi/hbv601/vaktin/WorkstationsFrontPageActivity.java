package is.hi.hbv601.vaktin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import is.hi.hbv601.vaktin.Adapters.WorkstationListAdapter;
import is.hi.hbv601.vaktin.Database.AppDatabase;
import is.hi.hbv601.vaktin.Database.TokenDao;
import is.hi.hbv601.vaktin.Entities.Token;
import is.hi.hbv601.vaktin.Entities.Workstation;
import is.hi.hbv601.vaktin.R;

public class WorkstationsFrontPageActivity extends AppCompatActivity {

    private UserLocalStore mUserLocalStore;
    private AppDatabase db;
    private ListView mListView;
    private WorkstationsFrontPageActivity thisActivity;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workstations);

        thisActivity = this;
        mUserLocalStore = new UserLocalStore(this);

        // Tenging við Room og sækja vinnustöðvar
        db = AppDatabase.getAppDatabase(this);
        ArrayList<Workstation> workstations = (ArrayList)db.workstationDao().findAllWorkstations();

        ListView mListView = (ListView)findViewById(R.id.list_view);

        WorkstationListAdapter adapter = new WorkstationListAdapter(this, R.layout.adapter_view_layout_workstations, workstations);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("id " + id + "\nposition " + position);
                Workstation workstation =(Workstation) parent.getItemAtPosition(position);
                System.out.println("Value is "+workstation.getWorkstationName());

                Intent i = new Intent(thisActivity, WorkstationListActivity.class);
                i.putExtra("workstationId", workstation.getWorkstationId());
                startActivity(i);
            }
        });

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
                mUserLocalStore.clearedUserData();
                mUserLocalStore.setUserLoggedIn(false);

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
