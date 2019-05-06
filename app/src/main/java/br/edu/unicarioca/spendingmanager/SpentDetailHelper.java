package br.edu.unicarioca.spendingmanager;

import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.edu.unicarioca.spendingmanager.models.Spent;

public class SpentDetailHelper {
    private EditText spentDateField;
    private EditText spentLocationField;
    private EditText spentItemField;
    private EditText spentQuantityField;
    private EditText spentUnitaryValField;

    public SpentDetailHelper(SpentDetailActivity activity) {
        this.spentDateField = (EditText) activity.findViewById(R.id.edit_text_spent_date);
        this.spentLocationField = (EditText) activity.findViewById(R.id.edit_text_spent_location);
        this.spentItemField = (EditText) activity.findViewById(R.id.edit_text_spent_item);
        this.spentQuantityField = (EditText) activity.findViewById(R.id.edit_text_spent_quantity);
        this.spentUnitaryValField = (EditText) activity.findViewById(R.id.edit_view_spent_unitary_val);
    }

    public void fillFields(Spent spent) {
        this.spentDateField.setText(spent.getSpentDate().toString());
        this.spentLocationField.setText(spent.getLocation());
        this.spentItemField.setText(String.valueOf(spent.getQuantity()));
        this.spentQuantityField.setText(String.valueOf(spent.getQuantity()));
        this.spentUnitaryValField.setText(String.valueOf(spent.getUnitaryVal()));
    }


    public Spent getSpent() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = simpleDateFormat.parse(this.spentDateField.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Spent(
            date,
            this.spentLocationField.getText().toString(),
            this.spentItemField.getText().toString(),
            Integer.parseInt(this.spentQuantityField.getText().toString()),
            Double.valueOf(this.spentUnitaryValField.getText().toString())
        );
    }
}
