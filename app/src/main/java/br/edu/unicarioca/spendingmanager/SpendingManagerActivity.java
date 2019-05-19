package br.edu.unicarioca.spendingmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import br.edu.unicarioca.spendingmanager.dao.PurchaseDAO;
import br.edu.unicarioca.spendingmanager.models.Purchase;

public class SpendingManagerActivity extends AppCompatActivity {
    private ListView purchaseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending_manager);

        purchaseList = (ListView) findViewById(R.id.list_view_purchases);

        /*
        ListView implements AdapterView. AdapterView has the setOnItemClickListener()
        method. This method sets a Listener to the AdapterView to handle all click event
        to each item in this AdapterView
        */
        purchaseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // Documentation of this method https://developer.android.com/reference/android/widget/AdapterView.OnItemClickListener
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Purchase purchase = (Purchase) purchaseList.getItemAtPosition(position);
                Intent intent = new Intent(SpendingManagerActivity.this, PurchaseDetailActivity.class);
                intent.putExtra("purchase", purchase);
                startActivity(intent);
            }

        } );

        Button newPurchaseButton = (Button) findViewById(R.id.button_new_purchase);

            newPurchaseButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpendingManagerActivity.this, PurchaseDetailActivity.class);
                startActivity(intent);
            }
        } );

        Button searchPurchaseButton = (Button) findViewById(R.id.button_search_purchase);
        searchPurchaseButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpendingManagerActivity.this, SearchPurchaseActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        loadPurchases();
    }

    private void loadPurchases() {
        PurchaseDAO dao = new PurchaseDAO(this);
        List<Purchase> purchases = dao.list();
        dao.close();

        ArrayAdapter<Purchase> adapter = new ArrayAdapter<Purchase>(this, android.R.layout.simple_list_item_1, purchases);
        purchaseList.setAdapter(adapter);
    }
}
