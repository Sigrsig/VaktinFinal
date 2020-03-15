package is.hi.hbv601.vaktin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import is.hi.hbv601.vaktin.Entities.Workstation;
import is.hi.hbv601.vaktin.R;

public class WorkstationListAdapter extends ArrayAdapter<Workstation> {

    public static final String TAG = "WorkstationListAdapter";

    private Context mContext;
    private int mResource;

    public WorkstationListAdapter(@NonNull Context context, int resource, @NonNull List<Workstation> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getWorkstationName();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        final TextView tvName = (TextView)convertView.findViewById(R.id.text_view);

        tvName.setText(name);
        View row = null;

        return convertView;
    }
}
