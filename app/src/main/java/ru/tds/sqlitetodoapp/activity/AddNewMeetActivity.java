package ru.tds.sqlitetodoapp.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

import ru.tds.sqlitetodoapp.DatabaseHelper;
import ru.tds.sqlitetodoapp.R;
import ru.tds.sqlitetodoapp.dialogs.DatePickerFragment;
import ru.tds.sqlitetodoapp.dialogs.TimePickerFragment;

public class AddNewMeetActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "AddNewMeetActivity";

    DatabaseHelper databaseHelper;
    EditText editTextDate, editTextDuration, editTextType, editTextComment;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_meet);

        Log.d(TAG, "onCreate: started");

        // for work button back on action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        databaseHelper = new DatabaseHelper(this);

        // declare items on screen
        editTextDate = findViewById(R.id.editTextDate);
        editTextDuration = findViewById(R.id.editTextDuration);
        editTextType = findViewById(R.id.editTextType);
        editTextComment = findViewById(R.id.editTextComment);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: on btnAdd");

                int duration;

                if (editTextDate.getText().toString().isEmpty()) {
                    Toast.makeText(AddNewMeetActivity.this, "Не указана дата события", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editTextType.getText().toString().isEmpty()) {
                    Toast.makeText(AddNewMeetActivity.this, "Не указан тип события", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!editTextDuration.getText().toString().isEmpty()) {
                    duration = Integer.parseInt(editTextDuration.getText().toString());
                } else {
                    duration = 0;
                }

                if (databaseHelper.insertData(editTextDate.getText().toString(),
                        duration,
                        editTextType.getText().toString(),
                        editTextComment.getText().toString())) {

                    editTextDate.setText("");
                    editTextDuration.setText("");
                    editTextType.setText("");
                    editTextComment.setText("");

                    Intent intent = new Intent(AddNewMeetActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: on editTextDate");
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        editTextType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: on editTextType");
                PopupMenu popupMenu = new PopupMenu(AddNewMeetActivity.this, v);
                popupMenu.inflate(R.menu.menu_type_meet);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.meet:
                                Toast.makeText(getApplicationContext(),
                                        "Вы выбрали 'Встреча с клиентом'",
                                        Toast.LENGTH_SHORT).show();
                                editTextType.setText("Встреча с клиентом");
                                return true;
                            case R.id.show:
                                Toast.makeText(getApplicationContext(),
                                        "Вы выбрали 'Показ'",
                                        Toast.LENGTH_SHORT).show();
                                editTextType.setText("Показ");
                                return true;
                            case R.id.call:
                                Toast.makeText(getApplicationContext(),
                                        "Вы выбрали 'Запланированный звонок'",
                                        Toast.LENGTH_SHORT).show();
                                editTextType.setText("Запланированный звонок");
                                return true;
                            default:
                                return false;
                        }
                    }
                });

//                popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
//                    @Override
//                    public void onDismiss(PopupMenu menu) {
//                        Toast.makeText(getApplicationContext(), "onDismiss",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
                popupMenu.show();
            }
        });
    }

    /**
     * Method for work button back on action bar
     *
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        Log.d(TAG, "onSupportNavigateUp: clicked button back");
        onBackPressed();
//        Intent intent=new Intent();
//        setResult(RESULT_OK, intent);
//        finish();
        return true;
    }

    /**
     * Method for set date when you choose date on DatePickerFragment
     *
     * @param view       DatePicker
     * @param year       year
     * @param month      month
     * @param dayOfMonth dayOfMonth
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.d(TAG, "onDateSet: method is called for set date");

        // set date that you choose
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDate = DateFormat.getDateInstance().format(c.getTime());
        editTextDate.setText(currentDate);

        // open TimePickerFragment after set date on textView
        TimePickerFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
    }

    /**
     * Method for set time when you choose time on TimePickerFragment
     *
     * @param view      TimePicker
     * @param hourOfDay hour
     * @param minute    minute
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.d(TAG, "onTimeSet: method is called for set time");
        String currentTime = null;
        String currentHour = null;
        String currentMinute = null;

        if (hourOfDay == 0) {
            currentHour = "0" + hourOfDay;
        } else if (hourOfDay < 10) {
            currentHour = "0" + hourOfDay;
        } else {
            currentHour = "" + hourOfDay;
        }


        if (minute == 0) {
            currentTime = " " + currentHour + ":00";
        } else if (minute < 10) {
            currentTime = " " + currentHour + ":0" + minute;
        } else {
            currentTime = " " + currentHour + ":" + minute;
        }
        editTextDate.setText(editTextDate.getText() + currentTime);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }
}
