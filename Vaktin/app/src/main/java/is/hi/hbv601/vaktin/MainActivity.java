package is.hi.hbv601.vaktin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.room.Database;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.telecom.Call;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import is.hi.hbv601.vaktin.Database.AppDatabase;
import is.hi.hbv601.vaktin.Database.CommentDao;
import is.hi.hbv601.vaktin.Database.EmployeeDao;
import is.hi.hbv601.vaktin.Database.TokenDao;
import is.hi.hbv601.vaktin.Database.WorkstationDao;
import is.hi.hbv601.vaktin.Entities.Comment;
import is.hi.hbv601.vaktin.Entities.Employee;
import is.hi.hbv601.vaktin.Entities.Footer;
import is.hi.hbv601.vaktin.Entities.Token;
import is.hi.hbv601.vaktin.Entities.Workstation;
import is.hi.hbv601.vaktin.fragments.Kvoldvakt;
import is.hi.hbv601.vaktin.fragments.Morgunvakt;
import is.hi.hbv601.vaktin.fragments.Naeturvakt;



public class MainActivity extends AppCompatActivity {

    Button mloginNavButton;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    UserLocalStore mUserLocalStore;
    TextView mComment;
    TextView mFooter;

    private final String url = "http://10.0.2.2:8080/";

    // Instance fyrir DB
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        db = AppDatabase.getAppDatabase(this);



        setContentView(R.layout.activity_main);

        mComment = (TextView) findViewById(R.id.comment);
        String commentMessage = getIntent().getStringExtra("message_comment");
        mComment.setText(commentMessage);

        mFooter = (TextView) findViewById(R.id.footer);
        String footerMessage = getIntent().getStringExtra("message_footer");
        mFooter.setText(footerMessage);
/*
        TokenDao tokenDao = db.tokenDao();
        if (tokenDao.findById(1) == null || tokenDao.findById(1).getToken() == null) {
            initFunc();
            startActivity(new Intent(this, LoginActivity.class));
        }*/

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
        switch (item.getItemId()) {
            case R.id.edit:
                Toast.makeText(this, "Edit Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, EditActivity.class));
                return true;
            case R.id.logout:
                Toast.makeText(this, "Log Out Selected", Toast.LENGTH_SHORT).show();
                mUserLocalStore.clearedUserData();
                mUserLocalStore.setUserLoggedIn(false);

                // Eyða token
                AppDatabase db = AppDatabase.getInstance();
                TokenDao td = db.tokenDao();
                Token tmpToken = td.findById(1);
                tmpToken.setToken(null);
                td.insertToken(tmpToken);

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

    private void initFunc() {
        TokenDao td = db.tokenDao();
        Token tmpToken = td.findById(1);
        if (tmpToken != null) {
            String result = null;
            try {
                result = new Api().getFrontPage(url, tmpToken.getToken());
            }
            catch (IOException e) {
                System.err.println(e.getMessage());
            }


            List<Employee> employees = new ArrayList<>();
            EmployeeDao employeeDao = db.employeeDao();
            try {
                JSONObject jsonBody = new JSONObject(result);

                // Sækja allar vinnustöðvar
                JSONArray jsonArray = jsonBody.getJSONArray("workstations");
                WorkstationDao wd = db.workstationDao();
                List<Workstation> workstations = new ArrayList<>();
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String name = obj.getString("workstationName");
                        Workstation tmpWorkstation = new Workstation(name);
                        workstations.add(tmpWorkstation);
                    }
                }

                wd.insertAll(workstations);

                // Sækja starfsmenn í vinnu í dag
                jsonArray = jsonBody.getJSONArray("employeesToday");
                if (jsonArray != null) {
                    System.out.println("jsonArray length " + jsonArray.length());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject emp = jsonArray.getJSONObject(i);
                        String name = emp.getString("name");
                        String tFrom = emp.getString("tFrom");
                        String tTo = emp.getString("tTo");
                        String role = emp.getString("role");
                        String workstationName = emp.getString("workstation");
                        Employee tmpEmp = new Employee(name, role, tFrom, tTo);
                        System.out.println("workstationName.equals() "+ workstationName.equals("null"));
                        if (workstationName != null && !workstationName.equals("null")) {
                            System.out.println(workstationName);
                            Workstation tmpWorkstation = wd.findWorkstationWithName(workstationName);
                            System.out.println("vinnustöð " + tmpWorkstation);
                            if (tmpWorkstation != null) {
                                long id = tmpWorkstation.getWorkstationId();
                                tmpEmp.setEmployeeWorkstationId(id);
                            }

                        }
                        employees.add(tmpEmp);
                    }
                }

                // Sækja starfsmenn í vinnu næstu nótt
                jsonArray = jsonBody.getJSONArray("employeesTomorrow");
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String name = obj.getString("name");
                        String tFrom = obj.getString("tFrom");
                        String tTo = obj.getString("tTo");
                        String role = obj.getString("role");
                        Employee tmpEmp = new Employee(name, role, tFrom, tTo);
                        employees.add(tmpEmp);
                    }
                }

                // Sækja comments
                jsonArray = jsonBody.getJSONArray("comments");
                List<Comment> comments = new ArrayList<>();
                if (jsonArray != null && jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String descr = obj.getString("description");
                        Comment tmpComment = new Comment(descr);
                        comments.add(tmpComment);
                    }
                }

                // Sækja footer SEINNA
                /*
                JSONObject obj = jsonBody.getString("footer");
                if (obj != null) {
                    String shiftManager = obj.getString("")
                    Footer tmpFooter =
                }
                */

                CommentDao cd = db.commentDao();
                cd.insertAll(comments);

                employeeDao.insertAll(employees);

                System.out.println("ok kúl mar");


            }
            catch (JSONException e) {
                System.err.println(e.getMessage());
            }





        }

    }






}
