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
import android.widget.ImageView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity{

    Button mTilbaka_button;
    UserLocalStore mUserLocalStore;
    private Button mEmployee_button;
    private Button mComment_button;
    private Button mFooter_button;
    private Button mWorkstation_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        mWorkstation_button = (Button) findViewById(R.id.workstation_button);


        mWorkstation_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditActivity.this, "velkomin á Vaktina", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(EditActivity.this, WorkstationActivity.class);
                startActivity(i);

            }
        });

        mEmployee_button = (Button) findViewById(R.id.employee_button);
        mEmployee_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditActivity.this, EmployeeActivity.class);
                startActivity(i);
            }
        });

        mComment_button = (Button)findViewById(R.id.comment_button);
        mComment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditActivity.this, CommentActivity.class);
                startActivity(i);
            }
        });

        mFooter_button = (Button)findViewById(R.id.footer_button);
        mFooter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditActivity.this, FooterActivity.class);
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
            case R.id.workstations:
                Toast.makeText(this, "Vinnustöðvar valdar", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, WorkstationsFrontPageActivity.class));
                return true;
            case R.id.remove_employee_activity:
                Toast.makeText(this, "Eyða starfsmönnum valið", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, RemoveEmployeeActivity.class));
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