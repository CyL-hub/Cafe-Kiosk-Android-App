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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * StoreOrderActivity class. Manages the entire Store Orders menu.
 * @author Brian Chang, Chris Lam
 */
public class StoreOrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private StoreOrders storeOrder = SingletonContainers.getInstance().getStoreOrder();
    private Spinner orderSpinner;
    private ListView storeOrderLV;
    private Order orderSelected;
    private TextView totalTxt;
    private Button removeBtn;
    ArrayAdapter adapter, adapterLV;

    /**
     * Sets title and displays StoreOrderActivity view
     * @param savedInstanceState keeps track of activity state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_order);
        setTitle(R.string.store_order_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        totalTxt = (TextView) findViewById(R.id.totalTxt);
        removeBtn = (Button) findViewById(R.id.removeBtn);
        //Populate the spinner with Order Numbers
        orderSpinner = (Spinner)findViewById(R.id.orderSp);
        storeOrderLV = (ListView) findViewById(R.id.storeOrderLV);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,storeOrder.getOrderList());
        orderSpinner.setAdapter(adapter);
        //Grab default order(either first order placed or null if no orders placed yet)
        orderSelected = (Order) orderSpinner.getSelectedItem();
        //if default order is not null, populate the list view with first order's items
        if(orderSelected != null) {
            ArrayAdapter adapterLV = new ArrayAdapter(this, android.R.layout.simple_list_item_1, orderSelected.getList());
            storeOrderLV.setAdapter(adapterLV);
        }
        orderSpinner.setOnItemSelectedListener(this);
    }

    /**
     * Helper method to calculate the total pricing of the Order parameter.
     * @param order Order object.
     * @return total Float total price of current order.
     */
    private float calculateOrder(Order order) {
        float subtotal = 0;
        for (com.cs213p5.MenuItem var : order.getList()) {
            subtotal += var.getPrice();
        }
        return subtotal;
    }

    /**
     * Method to remove the current order selected in the ordersSpinner, from the StoreOrders object's Order List.
     * GUI is reset upon successful removal of the order.
     * Activates upon clicking the 'Cancel Order' button.
     * @param view current View
     */
    public void cancelOrder(View view) {
        totalTxt = (TextView) findViewById(R.id.totalTxt);
        if (storeOrder.getOrderList().isEmpty()) { // storeOrder is empty upon opening StoreOrderActivity
            AlertDialog.Builder emptyPlace_alertDialogBuilder = new AlertDialog.Builder(this);
            emptyPlace_alertDialogBuilder.setTitle(R.string.cancel_order_title);
            emptyPlace_alertDialogBuilder
                    .setMessage(R.string.cancel_empty_order)
                    .setCancelable(false)
                    .setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog emptyPlaceAlert = emptyPlace_alertDialogBuilder.create();
            emptyPlaceAlert.show();
            return;
        }
        AlertDialog.Builder cancelOrder_alertDialogBuilder = new AlertDialog.Builder(this);
        cancelOrder_alertDialogBuilder.setTitle(R.string.cancel_order_title);
        cancelOrder_alertDialogBuilder
                .setMessage(R.string.cancel_order_msg)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Order orderSelected = (Order) orderSpinner.getSelectedItem();
                        if(storeOrder.remove(orderSelected)) {
                            // reset the spinner
                            ArrayAdapter adapter = new ArrayAdapter(storeOrderLV.getContext(), android.R.layout.simple_list_item_1,storeOrder.getOrderList());
                            orderSpinner.setAdapter(adapter);
                            if(storeOrder.getOrderList().isEmpty()) { // storeOrder is empty after removing an order
                                storeOrderLV.setAdapter(null);
                                orderSelected = null;
                            }
                            else {
                                orderSelected = (Order) orderSpinner.getSelectedItem(); // reset the list view
                                adapterLV = new ArrayAdapter(storeOrderLV.getContext(), android.R.layout.simple_list_item_1, orderSelected.getList());
                                storeOrderLV.setAdapter(adapterLV);
                            }
                            if(orderSelected == null) {
                                totalTxt.setText(R.string._0_00);
                            }
                            else {
                                totalTxt.setText(String.format("$" + "%,.2f", calculateOrder(orderSelected)));
                            }
                            Toast.makeText(getApplicationContext(),R.string.order_canceled,Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),R.string.order_not_canceled,Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog cancelOrderAlert = cancelOrder_alertDialogBuilder.create();
        cancelOrderAlert.show();
    }

    /**
     * Manages the Order selection.
     * Updates StoreOrder menu accordingly.
     * @param parent the AdapterView where the selection happens
     * @param view current within the AdapterView clicked
     * @param position of the item selected, by index
     * @param id row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Order orderSelected = (Order) parent.getItemAtPosition(position);
        if(orderSelected != null) {
            ArrayAdapter adapterLV = new ArrayAdapter(this, android.R.layout.simple_list_item_1, orderSelected.getList());
            storeOrderLV.setAdapter(adapterLV);
        }
        totalTxt.setText(String.format("$" + "%,.2f", calculateOrder(orderSelected)));
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