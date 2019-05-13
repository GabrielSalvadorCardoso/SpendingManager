package br.edu.unicarioca.spendingmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import br.edu.unicarioca.spendingmanager.dao.PurchaseDAO;
import br.edu.unicarioca.spendingmanager.models.Item;
import br.edu.unicarioca.spendingmanager.models.Purchase;

public class PurchaseDetailActivity extends AppCompatActivity {
    private PurchaseDetailHelper helper;
    private Button buttonNewItem;
    private Purchase purchase;
    private ListView itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_detail);

        buttonNewItem = (Button) findViewById(R.id.button_new_item);
        buttonNewItem.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PurchaseDetailActivity.this, ItemDetailActivity.class);
                intent.putExtra("purchase", purchase);
                startActivity(intent);
            }
        } );

        itemsList = (ListView) findViewById(R.id.list_view_items);
        itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) itemsList.getItemAtPosition(position);
                Intent intent = new Intent(PurchaseDetailActivity.this, ItemDetailActivity.class);
                intent.putExtra("item", item);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        helper = new PurchaseDetailHelper(this);

        Intent intent = getIntent();
        purchase = (Purchase) intent.getSerializableExtra("purchase");

        if (purchase != null) {
            purchase = new PurchaseDAO(this).getPurchaseById(purchase.getId());
            helper.fillFields(purchase);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_spent, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_create_spent:
                Purchase purchase = helper.getPurchase();
                PurchaseDAO dao = new PurchaseDAO(this);

                if (purchase.getId() == null)
                    dao.insert(purchase);
                else
                    dao.update(purchase);

                dao.close();
                Toast.makeText(PurchaseDetailActivity.this, "Compra em " + purchase.getLocation() + " registrado", Toast.LENGTH_LONG).show();
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
