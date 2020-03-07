package is.hi.hbv601.vaktin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import is.hi.hbv601.vaktin.Database.AppDatabase;
import is.hi.hbv601.vaktin.Database.WorkstationDao;
import is.hi.hbv601.vaktin.Entities.Workstation;
import is.hi.hbv601.vaktin.R;

public class Morgunvakt extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.morgunvakt,
                container,
                false);

        AppDatabase db = AppDatabase.getInstance();
        WorkstationDao wd = db.workstationDao();
       // List<Workstation> workstations = wd.

        return rootView;
    }
}
