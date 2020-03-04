package is.hi.hbv601.vaktin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CommentActivity extends AppCompatActivity {

    Button mTilbaka_button;
    UserLocalStore mUserLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        mTilbaka_button = (Button) findViewById(R.id.tilbaka_button);


        mTilbaka_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CommentActivity.this, "Aftur á Editsíðuna", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(CommentActivity.this, EditActivity.class);
                startActivity(i);

            }
        });

        mUserLocalStore = new UserLocalStore(this);
    }
}
