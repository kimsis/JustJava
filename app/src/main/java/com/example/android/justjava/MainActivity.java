package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int pricePerCoffee = 5;
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        EditText nameField = (EditText) findViewById(R.id.name_edit_text_view);
        String name = nameField.getText().toString();
        int price = calculatePrice(pricePerCoffee, hasWhippedCream, hasChocolate);
        String orderSummary = createOrderSummary(price, name, hasWhippedCream, hasChocolate);
        composeEmail( getString(R.string.subject, name), orderSummary);
        quantity = 1;
        displayQuantity(quantity);
    }

    private int calculatePrice(int pricePerCoffee, boolean hasWhippedCream, boolean hasChocolate)
    {
        if(hasWhippedCream) pricePerCoffee += 1;
        if(hasChocolate) pricePerCoffee += 2;
        return pricePerCoffee * quantity;
    }

    public String createOrderSummary(int price, String name, boolean hasWhippedCream, boolean hasChocolate){
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.add_whipped_cream, hasWhippedCream);
        priceMessage += "\n" + getString(R.string.add_chocolate, hasChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.total, price);
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    public void composeEmail(String subject, String orderSummary) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void increment(View view) {
        if (quantity == 100)
        {
            Toast.makeText(this, "You cannot have more than 100 coffees, it is dangerous", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity +1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1)
        {
            Toast.makeText(this, "You cannot have less than 1 coffee, it is illogical", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_number);
        quantityTextView.setText(numberOfCoffees);
    }

}