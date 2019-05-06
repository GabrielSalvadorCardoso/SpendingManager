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

import br.edu.unicarioca.spendingmanager.dao.SpentDAO;
import br.edu.unicarioca.spendingmanager.models.Spent;

public class SpendingManagerActivity extends AppCompatActivity {
    private ListView spendingsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending_manager);

        spendingsList = (ListView) findViewById(R.id.list_view_spendings);

        /*
        ListView implements AdapterView. AdapterView has the setOnItemClickListener()
        method. This method sets a Listener to the AdapterView to handle all click event
        to each item in this AdapterView
        */
        spendingsList.setOnItemClickListener( new AdapterView.OnItemClickListener() {

            // Documentation of this method https://developer.android.com/reference/android/widget/AdapterView.OnItemClickListener
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Spent spent = (Spent) spendingsList.getItemAtPosition(position);
                Intent intent = new Intent(SpendingManagerActivity.this, SpentDetailActivity.class);
                intent.putExtra("spent", spent);
                startActivity(intent);
            }

        } );

        Button newSpentButton = (Button) findViewById(R.id.button_new_spent);

        newSpentButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpendingManagerActivity.this, SpentDetailActivity.class);
                startActivity(intent);
            }
        } );
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadSpendings();
    }

    private void loadSpendings() {
        SpentDAO dao = new SpentDAO(this);
        //SpentDAO dao = new SpentDAO(this);
        List<Spent> spendings = dao.list();
        dao.close();

        ArrayAdapter<Spent> adapter = new ArrayAdapter<Spent>(this, android.R.layout.simple_list_item_1, spendings);
        spendingsList.setAdapter(adapter);
    }
}
