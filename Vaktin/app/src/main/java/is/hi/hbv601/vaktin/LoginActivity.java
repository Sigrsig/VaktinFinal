package is.hi.hbv601.vaktin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity{

    Button mloginButton;
    EditText muName;
    EditText mPassword;
    ImageView mlogo;
    UserLocalStore mUserLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        muName = (EditText) findViewById(R.id.uName);
        mPassword = (EditText) findViewById(R.id.password);
        mloginButton = (Button) findViewById(R.id.login_button);
        //ImageView logo = (ImageView) findViewById(R.id.logo);
        //logo.setImageResource(R.drawable.logo);

        mUserLocalStore = new UserLocalStore(this);

        mloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = muName.getText().toString();
                String password = mPassword.getText().toString();
                User user = new User(userName,password);
                mUserLocalStore.storeUserData(user);
                mUserLocalStore.setUserLoggedIn(true);
                Toast.makeText(LoginActivity.this, "Welcome "+userName, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);

                /*Hér þarf að ná í notendaupplýsingar og athuga hvort gilt password/uName
                * Nota einhverjar if lykkjur
                *
                * */

            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
    }
    private boolean authenticate(){
        return mUserLocalStore.getUserLoggefIn();
    }

    private void displayUserDetails(){
        User user = mUserLocalStore.getLoggedInUser();
        muName.setText(user.uName);
        mPassword.setText(user.password);
    }
}

