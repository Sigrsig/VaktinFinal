package is.hi.hbv601.vaktin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

import is.hi.hbv601.vaktin.Database.AppDatabase;
import is.hi.hbv601.vaktin.Database.CommentDao;
import is.hi.hbv601.vaktin.Database.FooterDao;
import is.hi.hbv601.vaktin.Entities.Footer;

public class FooterActivity extends AppCompatActivity {

    Button mVista_button;
    UserLocalStore mUserLocalStore;
    EditText mType1;
    EditText mType2;
    EditText mName1;
    EditText mName2;
    EditText mPhoneNr1;
    EditText mPhoneNr2;
    AppDatabase mAppDatabase;
    private String url = "http://10.0.2.2:8080/setfooter";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footer);
        mVista_button = (Button) findViewById(R.id.vista_button);

        mName1 = (EditText) findViewById(R.id.fName1);
        mName2 = (EditText) findViewById(R.id.fName2);
        mPhoneNr1 = (EditText) findViewById(R.id.fPhonenr1);
        mPhoneNr2 = (EditText) findViewById(R.id.fPhonenr2);

        mAppDatabase = AppDatabase.getAppDatabase(this);
        final FooterDao fd = mAppDatabase.footerDao();

        mVista_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FooterActivity.this, "Aftur á Editsíðuna", Toast.LENGTH_SHORT).show();

                Calendar calendar = Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
                
                /*
                ná í nýjan fót
                */
                Footer tmpFooter = new Footer(currentDate,mName1.getText().toString(),mPhoneNr1.getText().toString(),mName2.getText().toString(),mPhoneNr2.getText().toString());
                fd.insertFooter(tmpFooter);

                /*
                * Bæta footer við ytri gagnagrunn
                */
                String result = null;
                try {
                    result = new Api().postNewFooter(url, currentDate,mName1.getText().toString(),mPhoneNr1.getText().toString(),mName2.getText().toString(),mPhoneNr2.getText().toString(), mAppDatabase.tokenDao().findById(1).getToken());
                }
                catch (IOException e) {
                    System.err.println(e.getMessage());
                }
                catch (JSONException e) {
                    System.err.println(e.getMessage());
                }
                Toast.makeText(FooterActivity.this, "Nýr síðurfótur birtur", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(FooterActivity.this, MainActivity.class);
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
