package is.hi.hbv601.vaktin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CommentActivity extends AppCompatActivity {

    UserLocalStore mUserLocalStore;
    Button mSave_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        mSave_button = (Button) findViewById(R.id.vista_button);


        mSave_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CommentActivity.this, "Ný athugasemd vistuð", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(CommentActivity.this, EditActivity.class);
                startActivity(i);

            }
        });

        mUserLocalStore = new UserLocalStore(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.edit:
                Toast.makeText(this, "Edit Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, EditActivity.class));
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
