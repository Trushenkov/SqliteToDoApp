package ru.tds.sqlitetodoapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.tds.sqlitetodoapp.R;

/**
 * Activity for showing cancelled meets
 *
 * @author Trushenkov Dmitry
 */
public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
