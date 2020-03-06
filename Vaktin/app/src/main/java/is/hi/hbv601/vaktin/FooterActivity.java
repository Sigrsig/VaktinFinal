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

public class FooterActivity extends AppCompatActivity {

    Button mVista_button;
    UserLocalStore mUserLocalStore;
    EditText mType1;
    EditText mType2;
    EditText mName1;
    EditText mName2;
    EditText mPhoneNr1;
    EditText mPhoneNr2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footer);
        mVista_button = (Button) findViewById(R.id.vista_button);
        mType1 = (EditText) findViewById(R.id.fType1);
        mType2 = (EditText) findViewById(R.id.fType2);
        mName1 = (EditText) findViewById(R.id.fName1);
        mName2 = (EditText) findViewById(R.id.fName2);
        mPhoneNr1 = (EditText) findViewById(R.id.fPhonenr1);
        mPhoneNr2 = (EditText) findViewById(R.id.fPhonenr2);


        mVista_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FooterActivity.this, "Aftur á Editsíðuna", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(FooterActivity.this, MainActivity.class);
                String message1 = mType1.getText().toString();
                String message2 = mType2.getText().toString();
                String message3 = mName1.getText().toString();
                String message4 = mName2.getText().toString();
                String message5 = mPhoneNr1.getText().toString();
                String message6 = mPhoneNr2.getText().toString();
                String messageFooter
                        = message1 + " - " + message3 + " - " + message5 +" - " + message2 +" - " + message4 +" - " + message6;
                i.putExtra("message_footer", messageFooter);
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
