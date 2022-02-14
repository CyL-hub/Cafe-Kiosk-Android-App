package com.cs213p5;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * CoffeeActivity class. Manages the Coffee ordering menu.
 * @author Brian Chang, Chris Lam
 */
public class CoffeeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private CheckBox creamCB, syrupCB, caramelCB, milkCB, whippedCreamCB;
    private Spinner sizeSp, quantityCofSp;
    private TextView subtotalTV;
    private Button orderBtn;
    private Coffee myCoffee;
    private float subtotal;
    private Order myOrder = SingletonContainers.getInstance().getMyOrder();
    private static final int DEFAULT_QUANTITY = 1;

    /**
     * Sets title and displays CoffeeActivity view
     * @param savedInstanceState keeps track of activity state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);
        setTitle(R.string.coffee_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        creamCB = (CheckBox) findViewById(R.id.creamCB);
        syrupCB = (CheckBox) findViewById(R.id.syrupCB);
        caramelCB = (CheckBox) findViewById(R.id.caramelCB);
        milkCB = (CheckBox) findViewById(R.id.milkCB);
        whippedCreamCB = (CheckBox) findViewById(R.id.whippedCreamCB);
        sizeSp = (Spinner) findViewById(R.id.sizeSp);
        quantityCofSp = (Spinner) findViewById(R.id.quantityCofSp);
        subtotalTV = (TextView) findViewById(R.id.subtotalTV);

        myCoffee = new Coffee(getString(R.string.Short), DEFAULT_QUANTITY);
        updateSubtotal();

        setUpCB(creamCB, getString(R.string.cream).toLowerCase());
        setUpCB(syrupCB, getString(R.string.syrup).toLowerCase());
        setUpCB(caramelCB, getString(R.string.caramel).toLowerCase());
        setUpCB(milkCB, getString(R.string.milk).toLowerCase());
        setUpCB(whippedCreamCB, getString(R.string.whipped_cream).toLowerCase());

        sizeSp.setOnItemSelectedListener(this);
        quantityCofSp.setOnItemSelectedListener(this);

        orderBtn = (Button) findViewById(R.id.orderBtn);
        orderBtn.setOnClickListener(this::order);
    }

    /**
     * Helper method for setting up OnClick action for check boxes.
     * @param box CheckBox to add functionality
     * @param addIn current AddIn selected
     */
    private void setUpCB(CheckBox box, String addIn) {
        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAddIn(addIn, box.isChecked());
            }
        });
    }

    /**
     * Helper method to update the subtotalTF with the current subtotal after an event action.
     */
    private void updateSubtotal() {
        myCoffee.itemPrice(); // calculates the price
        subtotal = myCoffee.getPrice();
        subtotalTV.setText(String.format("$" + "%,.2f", subtotal));
    }

    /**
     * Helper method to modified the myCoffee Coffee object's addInList array.
     * @param addIn String name of add in condiment.
     * @param add boolean True if checkbox is selected, False otherwise.
     */
    private void updateAddIn(String addIn, boolean add) {
        if(add) myCoffee.add(new AddIn(addIn));
        else myCoffee.remove(new AddIn(addIn));
        updateSubtotal();
    }

    /**
     * Manages the Size and Quantity selection.
     * Updates price and myCoffee accordingly.
     * @param parent the AdapterView where the selection happens
     * @param view current within the AdapterView clicked
     * @param position of the item selected, by index
     * @param id row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sizeSp:
                String size = (String) parent.getItemAtPosition(position);
                myCoffee.setSize(size.toLowerCase());
                break;
            case R.id.quantityCofSp:
                int quantity = Integer.parseInt((String) parent.getItemAtPosition(position));
                myCoffee.setQuantity(quantity);
        }
        updateSubtotal();
    }

    /**
     * Manages onClick Order button event.
     * Adds coffee to current order and resets the coffee menu.
     * @param view current View
     */
    public void order(View view) {
        AlertDialog.Builder addCoffee_alertDialogBuilder = new AlertDialog.Builder(this);
        addCoffee_alertDialogBuilder.setTitle(R.string.add_coffee_title);
        addCoffee_alertDialogBuilder
                .setMessage(R.string.add_coffee_msg)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myOrder.add(myCoffee);
                        // reset interface
                        sizeSp = (Spinner) findViewById(R.id.sizeSp);
                        quantityCofSp = (Spinner) findViewById((R.id.quantityCofSp));
                        ArrayAdapter sizeAdapter = ArrayAdapter.createFromResource(sizeSp.getContext(), R.array.coffee_size_array, android.R.layout.simple_spinner_dropdown_item);
                        ArrayAdapter quantityAdapter = ArrayAdapter.createFromResource(quantityCofSp.getContext(), R.array.quantity_array, android.R.layout.simple_spinner_dropdown_item);
                        sizeSp.setAdapter(sizeAdapter);
                        quantityCofSp.setAdapter(quantityAdapter);
                        myCoffee = new Coffee(getString(R.string.Short), DEFAULT_QUANTITY);
                        updateSubtotal();
                        creamCB.setChecked(false);
                        syrupCB.setChecked(false);
                        caramelCB.setChecked(false);
                        milkCB.setChecked(false);
                        whippedCreamCB.setChecked(false);
                        // output confirmation
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, R.string.order_added, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                })
                .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),R.string.coffee_not_added,Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog addCoffeeAlert = addCoffee_alertDialogBuilder.create();
        addCoffeeAlert.show();
    }

    /**
     * must implement AdapterView.OnItemSelectedListener class
     * @param parent the AdapterView where the selection happens
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Supports returning to MainActivity.
     * @param item previously created MenuItem
     */
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}