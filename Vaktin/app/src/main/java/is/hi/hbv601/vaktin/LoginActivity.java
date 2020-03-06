package is.hi.hbv601.vaktin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

        //reyna að tengja myndina á forsíðu
        /*ImageView logo = (ImageView) findViewById(R.id.logo);
        logo.setImageResource(R.drawable.logo);*/

        mUserLocalStore = new UserLocalStore(this);

        mloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = muName.getText().toString();
                String password = mPassword.getText().toString();
                User user = new User(userName,password);
                //mUserLocalStore.storeUserData(user);
                //mUserLocalStore.setUserLoggedIn(true);

                Toast.makeText(LoginActivity.this, "Welcome "+userName, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);

                //forritið krassar þegar loggað er inn vegna þessa búts, má commenta út til að prófa

                try {
                    String result = authenticate(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    //Aðferð til að gera http request. Kallað á hana í onclick þar sem búinn er til nýr user

    protected String authenticate(User user) throws IOException {
        String url = "http://vaktin.is/authenticate";
        final MediaType JSON
                = MediaType.get("application/json; charset=utf-8");
        Gson gson = new Gson();
        String json = gson.toJson(user);
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
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
        muName.setText(user.username);
        mPassword.setText(user.password);
    }
}

