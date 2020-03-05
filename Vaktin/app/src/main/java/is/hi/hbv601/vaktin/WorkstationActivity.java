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

public class WorkstationActivity extends AppCompatActivity {

    Button mTilbaka_button;
    UserLocalStore mUserLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workstation);
        mTilbaka_button = (Button) findViewById(R.id.tilbaka_button);


        mTilbaka_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WorkstationActivity.this, "Aftur á Edit síðuna", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(WorkstationActivity.this, EditActivity.class);
                startActivity(i);

            }
        });

        mUserLocalStore = new UserLocalStore(this);
    }

}
