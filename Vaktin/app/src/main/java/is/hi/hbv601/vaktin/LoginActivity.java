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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import is.hi.hbv601.vaktin.Database.AppDatabase;
import is.hi.hbv601.vaktin.Database.TokenDao;
import is.hi.hbv601.vaktin.Database.UserDao;
import is.hi.hbv601.vaktin.Entities.Token;
import is.hi.hbv601.vaktin.Entities.User;
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

    //private final String loginString = "http://localhost:8080/authenticate";
    private final String loginString = "http://10.0.2.2:8080/authenticate";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
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

                JSONObject json = new JSONObject();
                try {
                    json.put("username", userName);
                    json.put("password", password);
                }
                catch (JSONException e) {
                    System.err.println(e.getMessage());
                }


                String result = null;
                try {
                    result = new Api().postAuth(loginString, json.toString());
                }
                catch (IOException e) {
                    System.err.println(e.getMessage());
                }

                String t = null;
                try {
                    if (result != null) {
                        JSONObject jsonBody = new JSONObject(result);
                        t = jsonBody.getString("token");
                    }

                }
                catch (JSONException e) {
                    System.err.println(e.getMessage());
                }

                /***
                 * Ef /authentication virkar þá er búið til token og user í Room
                 * User er með token breytu en kannski er óþarfi að hafa hana og betra að vera
                 * alltaf bara með eitt token í Room
                 */
                if (result != null && t != null) {
                    User tmpUser = new User(userName, password);
                    tmpUser.setToken(t);
                    AppDatabase db = AppDatabase.getInstance();
                    UserDao ud = db.userDao();
                    ud.insertUsers(tmpUser);
                    TokenDao td = db.tokenDao();
                    Token tmpToken = new Token(t);
                    td.insertToken(tmpToken);
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Vitlaust nafn/lykilorð", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                }





                //Toast.makeText(LoginActivity.this, "Welcome "+userName, Toast.LENGTH_SHORT).show();


                //forritið krassar þegar loggað er inn vegna þessa búts, má commenta út til að prófa

                /*
                try {
                    String result = authenticate(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */

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

