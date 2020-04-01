package is.hi.hbv601.vaktin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import is.hi.hbv601.vaktin.Database.AppDatabase;
import is.hi.hbv601.vaktin.Database.TokenDao;
import is.hi.hbv601.vaktin.Database.UserDao;
import is.hi.hbv601.vaktin.Entities.Employee;
import is.hi.hbv601.vaktin.Entities.Token;
import is.hi.hbv601.vaktin.Entities.User;

public class PopUpRemoveEmployeeActivity extends AppCompatActivity {
    Button mDeleteButton;
    Button mNoButton;
    ListView mCommment;
    Context context;
    private final String url = "http://10.0.2.2:8080/deleteemployee";

    private Context getThisContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.5));

        mDeleteButton = (Button) findViewById(R.id.delete_button);
        mNoButton = (Button) findViewById(R.id.no_button);
        mCommment = (ListView) findViewById(R.id.comment);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppDatabase db = AppDatabase.getInstance();
                long id = getIntent().getLongExtra("employeeId", -1);
                Employee tmpEmp = db.employeeDao().findById(id);
                // Delete from REST
                // Vista breytingar í REST
                new Api().removeEmployee(url, tmpEmp, db.tokenDao().findById(1).getToken());


                db.employeeDao().deleteEmployee(tmpEmp);



                Toast.makeText(getThisContext(), "Starfsmanni eytt", Toast.LENGTH_SHORT).show();


                Intent i = new Intent(PopUpRemoveEmployeeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PopUpRemoveEmployeeActivity.this, RemoveEmployeeActivity.class);
                startActivity(i);

            }
        });
    }
}