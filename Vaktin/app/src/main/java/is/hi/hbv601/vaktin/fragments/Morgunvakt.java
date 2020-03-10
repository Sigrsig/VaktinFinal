package is.hi.hbv601.vaktin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import is.hi.hbv601.vaktin.Database.AppDatabase;
import is.hi.hbv601.vaktin.Database.WorkstationDao;
import is.hi.hbv601.vaktin.Entities.Employee;
import is.hi.hbv601.vaktin.Entities.Workstation;
import is.hi.hbv601.vaktin.R;
import is.hi.hbv601.vaktin.Utilities.LocalDateConverter;
import is.hi.hbv601.vaktin.Utilities.LocalDateTimeConverter;

public class Morgunvakt extends Fragment {

    private ListView mListView;
    private TextView mTextView;
    private LinearLayout mLinearLayout;
    private ScrollView mScrollView;

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

    /***
     * Laga current min stillingu á api
     *
     */
    private ArrayList<Employee> findAllSortedToday() {
        LocalDate date = LocalDate.now();
        ArrayList<Employee> resultList = new ArrayList<>();
        for (Employee emp : employees) {
            LocalDate empDate = (LocalDateTimeConverter.toDate(emp.gettFrom())).toLocalDate();
            if (empDate.equals(date)) {
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
        AppDatabase db = AppDatabase.getInstance();
        WorkstationDao wd = db.workstationDao();
        ArrayList<Workstation> workstations = (ArrayList)db.workstationDao().findAllWorkstations(); // Finnur öll nöfn á workstation
        employees = (ArrayList)db.employeeDao().loadAllEmployees(); // Sækir alla starfsmenn í Room
        ArrayList<Employee> employeesToday = findAllSortedToday(); // Starfsmenn dagsins flokkaðir í tímaröð
        /***
         * Set nokkra starfsmenn á vinnustöð til að prófa.
         * Eyða síðar
         */
        Long workstationId = workstations.get(1).getWorkstationId();
        Long workstationId2 = workstations.get(2).getWorkstationId();
        employeesToday.get(1).setEmployeeWorkstationId(workstationId);
        employeesToday.get(2).setEmployeeWorkstationId(workstationId2);
        for (Workstation workstation : workstations) {
            TextView textView = new TextView(getActivity());
            textView.setText(workstation.getWorkstationName());
            mLinearLayout.addView(textView);
            /*
            ListView listView = new ListView(getActivity());
            EmployeeListAdapter employeeListAdapter = new EmployeeListAdapter(getActivity(), R.layout.adapter_view_layout, test);
            listView.setAdapter(employeeListAdapter);
            mLinearLayout.addView(listView);
            */
            for (Employee e : employeesToday) {
                if (e.getEmployeeWorkstationId() == workstation.getWorkstationId()) {
                    TextView textViewName= new TextView(getActivity());
                    textViewName.setText(e.getName());
                    mLinearLayout.addView(textViewName);
                    TextView textViewTimeFrom = new TextView(getActivity());
                    textViewTimeFrom.setText(e.gettFrom());
                    mLinearLayout.addView(textViewTimeFrom);
                    TextView textViewTimeTo = new TextView(getActivity());
                    textViewTimeTo.setText(e.gettTo());
                    mLinearLayout.addView(textViewTimeTo);
                }

            }
        }
        //EmployeeListAdapter employeeListAdapter = new EmployeeListAdapter(getActivity(), R.layout.adapter_view_layout, test);
        //mListView.setAdapter(employeeListAdapter);
    }


}
