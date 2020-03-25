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

import is.hi.hbv601.vaktin.Database.AppDatabase;
import is.hi.hbv601.vaktin.Database.WorkstationDao;
import is.hi.hbv601.vaktin.Entities.Employee;
import is.hi.hbv601.vaktin.Entities.Workstation;
import is.hi.hbv601.vaktin.R;
import is.hi.hbv601.vaktin.Utilities.LocalDateTimeConverter;
import is.hi.hbv601.vaktin.Utilities.TimeSorter;

public class Naeturvakt extends Fragment {

    private LinearLayout mLinearLayout;
    private ArrayList<Employee> employees;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.naeturvakt,
                container,
                false);


        return rootView;
    }


    private ArrayList<Employee> findAllSortedTomorrow() {
        LocalDate date = LocalDate.now().plusDays(1);
        ArrayList<Employee> resultList = new ArrayList<>();
        for (Employee emp : employees) {
            LocalDateTime empDateTime = LocalDateTimeConverter.toDate(emp.gettFrom());
            if (empDateTime.toLocalDate().equals(date) && empDateTime.getHour() < 3) {
                resultList.add(emp);
            }
        }
        resultList.sort(new TimeSorter());
        return resultList;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mLinearLayout = (LinearLayout)getView().findViewById(R.id.test);

        /***
         * Sækja gögn til að birta við morgunvakt
         * Af hverju getur maður ekki bætt við nýju ListView fyrir hverja starfsstöð?
         */
        AppDatabase db = AppDatabase.getInstance();
        WorkstationDao wd = db.workstationDao();
        ArrayList<Workstation> workstations = (ArrayList)db.workstationDao().findAllWorkstations(); // Finnur öll nöfn á workstation
        employees = (ArrayList)db.employeeDao().loadAllEmployees(); // Sækir alla starfsmenn í Room
        ArrayList<Employee> employeesToday = findAllSortedTomorrow(); // Starfsmenn dagsins flokkaðir í tímaröð

        for (Workstation workstation : workstations) {
            TextView textView = new TextView(getActivity());
            textView.setText(workstation.getWorkstationName());
            mLinearLayout.addView(textView);

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

    }
}
