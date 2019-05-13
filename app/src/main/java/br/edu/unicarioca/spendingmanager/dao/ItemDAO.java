package br.edu.unicarioca.spendingmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;

import br.edu.unicarioca.spendingmanager.models.Item;

public class ItemDAO extends SQLiteOpenHelper {
    public ItemDAO(Context context) {
        super(context, "Spendings", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPurchaseTableSql = "CREATE TABLE IF NOT EXISTS Purchase (id_purchase INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, location TEXT)";
        String createItemTableSql = "CREATE TABLE IF NOT EXISTS Item (id_item INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, quantity INTEGER, unitary_val REAL," +
                "purchase INTEGER," +
                "FOREIGN KEY (purchase) REFERENCES Purchase(id_purchase))";
        db.execSQL(createPurchaseTableSql);
        db.execSQL(createItemTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropPurchaseTableSql = "DROP TABLE IF EXISTS Purchase";
        String dropItemTableSql = "DROP TABLE IF EXISTS Item";
        db.execSQL(dropPurchaseTableSql);
        db.execSQL(dropItemTableSql);
        onCreate(db);
    }

    private ContentValues getItemAsContentValues(Item item) {
        ContentValues content = new ContentValues();
        content.put("name", item.getName());
        content.put("quantity", item.getQuantity());
        content.put("unitary_val", item.getUnitaryVal());
        content.put("purchase", item.getPurchase().getId());
        return content;
    }

    public void insert(Item item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = getItemAsContentValues(item);
        db.insert("Item", null, content);
    }

    public void update(Item item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = getItemAsContentValues(item);
        String[] params = {item.getId().toString()};
        db.update("Item", content, "id_item = ?", params);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}
