package com.cs213p5;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * OrderActivity class. Manages the current Order menu.
 * @author Brian Chang, Chris Lam
 */
public class OrderActivity extends AppCompatActivity {

    private static final double TAXRATE = 0.06625, TAXTOTAL = 1.06625;
    private Order myOrder = SingletonContainers.getInstance().getMyOrder();
    private StoreOrders storeOrder = SingletonContainers.getInstance().getStoreOrder();
    private ListView orderLV;
    private TextView subtotalTV;
    private TextView salesTaxTV;
    private TextView totalTV;
    private com.cs213p5.MenuItem removeSelected;

    /**
     * Sets title and displays OrderActivity view
     * @param savedInstanceState keeps track of activity state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setTitle(R.string.order_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        orderLV = (ListView) findViewById(R.id.orderLV);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.simple_list_item_single_choice_resize, myOrder.getList());
        orderLV.setAdapter(adapter);
        orderLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                removeSelected = (com.cs213p5.MenuItem) parent.getItemAtPosition(position);
            }
        });
        subtotalTV = (TextView) findViewById(R.id.subtotalTV);
        salesTaxTV = (TextView) findViewById(R.id.salesTaxTxt);
        totalTV = (TextView) findViewById(R.id.totalTxt);
        subtotalTV.setText(R.string._0_00);
        salesTaxTV.setText(R.string._0_00);
        totalTV.setText(R.string._0_00);
        if(!myOrder.getList().isEmpty()){
            refresh();
        }
    }

    /**
     * Helper method to calculate the total price of the order.
     */
    private float calculateOrder() {
        float total = 0;
        for (com.cs213p5.MenuItem var : myOrder.getList()) {
            total += var.getPrice();
        }
        return total;
    }

    /**
     * Helper method to refresh the Order menu upon user-interacted changes.
     */
    private void refresh() {
        //Refresh DonutLV
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.simple_list_item_single_choice_resize, myOrder.getList());
        orderLV.setAdapter(adapter);
        //Update Price
        subtotalTV.setText(String.format("$" + "%.2f",calculateOrder()));
        salesTaxTV.setText(String.format("$" + "%.2f",calculateOrder()*TAXRATE));
        totalTV.setText(String.format("$" + "%.2f",calculateOrder()*TAXTOTAL));
    }

    /**
     * Removes a MenuItem from the current order, upon clicking Remove button.
     * Updates the ListView accordingly.
     * @param view current View
     */
    public void removeClick(View view) {

        if(removeSelected == null) {
            Toast.makeText(getApplicationContext(),R.string.no_selection,Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder cancelItem_alertDialogBuilder = new AlertDialog.Builder(this);
        cancelItem_alertDialogBuilder.setTitle(R.string.remove_item_title);
        cancelItem_alertDialogBuilder
                .setMessage(R.string.remove_item_msg)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if( myOrder.getList().remove(removeSelected)) {
                            refresh();
                            //reset listview selection
                            removeSelected = null;
                            Toast.makeText(getApplicationContext(),R.string.item_removed,Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),R.string.item_not_removed,Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog cancelItemAlert = cancelItem_alertDialogBuilder.create();
        cancelItemAlert.show();
    }

    /**
     * Adds current order to StoreOrder, upon clicking Add To Order button.
     * Resets the Order menu.
     * @param view current View
     */
    public void placeOrderClick(View view) {
        if(myOrder.getList().isEmpty()) {
            AlertDialog.Builder emptyPlace_alertDialogBuilder = new AlertDialog.Builder(this);
            emptyPlace_alertDialogBuilder.setTitle(R.string.place_order_title);
            emptyPlace_alertDialogBuilder
                    .setMessage(R.string.place_empty_order)
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

        AlertDialog.Builder placeOrder_alertDialogBuilder = new AlertDialog.Builder(this);
        placeOrder_alertDialogBuilder.setTitle(R.string.place_order_title);
        placeOrder_alertDialogBuilder
                .setMessage(R.string.place_order_msg)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (storeOrder.add(myOrder)) {
                            myOrder = SingletonContainers.getInstance().setMyOrder();
                            refresh();
                            Toast.makeText(getApplicationContext(),R.string.order_placed,Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),R.string.order_not_placed,Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog placeOrderAlert = placeOrder_alertDialogBuilder.create();
        placeOrderAlert.show();


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