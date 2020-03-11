package is.hi.hbv601.vaktin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import is.hi.hbv601.vaktin.Database.AppDatabase;
import is.hi.hbv601.vaktin.Entities.Workstation;

public class WorkstationListActivity extends AppCompatActivity {

    private TextView mTextView;
    private AppDatabase db;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workstationlist);

        mTextView = (TextView)findViewById(R.id.textView);

        // Sækja rétt id
        // Þarf að grípa ef -1?
        Intent i = getIntent();
        id = i.getLongExtra("workstationId", -1);

        // Tenging við Room og finna rétta vinnustöð
        db = AppDatabase.getAppDatabase(this);
        Workstation workstation = db.workstationDao().findWorkstationWithId(id);

        mTextView.setText(workstation.getWorkstationName());



    }
}
