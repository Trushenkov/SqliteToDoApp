package ru.tds.sqlitetodoapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.tds.sqlitetodoapp.entities.Meet;

/*
 * Custom adapter for ListView.
 *
 * Created by Trushenkov Dmitry on 30.03.2019
 */
public class MeetArrayAdapter extends ArrayAdapter<Meet> {

    private static final String TAG = "MeetArrayAdapter";

    private ArrayList<Meet> meetsArrayList;
    private Context mContext;
    private int mResource;

    public MeetArrayAdapter(Context context, int resource, ArrayList<Meet> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        meetsArrayList = objects;
    }


    @Override
    public long getItemId(int position) {
        return meetsArrayList.get(position).getId();
    }

    @Override
    public int getCount() {
        return meetsArrayList.size();
    }

    @Override
    public Meet getItem(int position) {
        return meetsArrayList.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //Get data of object on that clicked
        int id = getItem(position).getId();
        String type = getItem(position).getType();
        String comment = getItem(position).getComment();
        String duration = String.valueOf(getItem(position).getDuration());
        String date = getItem(position).getDate();

        final Meet meet = new Meet(id, date, Integer.parseInt(duration), type, comment);

        // convertview - view for interaction with ListView. Example - onClickListener, click button "cancel" etc.
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        //declare items on screen
        TextView tvType = convertView.findViewById(R.id.tvType);
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        Button btnCancel = convertView.findViewById(R.id.btnCancel);

        //set text on set view
        tvType.setText(type);
        tvDate.setText(date);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper helper = new DatabaseHelper(getContext());
                helper.delete(getItem(position).getId());
                remove(meetsArrayList.get(position));
                notifyDataSetChanged();
                Toast.makeText(mContext, "Событие #" + position + " отменено", Toast.LENGTH_SHORT).show();
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Clicked on item: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
