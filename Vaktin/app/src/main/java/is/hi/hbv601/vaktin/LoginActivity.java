package is.hi.hbv601.vaktin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button mloginButton;
    EditText muName;
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            mloginButton = (Button) findViewById(R.id.login_button);
            muName = (EditText) findViewById(R.id.uName);
            mPassword = (EditText) findViewById(R.id.password);
            mloginButton.setOnClickListener(this) ;
            /*mloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.login_Button:

                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }


}
