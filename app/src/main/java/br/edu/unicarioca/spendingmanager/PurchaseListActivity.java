package br.edu.unicarioca.spendingmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.edu.unicarioca.spendingmanager.dao.PurchaseDAO;
import br.edu.unicarioca.spendingmanager.models.Purchase;

public class PurchaseListActivity extends AppCompatActivity {
    private ListView searcResultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);

        searcResultList = (ListView) findViewById(R.id.list_view_psearch_result);

        searcResultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Purchase purchase = (Purchase) searcResultList.getItemAtPosition(position);
                Intent intent = new Intent(PurchaseListActivity.this, PurchaseDetailActivity.class);
                intent.putExtra("purchase", purchase);
                startActivity(intent);
            }

        } );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String initDate = (String) intent.getSerializableExtra("initDate");
        String finDate = (String) intent.getSerializableExtra("finDate");
        String local = (String) intent.getSerializableExtra("local");

        PurchaseDAO dao = new PurchaseDAO(this);
        List<Purchase> purchases;

        if (!initDate.equals("") && !finDate.equals("") && !local.equals(""))
            purchases = dao.listByLocalAndDateRange(initDate, finDate, local);
        else if (!initDate.equals("") && !finDate.equals(""))
            purchases = dao.listByDateRange(initDate, finDate);
        else
            purchases = dao.listByLocal(local);
        dao.close();

        Log.d("PurchaseList", purchases.toString());

        ArrayAdapter<Purchase> adapter = new ArrayAdapter<Purchase>(this, android.R.layout.simple_list_item_1, purchases);
        searcResultList.setAdapter(adapter);
    }
}
