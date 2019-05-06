package br.edu.unicarioca.spendingmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import br.edu.unicarioca.spendingmanager.dao.SpentDAO;
import br.edu.unicarioca.spendingmanager.models.Spent;

public class SpentDetailActivity extends AppCompatActivity {
    private SpentDetailHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spent_detail);

        helper = new SpentDetailHelper(this);

        Intent intent = getIntent();
        Spent spent = (Spent) intent.getSerializableExtra("spent");

        if (spent != null)
            helper.fillFields(spent);
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
                Spent spent = helper.getSpent();
                SpentDAO dao = new SpentDAO(this);

                if (spent.getId() == null)
                    dao.insert(spent);
                dao.close();
                Toast.makeText(SpentDetailActivity.this, "Compra do item " + spent.getItem() + " registrado", Toast.LENGTH_LONG).show();
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
