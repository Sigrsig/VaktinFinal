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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import is.hi.hbv601.vaktin.Adapters.CommentListAdapter;
import is.hi.hbv601.vaktin.Adapters.WorkstationListAdapter;
import is.hi.hbv601.vaktin.Database.AppDatabase;
import is.hi.hbv601.vaktin.Database.CommentDao;
import is.hi.hbv601.vaktin.Database.EmployeeDao;
import is.hi.hbv601.vaktin.Database.FooterDao;
import is.hi.hbv601.vaktin.Database.TokenDao;
import is.hi.hbv601.vaktin.Database.WorkstationDao;
import is.hi.hbv601.vaktin.Entities.Comment;
import is.hi.hbv601.vaktin.Entities.Employee;
import is.hi.hbv601.vaktin.Entities.Footer;
import is.hi.hbv601.vaktin.Entities.Token;
import is.hi.hbv601.vaktin.Entities.Workstation;
import is.hi.hbv601.vaktin.Utilities.LocalDateConverter;
import is.hi.hbv601.vaktin.Utilities.LocalDateTimeConverter;
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
    private ListView mListView;


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
        /*
        * Birta comment á forsíðu
        */
       /* mComment = (TextView) findViewById(R.id.comment);
        List<Comment> commentM = db.commentDao().findAllComment();
        String tmpString = "";
        for(Comment c: commentM){
            tmpString+=c.getDescription()+"\n";
        }
        mComment.setText(tmpString);*/

        ArrayList<Comment> comments = (ArrayList)db.commentDao().findAllComment();
        ListView mListView = (ListView)findViewById(R.id.comment);
        CommentListAdapter adapter = new CommentListAdapter(this, R.layout.adapter_view_comment, comments);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("id " + id + "\nposition " + position);
                Comment comment =(Comment) parent.getItemAtPosition(position);
                System.out.println("Value is "+comment.getDescription());
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                i.putExtra("description", comment.getDescription());
                startActivity(i);
            }
        });




        //Ná í gögn úr gagnagrunni og birta á aðalsíðu
        mFooter = (TextView) findViewById(R.id.footer);

        Footer c = db.footerDao().findFoooter();
        String tmpFooter ="Vaktstjóri - " + c.getShiftManager()+ " - "+c.getShiftManagerNumber() + " - Deildarstjóri - " + c.getHeadDoctor() + " - " + c.getHeadDoctorNumber();
        mFooter.setText(tmpFooter);



        /***
         * Notandi sendur á login ef ekkert token finnst
         * Token er eytt ef sendandi loggar út
         */
        TokenDao tokenDao = db.tokenDao();
        Token tmpToken = tokenDao.findById(1);
        if (tmpToken == null || tmpToken.getToken() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            Toast.makeText(this, "Vitlaust nafn/password", Toast.LENGTH_SHORT).show();
        }

        else {
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

        /***
         * Passar að gera ekki get request á RestController ef þegar er búið að sækja gögn
         * sama dag.
         */
        if (tmpToken != null) {
            if (tmpToken.isAlreadyInitialized() == false || tmpToken.getToday() == LocalDateConverter.toDateString(LocalDate.now())) {
                tmpToken.setAlreadyInitialized(true);
                tmpToken.setToday(LocalDateConverter.toDateString(LocalDate.now()));
                tokenDao.insertToken(tmpToken);
                initFunc();
            }
        }

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
            case R.id.workstations:
                Toast.makeText(this, "Vinnustöðvar valdar", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, WorkstationsFrontPageActivity.class));
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

                /***
                 * Eyða Room. Finnum betri lausn síðar
                 */
                td.nukeTable();
                db.employeeDao().nukeTable();
                db.workstationDao().nukeTable();
                db.userDao().nukeTable();
                db.commentDao().nukeTable();
                db.footerDao().nukeTable();

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
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject emp = jsonArray.getJSONObject(i);
                        String name = emp.getString("name");
                        String tFrom = emp.getString("tFrom");
                        String tTo = emp.getString("tTo");
                        String role = emp.getString("role");
                        String workstationName = emp.getString("workstation");
                        Employee tmpEmp = new Employee(name, role, tFrom, tTo);
                        if (workstationName != null && !workstationName.equals("null")) {
                            Workstation tmpWorkstation = wd.findWorkstationWithName(workstationName);
                            if (tmpWorkstation != null) {
                                long id = tmpWorkstation.getWorkstationId();
                                tmpEmp.setEmployeeWorkstationId(id);
                            }
                        }
                        else {
                            tmpEmp.setEmployeeWorkstationId(-1);
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

                // Sækja footer

                JSONObject item = new JSONObject();
                String date = item.getString("date");
                String shiftManager = item.getString("shiftManager");
                String shiftManagerNumber = item.getString("shiftManagerNumber");
                String headDoctor = item.getString("headDoctor");
                String headDoctorNumber = item.getString("headDoctorNumber");
                Footer tmpFooter = new Footer(date, shiftManager, shiftManagerNumber, headDoctor, headDoctorNumber);
                FooterDao fd = db.footerDao();
                fd.insertFooter(tmpFooter);


                CommentDao cd = db.commentDao();
                cd.insertAll(comments);

                employeeDao.insertAll(employees);



            }
            catch (JSONException e) {
                System.err.println(e.getMessage());
            }





        }

    }






}
