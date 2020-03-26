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

import is.hi.hbv601.vaktin.Api;
import is.hi.hbv601.vaktin.Database.AppDatabase;
import is.hi.hbv601.vaktin.Database.WorkstationDao;
import is.hi.hbv601.vaktin.Entities.Employee;
import is.hi.hbv601.vaktin.Entities.Workstation;
import is.hi.hbv601.vaktin.MainActivity;
import is.hi.hbv601.vaktin.R;
import is.hi.hbv601.vaktin.Utilities.LocalDateTimeConverter;
import is.hi.hbv601.vaktin.Utilities.TimeSorter;

/***
 * Fragment for Kvöldvakt in MainActivity
 */
public class Kvoldvakt extends Fragment {

    private LinearLayout mLinearLayout;
    private ArrayList<Employee> employees; // Fetches all employees in Room database
    private final String buttonString = "Fjarlægja";
    private final String url = "http://10.0.2.2:8080/removeemployee"; // POST request to remove employee from workstation

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.kvoldvakt,
                container,
                false);

        return rootView;
    }


    /***
     * Filters and sorts all employees working on night shift
     * @return List of employees working on night shift
     */
    private ArrayList<Employee> findAllSortedToday() {
        LocalDate date = LocalDate.now();
        ArrayList<Employee> resultList = new ArrayList<>();
        for (Employee emp : employees) {
            LocalDateTime empDateTime = LocalDateTimeConverter.toDate(emp.gettFrom());
            if (empDateTime.toLocalDate().equals(date) && empDateTime.getHour() >= 16) {
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
         *
         * Role is missing
         */
        final AppDatabase db = AppDatabase.getInstance();
        ArrayList<Workstation> workstations = (ArrayList)db.workstationDao().findAllWorkstations(); // Finnur öll nöfn á workstation
        employees = (ArrayList)db.employeeDao().loadAllEmployees(); // Sækir alla starfsmenn í Room
        ArrayList<Employee> employeesToday = findAllSortedToday(); // Starfsmenn dagsins flokkaðir í tímaröð

        for (Workstation workstation : workstations) {

            TextView textView = new TextView(getActivity()); // Name of workstation
            textView.setTextAppearance(getActivity(), R.style.workst_style);

            textView.setText(workstation.getWorkstationName());
            mLinearLayout.addView(textView);

            for (final Employee e : employeesToday) {
                if (e.getEmployeeWorkstationId() == workstation.getWorkstationId()) {
                    TextView textViewName= new TextView(getActivity()); // Name of employee
                    textViewName.setTextAppearance(getActivity(), R.style.title_style);
                    textViewName.setText(e.getName());
                    mLinearLayout.addView(textViewName);
                    TextView textViewTimeFrom = new TextView(getActivity()); // Start of shift
                    textViewName.setTextAppearance(getActivity(), R.style.title_style);
                    textViewTimeFrom.setText(e.gettFrom());
                    mLinearLayout.addView(textViewTimeFrom);
                    TextView textViewTimeTo = new TextView(getActivity()); // End of shift
                    textViewName.setTextAppearance(getActivity(), R.style.title_style);
                    textViewTimeTo.setText(e.gettTo());
                    mLinearLayout.addView(textViewTimeTo);

                    Button button = new Button(getActivity()); // Button to remove from workstation
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
