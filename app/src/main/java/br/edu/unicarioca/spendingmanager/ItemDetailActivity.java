package br.edu.unicarioca.spendingmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.unicarioca.spendingmanager.dao.ItemDAO;
import br.edu.unicarioca.spendingmanager.models.Item;
import br.edu.unicarioca.spendingmanager.models.Purchase;

public class ItemDetailActivity extends AppCompatActivity {
    private Purchase purchase;
    private Item item;
    private ItemDetailHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        Button saveItemButton = (Button) findViewById(R.id.button_save_item);
        saveItemButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveItem();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        purchase = (Purchase) intent.getSerializableExtra("purchase");
        item = (Item) intent.getSerializableExtra("item");
        helper = new ItemDetailHelper(this, purchase);

        TextView textViewPurchase = (TextView) findViewById(R.id.textview_purchase);
        String formText = "";
        if(purchase != null) {
            formText = purchase.toString();
        } else {
            formText = item.getPurchase().toString();
            helper.fillFields(item);
        }


        textViewPurchase.setText(formText);
    }

    private void saveItem() {
        Item item = helper.getItem();
        ItemDAO dao = new ItemDAO(this);

        if (purchase != null)
            item.setPurchase(purchase);

        if (item.getId() == null)
            dao.insert(item);
        else
            dao.update(item);

        dao.close();
        Toast.makeText(ItemDetailActivity.this, "Item " + item.getName() + " salvo!", Toast.LENGTH_LONG).show();
        finish();
    }
}
