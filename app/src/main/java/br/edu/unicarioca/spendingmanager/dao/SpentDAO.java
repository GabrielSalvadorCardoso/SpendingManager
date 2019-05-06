package br.edu.unicarioca.spendingmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.edu.unicarioca.spendingmanager.models.Spent;

public class SpentDAO extends SQLiteOpenHelper {

    public SpentDAO(Context context) {
        super(context, "Spent", null, 1);
    }

    public List<Spent> list() {
        String sql = "SELECT * FROM Spent";
        List<Spent> spendings = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        while(cursor.moveToNext()) {
            String dateString = cursor.getString(cursor.getColumnIndex("date"));
            Date date =  new Date();
            try {
                date = simpleDateFormat.parse(dateString);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Spent spent = new Spent(
                cursor.getLong(cursor.getColumnIndex("id")),
                date,
                cursor.getString(cursor.getColumnIndex("location")),
                cursor.getString(cursor.getColumnIndex("item")),
                cursor.getInt(cursor.getColumnIndex("quantity")),
                cursor.getDouble(cursor.getColumnIndex("unitary_val"))
            );
            spendings.add(spent);
        }

        cursor.close();
        return spendings;
        /*
        Spent spent1 = new Spent(
                Calendar.getInstance().getTime(),
                "999 Street",
                "Test item 2",
                2,
                3.99
        );

        Spent spent2 = new Spent(
                Calendar.getInstance().getTime(),
                "777 Street",
                "Test item 2",
                5,
                8.99
        );

        List<Spent> spendings = new ArrayList<>();
        spendings.add(spent1);
        spendings.add(spent2);
        return spendings;
        */

    }

    @NonNull
    private ContentValues getSpentAsContentValues(Spent spent) {
        ContentValues content = new ContentValues();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String stringDate = simpleDateFormat.format(spent.getSpentDate());

        content.put("date", stringDate);
        content.put("location", spent.getLocation());
        content.put("item", spent.getItem());
        content.put("quantity", spent.getQuantity());
        content.put("unitary_val", spent.getUnitaryVal());
        return content;
    }

    public void insert(Spent spent) {
        ContentValues content = this.getSpentAsContentValues(spent);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("Spent", null, content);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Spent (id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, location TEXT, item TEXT, quantity INTEGER, unitary_val REAL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Spent";
        db.execSQL(sql);
        onCreate(db);
    }
}
