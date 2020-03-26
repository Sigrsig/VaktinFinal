package is.hi.hbv601.vaktin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import is.hi.hbv601.vaktin.Entities.Employee;
import is.hi.hbv601.vaktin.R;

/***
 *  NOT USED. DELETE BEFORE TURNING IN
 */
public class EmployeeListAdapter extends ArrayAdapter<Employee> {

    private static final String TAG = "EmployeeListAdapter";

    private Context mContext;
    private int mResource;

    public EmployeeListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Employee> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName();
        String role = getItem(position).getRole();
        String tFrom = getItem(position).gettFrom();
        String tTo = getItem(position).gettTo();

        //Employee employee = new Employee(name, role, tFrom, tTo);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView)convertView.findViewById(R.id.text_view_1);
        TextView tvTFrom = (TextView)convertView.findViewById(R.id.text_view_2);
        TextView tvTTo = (TextView)convertView.findViewById(R.id.text_view_3);

        tvName.setText(name);
        tvTFrom.setText(tFrom);
        tvTTo.setText(tTo);

        return convertView;
    }
}
