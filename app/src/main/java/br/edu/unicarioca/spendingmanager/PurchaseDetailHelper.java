package br.edu.unicarioca.spendingmanager;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.edu.unicarioca.spendingmanager.models.Item;
import br.edu.unicarioca.spendingmanager.models.Purchase;

public class PurchaseDetailHelper {
    private EditText purchaseDateField;
    private EditText purchaseLocationField;
    private ListView purchaseItemsList;
    private Purchase purchase;
    private Activity context;

    public PurchaseDetailHelper(PurchaseDetailActivity activity) {
        this.purchaseDateField = (EditText) activity.findViewById(R.id.edit_text_spent_date);
        this.purchaseLocationField = (EditText) activity.findViewById(R.id.edit_text_spent_location);
        this.purchaseItemsList = (ListView) activity.findViewById(R.id.list_view_items);
        purchase = new Purchase();
        context = activity;
    }

    public void fillFields(Purchase purchase) {
        this.purchaseDateField.setText(purchase.getPurchaseDateString());
        this.purchaseLocationField.setText(purchase.getLocation());

        List<Item> items = purchase.getItems();
        ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(context, android.R.layout.simple_list_item_1, items);
        purchaseItemsList.setAdapter(adapter);
        this.purchase = purchase;
    }


    public Purchase getPurchase() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = simpleDateFormat.parse(this.purchaseDateField.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.purchase.setPurchaseDate(date);
        this.purchase.setLocation(this.purchaseLocationField.getText().toString());

        return this.purchase;
    }
}
