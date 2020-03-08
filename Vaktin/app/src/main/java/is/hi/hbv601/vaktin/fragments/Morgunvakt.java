package is.hi.hbv601.vaktin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import is.hi.hbv601.vaktin.Adapters.EmployeeListAdapter;
import is.hi.hbv601.vaktin.Database.AppDatabase;
import is.hi.hbv601.vaktin.Database.WorkstationDao;
import is.hi.hbv601.vaktin.Entities.Employee;
import is.hi.hbv601.vaktin.Entities.Workstation;
import is.hi.hbv601.vaktin.R;

public class Morgunvakt extends Fragment {

    private ListView mListView;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mListView = (ListView)getView().findViewById(R.id.front_page_list);

        AppDatabase db = AppDatabase.getInstance();
        //WorkstationDao wd = db.workstationDao();
        ArrayList<Employee> test = (ArrayList)db.employeeDao().loadAllEmployees();
        EmployeeListAdapter employeeListAdapter = new EmployeeListAdapter(getActivity(), R.layout.adapter_view_layout, test);
        mListView.setAdapter(employeeListAdapter);
    }


}
