package is.hi.hbv601.vaktin.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

import is.hi.hbv601.vaktin.Adapters.EmployeeListAdapter;
import is.hi.hbv601.vaktin.Api;
import is.hi.hbv601.vaktin.Database.AppDatabase;
import is.hi.hbv601.vaktin.Database.WorkstationDao;
import is.hi.hbv601.vaktin.Entities.Employee;
import is.hi.hbv601.vaktin.Entities.Workstation;
import is.hi.hbv601.vaktin.MainActivity;
import is.hi.hbv601.vaktin.R;
import is.hi.hbv601.vaktin.Utilities.LocalDateConverter;
import is.hi.hbv601.vaktin.Utilities.LocalDateTimeConverter;
import is.hi.hbv601.vaktin.WorkstationListActivity;

public class Morgunvakt extends Fragment {

    private ListView mListView;
    private TextView mTextView;
    private LinearLayout mLinearLayout;
    private ScrollView mScrollView;
    private final String buttonString = "Fjarlægja";
    private final String url = "http://10.0.2.2:8080/removeemployee";

    private ArrayList<Employee> employees;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.morgunvakt,
                container,
                false);


        return rootView;
    }

    /***
     * TimeSorter class for sorting employees by hour arrived
     */
    private class TimeSorter implements Comparator<Employee> {

        @Override
        public int compare(Employee a, Employee b) {
            return a.gettFrom().compareTo(b.gettFrom());
        }
    }


    private ArrayList<Employee> findAllSortedToday() {
        LocalDate date = LocalDate.now();
        ArrayList<Employee> resultList = new ArrayList<>();
        for (Employee emp : employees) {
            //LocalDate empDate = (LocalDateTimeConverter.toDate(emp.gettFrom())).toLocalDate();
            LocalDateTime empDateTime = LocalDateTimeConverter.toDate(emp.gettFrom());
            if (empDateTime.toLocalDate().equals(date) && empDateTime.getHour() < 16) {
                resultList.add(emp);
            }
        }
        resultList.sort(new TimeSorter());
        return resultList;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //mListView = (ListView)getView().findViewById(R.id.front_page_list);
        mLinearLayout = (LinearLayout)getView().findViewById(R.id.test);


        /***
         * Sækja gögn til að birta við morgunvakt
         * Af hverju getur maður ekki bætt við nýju ListView fyrir hverja starfsstöð?
         */
        final AppDatabase db = AppDatabase.getInstance();
        WorkstationDao wd = db.workstationDao();
        ArrayList<Workstation> workstations = (ArrayList)db.workstationDao().findAllWorkstations(); // Finnur öll nöfn á workstation
        employees = (ArrayList)db.employeeDao().loadAllEmployees(); // Sækir alla starfsmenn í Room
        ArrayList<Employee> employeesToday = findAllSortedToday(); // Starfsmenn dagsins flokkaðir í tímaröð

        for (Workstation workstation : workstations) {
            TextView textView = new TextView(getActivity());
            textView.setTextAppearance(getActivity(), R.style.workst_style);
            textView.setText(workstation.getWorkstationName());
            mLinearLayout.addView(textView);

            for (final Employee e : employeesToday) {
                if (e.getEmployeeWorkstationId() == workstation.getWorkstationId()) {
                    TextView textViewName= new TextView(getActivity());
                    textViewName.setTextAppearance(getActivity(), R.style.title_style);
                    textViewName.setText(e.getName());
                    mLinearLayout.addView(textViewName);
                    TextView textViewTimeFrom = new TextView(getActivity());
                    textViewName.setTextAppearance(getActivity(), R.style.title_style);
                    textViewTimeFrom.setText(e.gettFrom());
                    mLinearLayout.addView(textViewTimeFrom);
                    TextView textViewTimeTo = new TextView(getActivity());
                    textViewName.setTextAppearance(getActivity(), R.style.title_style);
                    textViewTimeTo.setText(e.gettTo());
                    mLinearLayout.addView(textViewTimeTo);
                    Button button = new Button(getActivity());
                    button.setTextAppearance(getActivity(), R.style.butt);
                    button.setText(buttonString);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            e.setEmployeeWorkstationId(-1);
                            db.employeeDao().insertEmployee(e);

                            // Vista breytingar í REST
                            new Api().removeEmployeeFromWorkstation(url, e, db.tokenDao().findById(1).getToken());

                            Intent i = new Intent(getActivity(), MainActivity.class);
                            startActivity(i);

                        }
                    });

                    mLinearLayout.addView(button);
                }

            }
        }

    }


}
