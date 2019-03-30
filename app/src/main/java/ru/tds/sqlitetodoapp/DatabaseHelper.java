package ru.tds.sqlitetodoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import ru.tds.sqlitetodoapp.activity.AddNewMeetActivity;

/*
 * Created by Trushenkov Dmitry on 28.03.2019
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    /* table columns */
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date_time";
    public static final String COLUMN_TYPE = "type_meet";
    private static final String DATABASE_NAME = "DatabaseMeets.db"; //database name
    private static final String TABLE_NAME = "Meets"; //table name
    private static final int DATABASE_VERSION = 1; //database version
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_COMMENT = "comment";

    private Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        SQLiteDatabase db = this.getWritableDatabase();
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date_time NOT NULL, " +
                "duration INTEGER, " +
                "type_meet TEXT NOT NULL, " +
                "comment TEXT); ");
        db.execSQL("insert into " + TABLE_NAME + " (date_time, duration, type_meet, comment) values('28.03.2019 15:40', '30', 'Встреча с клиентом', 'Кирова 51')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Метод для получения всех данных из базы
     *
     * @return набор данных, возвращаемый запросом к базе данных.
     */
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    /**
     * Метод для добавления новой записи в базу данных
     *
     * @param date     дата-время
     * @param duration длительность
     * @param type     тип события
     * @param comment  комментарий
     * @return true, если запись добавлена, false - иначе
     */
    public boolean insertData(String date, int duration, String type, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_DURATION, duration);
        contentValues.put(COLUMN_TYPE, type);
        contentValues.put(COLUMN_COMMENT, comment);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            Toast.makeText(mContext, "Ошибки при добавлении", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(mContext, "Запись добавлена успешно", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    /**
     * Метод для удаления записи из базы
     *
     * @param id id записи, которую нужно удалить
     */
    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "_id = ?", new String[]{String.valueOf(id)});
    }
}
