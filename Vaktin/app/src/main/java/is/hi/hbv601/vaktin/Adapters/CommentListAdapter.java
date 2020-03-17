package is.hi.hbv601.vaktin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

import is.hi.hbv601.vaktin.CommentActivity;
import is.hi.hbv601.vaktin.Database.AppDatabase;
import is.hi.hbv601.vaktin.Database.CommentDao;
import is.hi.hbv601.vaktin.Entities.Comment;
import is.hi.hbv601.vaktin.Entities.Workstation;
import is.hi.hbv601.vaktin.MainActivity;
import is.hi.hbv601.vaktin.R;

public class CommentListAdapter extends ArrayAdapter<Comment> {

    public static final String TAG = "CommentListAdapter";

    private Context mContext;
    private int mResource;
    AppDatabase mAppDatabase;


    public CommentListAdapter(@NonNull Context context, int resource, @NonNull List<Comment> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String description = getItem(position).getDescription();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView)convertView.findViewById(R.id.text_view);

        tvName.setText(description);
        //Handle buttons and add onClickListeners
        /*Button callbtn = (Button)convertView.findViewById(R.id.btn);

        callbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //mAppDatabase = AppDatabase.getAppDatabase(mContext);
                //final CommentDao cd = mAppDatabase.commentDao().nukeTable();
                //cd.nukeTable();

            }
        });*/


        return convertView;
    }
}