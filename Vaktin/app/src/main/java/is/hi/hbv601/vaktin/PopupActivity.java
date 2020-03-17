package is.hi.hbv601.vaktin;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import is.hi.hbv601.vaktin.Database.AppDatabase;
import is.hi.hbv601.vaktin.Database.TokenDao;
import is.hi.hbv601.vaktin.Database.UserDao;
import is.hi.hbv601.vaktin.Entities.Token;
import is.hi.hbv601.vaktin.Entities.User;

public class PopupActivity extends AppCompatActivity {
    Button mDeleteButton;
    Button mNoButton;
    ListView mCommment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                db.commentDao().deleteComment(getIntent().getStringExtra("description"));
                Intent i = new Intent(PopupActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PopupActivity.this, MainActivity.class);
                startActivity(i);

            }
        });
    }
}
