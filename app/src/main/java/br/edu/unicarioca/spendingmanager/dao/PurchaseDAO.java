package br.edu.unicarioca.spendingmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.wifi.aware.PublishConfig;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.edu.unicarioca.spendingmanager.models.Item;
import br.edu.unicarioca.spendingmanager.models.Purchase;

public class PurchaseDAO extends SQLiteOpenHelper {
    public static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final String ID_PURCHASE_FIELD_NAME = "id_purchase";
    private static final String DATE_PURCHASE_FIELD_NAME = "date";
    private static final String LOCATION_PURCHASE_FIELD_NAME = "location";
    private static final String ID_ITEM_FIELD_NAME = "id_item";
    private static final String NAME_ITEM_FIELD_NAME = "name";
    private static final String QUANTITY_ITEM_FIELD_NAME = "quantity";
    private static final String UNITARY_VAL_ITEM_FIELD_NAME = "unitary_val";
    private static final String PURCHASE_FK_ITEM_FIELD_NAME = "purchase";
    private static final String PURCHASE_ITEM_JOIN_QUERY =  "SELECT " +
                                                            ID_PURCHASE_FIELD_NAME + ", " +
                                                            DATE_PURCHASE_FIELD_NAME + ", " +
                                                            LOCATION_PURCHASE_FIELD_NAME + ", " +
                                                            ID_ITEM_FIELD_NAME + ", " +
                                                            NAME_ITEM_FIELD_NAME + ", " +
                                                            QUANTITY_ITEM_FIELD_NAME + ", " +
                                                            UNITARY_VAL_ITEM_FIELD_NAME +
                                                            " FROM Purchase as p LEFT JOIN Item as i on p.id_purchase = i.purchase";
    private static final String PURCHASE_LIST_QUERY = "SELECT * FROM Purchase";
    private static final String CREATE_TABLE_PURCHASE = "CREATE TABLE Purchase (" +
                                                        ID_PURCHASE_FIELD_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                        DATE_PURCHASE_FIELD_NAME + " TEXT," +
                                                        LOCATION_PURCHASE_FIELD_NAME + " TEXT)";
    private static final String CREATE_TABLE_ITEM = "CREATE TABLE Item (" +
                                                    ID_ITEM_FIELD_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                    NAME_ITEM_FIELD_NAME + " TEXT, " +
                                                    QUANTITY_ITEM_FIELD_NAME + " INTEGER, " +
                                                    UNITARY_VAL_ITEM_FIELD_NAME + " REAL, " +
                                                    PURCHASE_FK_ITEM_FIELD_NAME + " INTEGER, " +
                                                    "FOREIGN KEY (" + PURCHASE_FK_ITEM_FIELD_NAME + ") REFERENCES Purchase(" + ID_PURCHASE_FIELD_NAME + "))";

    public PurchaseDAO(Context context) {
        super(context, "Spendings", null, 1);
    }

    public List<Purchase> list() {
        List<Purchase> purchases = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(PURCHASE_LIST_QUERY, null);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        while(cursor.moveToNext()) {
            String dateString = cursor.getString(cursor.getColumnIndex(DATE_PURCHASE_FIELD_NAME));
            Date date;
            try {
                date = simpleDateFormat.parse(dateString);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Purchase purchase = new Purchase(
                cursor.getLong(cursor.getColumnIndex(ID_PURCHASE_FIELD_NAME)),
                date,
                cursor.getString(cursor.getColumnIndex(LOCATION_PURCHASE_FIELD_NAME))
            );
            purchases.add(purchase);
        }

        cursor.close();
        return purchases;
    }

    public Purchase getPurchaseById(Long id) {
        SQLiteDatabase db = getReadableDatabase();
        String purchase_get_query = PURCHASE_ITEM_JOIN_QUERY + " where " + ID_PURCHASE_FIELD_NAME + "=" + id;
        Cursor cursor = db.rawQuery(purchase_get_query, null);
        Purchase purchase = null;

        while(cursor.moveToNext()) {
            if(purchase == null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
                String dateString = cursor.getString(cursor.getColumnIndex(DATE_PURCHASE_FIELD_NAME));
                Date date;
                try {
                    date = simpleDateFormat.parse(dateString);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                purchase = new Purchase(
                        cursor.getLong(cursor.getColumnIndex(ID_PURCHASE_FIELD_NAME)),
                        date,
                        cursor.getString(cursor.getColumnIndex(LOCATION_PURCHASE_FIELD_NAME))
                );
            }

            if (cursor.getLong(cursor.getColumnIndex(ID_ITEM_FIELD_NAME)) != 0) {
                Item item = new Item(
                        cursor.getLong(cursor.getColumnIndex(ID_ITEM_FIELD_NAME)),
                        cursor.getString(cursor.getColumnIndex(NAME_ITEM_FIELD_NAME)),
                        cursor.getInt(cursor.getColumnIndex(QUANTITY_ITEM_FIELD_NAME)),
                        cursor.getDouble(cursor.getColumnIndex(UNITARY_VAL_ITEM_FIELD_NAME)),
                        purchase
                );
                purchase.addItem(item);
            }
        }
        cursor.close();
        return purchase;
    }

    @NonNull
    private ContentValues getSpentAsContentValues(Purchase purchase) {
        ContentValues content = new ContentValues();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        String stringDate = simpleDateFormat.format(purchase.getPurchaseDate());

        content.put(DATE_PURCHASE_FIELD_NAME, stringDate);
        content.put(LOCATION_PURCHASE_FIELD_NAME, purchase.getLocation());
        return content;
    }

    public void insert(Purchase purchase) {
        ContentValues content = this.getSpentAsContentValues(purchase);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("Purchase", null, content);
    }

    public void update(Purchase purchase) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = getSpentAsContentValues(purchase);
        String[] params = {purchase.getId().toString()};
        db.update("Purchase", content, "id_purchase = ?", params);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PURCHASE);
        db.execSQL(CREATE_TABLE_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropPurchaseTableSql = "DROP TABLE IF EXISTS Purchase";
        String dropItemTableSql = "DROP TABLE IF EXISTS Item";
        db.execSQL(dropPurchaseTableSql);
        db.execSQL(dropItemTableSql);
        onCreate(db);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}
