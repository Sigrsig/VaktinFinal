package is.hi.hbv601.vaktin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import is.hi.hbv601.vaktin.Database.AppDatabase;
import is.hi.hbv601.vaktin.Database.CommentDao;
import is.hi.hbv601.vaktin.Entities.Comment;

public class CommentActivity extends AppCompatActivity {

    UserLocalStore mUserLocalStore;
    Button mSave_button;
    EditText mComment;
    AppDatabase mAppDatabase;
    private String url = "http://10.0.2.2:8080/addcomment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        mSave_button = (Button) findViewById(R.id.vista_button);
        mComment = (EditText) findViewById(R.id.comment);

        /***
         * Comment Dao til að vista nýtt Comment
         */
        mAppDatabase = AppDatabase.getAppDatabase(this);
        final CommentDao cd = mAppDatabase.commentDao();

        mSave_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageComment = mComment.getText().toString();

                // Athugasemt þegar til?

                if (cd.findCommentWithDescription(messageComment) == null) {
                    Comment tmpComment = new Comment(mComment.getText().toString());
                    cd.insertComment(tmpComment);

                    /*
                     * Bæta commenti við ytri gagnagrunn
                     */
                    String result = null;
                    try {
                        result = new Api().postNewComment(url, messageComment, mAppDatabase.tokenDao().findById(1).getToken());
                    }
                    catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                    catch (JSONException e) {
                        System.err.println(e.getMessage());
                    }
                    Toast.makeText(CommentActivity.this, "Ný athugasemd birt", Toast.LENGTH_SHORT).show();
                }

                Intent i = new Intent(CommentActivity.this, MainActivity.class);
                i.putExtra("message_comment", messageComment);
                startActivity(i);
            }
        });

        mUserLocalStore = new UserLocalStore(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.main:
                Toast.makeText(this, "Main page Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
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
