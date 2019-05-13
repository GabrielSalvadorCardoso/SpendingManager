package br.edu.unicarioca.spendingmanager;

import android.widget.EditText;
import android.widget.TextView;

import br.edu.unicarioca.spendingmanager.models.Item;
import br.edu.unicarioca.spendingmanager.models.Purchase;

class ItemDetailHelper {
    private ItemDetailActivity context;
    private EditText itemNameField;
    private EditText itemQuantityField;
    private EditText itemUnitaryValField;
    private Item item;

    public ItemDetailHelper(ItemDetailActivity activity, Purchase purchase) {
        this.itemNameField = activity.findViewById(R.id.edit_text_item_name);
        this.itemQuantityField = activity.findViewById(R.id.edit_text_item_quantity);
        this.itemUnitaryValField = activity.findViewById(R.id.edit_text_item_unitary_val);
        context = activity;
        item = new Item();
        if(purchase != null)
            item.setPurchase(purchase);
    }

    public Item getItem() {
        this.item.setName(this.itemNameField.getText().toString());
        this.item.setQuantity(Integer.parseInt(this.itemQuantityField.getText().toString()));
        this.item.setUnitaryVal(Double.valueOf(this.itemUnitaryValField.getText().toString()));
        return this.item;
    }

    public void fillFields(Item item) {
        this.itemNameField.setText(item.getName());
        this.itemQuantityField.setText((String.valueOf(item.getQuantity())));
        this.itemUnitaryValField.setText((item.getUnitaryVal().toString()));
        this.item = item;
    }
}
