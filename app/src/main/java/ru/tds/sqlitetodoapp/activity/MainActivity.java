package ru.tds.sqlitetodoapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.tds.sqlitetodoapp.DatabaseHelper;
import ru.tds.sqlitetodoapp.MeetArrayAdapter;
import ru.tds.sqlitetodoapp.R;
import ru.tds.sqlitetodoapp.entities.Meet;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    DatabaseHelper databaseHelper;
    ListView listView;
    ArrayList<Meet> arrayList;
    MeetArrayAdapter adapter;
    Cursor data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNewMeetActivity.class);
                startActivity(intent);
            }
        });

        listView = findViewById(R.id.list_view);
        databaseHelper = new DatabaseHelper(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "cliclked on item: " + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: Started.");
        super.onResume();

        arrayList = new ArrayList<>();
        data = databaseHelper.getAllData();

        if (data.getCount() == 0) {
            Toast.makeText(this, "The database is empty :(", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()) {
                //create object class Meet
                Meet meet = new Meet();
                meet.setId(data.getInt(0));
                meet.setDate(data.getString(1));
                meet.setDuration(Integer.parseInt(data.getString(2)));
                meet.setType(data.getString(3));
                meet.setComment(data.getString(4));

                arrayList.add(meet);
            }
        }

        adapter = new MeetArrayAdapter(this, R.layout.adapter_view_layout, arrayList);
        listView.setAdapter(adapter);

        //create ArrayAdapter for show 2 lines
//            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, arrayList) {
//                @Override
//                public View getView(int position, View convertView, ViewGroup parent) {
//
////                        View view = super.getView(position, convertView, parent);
////                        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
////                        TextView text2 = (TextView) view.findViewById(android.R.id.text2);
////
////                        text1.setText(arrayList.get(position).getType());
////                        text2.setText(arrayList.get(position).getDate());
////                        return view;
//
//                    TwoLineListItem row = (TwoLineListItem) super.getView(position, convertView, parent);
//
//                    row.getText1().setText(arrayList.get(position).getType());
//                    row.getText2().setText(arrayList.get(position).getDate());
//
//                    return row;
//                }
//            };
//        }
//        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * For open settings
     *
     * @param item settings three dots
     * @return open settings menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cancelled_meets) {
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        data.close();
        databaseHelper.close();
    }
}
