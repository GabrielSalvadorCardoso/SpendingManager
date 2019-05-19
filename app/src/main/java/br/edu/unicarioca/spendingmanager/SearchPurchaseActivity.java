package br.edu.unicarioca.spendingmanager;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.edu.unicarioca.spendingmanager.dao.PurchaseDAO;
import br.edu.unicarioca.spendingmanager.models.Purchase;

public class SearchPurchaseActivity extends AppCompatActivity {
    private EditText initialDateEditText;
    private EditText finalDateEditText;
    private EditText localEditText;
    private CheckBox searchByDateCheckBox;
    private CheckBox searchByLocationCheckBox;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_search);

        searchByDateCheckBox = (CheckBox) findViewById(R.id.check_box_search_by_date);
        searchByLocationCheckBox = (CheckBox) findViewById(R.id.check_box_search_by_location);
        initialDateEditText = (EditText) findViewById(R.id.edit_text_psearch_initial_date);
        finalDateEditText = (EditText) findViewById(R.id.edit_text_psearch_final_date);
        localEditText = (EditText) findViewById(R.id.edit_text_psearch_local);
        sendButton = (Button) findViewById(R.id.button_psearch_send);

        sendButton.setOnClickListener( new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
            String initDate = initialDateEditText.getText().toString();

            if( !initDate.equals("")) {
                String[] initDateArr = initDate.split("/");
                String[] reverseInitDateArr = {initDateArr[2], initDateArr[1], initDateArr[0]};
                initDate = String.join("/", reverseInitDateArr);
            }

            String finDate = finalDateEditText.getText().toString();

            if( !finDate.equals("") ) {
                String[] finDateArr = finDate.split("/");
                String[] reverseFinDateArr = {finDateArr[2], finDateArr[1], finDateArr[0]};
                finDate = String.join("/", reverseFinDateArr);
            }

            String local = localEditText.getText().toString();

            Intent intent = new Intent(SearchPurchaseActivity.this, PurchaseListActivity.class);
            intent.putExtra("initDate", initDate);
            intent.putExtra("finDate", finDate);
            intent.putExtra("local", local);
            startActivity(intent);
            }
        });


    }

    public void onSearchByDateClicked(View view) {
        if ( !((CheckBox) view).isChecked() ) {
            initialDateEditText.setText("");
            finalDateEditText.setText("");
            initialDateEditText.setEnabled(false);
            finalDateEditText.setEnabled(false);
            initialDateEditText.clearFocus();
            finalDateEditText.clearFocus();
        } else {
            initialDateEditText.setEnabled(true);
            finalDateEditText.setEnabled(true);
        }
        toggleSendButton();
    }

    public void onSearchByLocationClicked(View view) {
        if ( !((CheckBox) view).isChecked() ) {
            localEditText.setText("");
            localEditText.setEnabled(false);
            localEditText.clearFocus();
        } else {
            localEditText.setEnabled(true);
        }
        toggleSendButton();
    }

    private void toggleSendButton() {
        if (!searchByDateCheckBox.isEnabled() && !searchByLocationCheckBox.isEnabled()) {
            sendButton.setEnabled(false);
        } else {
            sendButton.setEnabled(true);
        }
    }
}
