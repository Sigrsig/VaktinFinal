package is.hi.hbv601.vaktin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import is.hi.hbv601.vaktin.fragments.Kvoldvakt;
import is.hi.hbv601.vaktin.fragments.Morgunvakt;
import is.hi.hbv601.vaktin.fragments.Naeturvakt;

public class MainActivity extends AppCompatActivity {
    Button mloginNavButton;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Current date added
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        TextView twDate = findViewById(R.id.currDate);
        twDate.setText(currentDate);

        List<Fragment> list = new ArrayList<>();
        list.add(new Morgunvakt());
        list.add(new Kvoldvakt());
        list.add(new Naeturvakt());

        pager = findViewById(R.id.pager);
        pagerAdapter = new com.example.frontpage3.SlidePagerAdapter(getSupportFragmentManager(), list);

        pager.setAdapter(pagerAdapter);

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
                return true;
            case R.id.logout:
                Toast.makeText(this, "Log Out Selected", Toast.LENGTH_SHORT).show();
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

