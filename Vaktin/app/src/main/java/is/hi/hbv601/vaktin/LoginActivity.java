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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        muName = (EditText) findViewById(R.id.uName);
        mPassword = (EditText) findViewById(R.id.password);
        mloginButton = (Button) findViewById(R.id.login_button);
        //ImageView logo = (ImageView) findViewById(R.id.logo);
        //logo.setImageResource(R.drawable.logo);


        mloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "velkomin á Vaktina", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

}

