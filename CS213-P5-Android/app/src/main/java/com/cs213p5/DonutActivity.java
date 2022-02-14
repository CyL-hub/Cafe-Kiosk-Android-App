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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * DonutActivity class. Manages the Donut ordering menu.
 * @author Brian Chang, Chris Lam
 */
public class DonutActivity extends AppCompatActivity {

    private ListView donutLV;
    private ArrayList<com.cs213p5.MenuItem> myDonuts;
    private TextView subtotalTV;
    private float subtotal;
    private com.cs213p5.MenuItem removeSelected;
    private Order myOrder = SingletonContainers.getInstance().getMyOrder();

    /**
     * Sets title and displays DonutActivity view
     * @param savedInstanceState keeps track of activity state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donut);
        setTitle(R.string.donut_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        donutLV = (ListView) findViewById(R.id.donutLV);
        myDonuts = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, myDonuts);
        donutLV.setAdapter(adapter);
        donutLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                removeSelected = (com.cs213p5.MenuItem) parent.getItemAtPosition(position);
            }
        });
        subtotalTV = (TextView) findViewById(R.id.subtotalTV);
        subtotalTV.setText(R.string._0_00);
    }

    /**
     * Functionality for clicking Add button
     * Adds a donut to list of donuts.
     * @param view current view
     */
    public void addClick(View view) {
        //get quantity selected
        Spinner quantitySpinner = (Spinner)findViewById(R.id.quantityDonSp);
        int quantity = Integer.parseInt((quantitySpinner.getSelectedItem().toString()));

        //get Donut type and flavor selected
        Spinner typeSpinner = (Spinner)findViewById(R.id.typeSp);
        String selected = typeSpinner.getSelectedItem().toString();
        String myType;
        String myFlavor;
        if(selected.equals(getString(R.string.chocolate_select))) {
            myType = getString(R.string.cake);
            myFlavor = getString(R.string.chocolate);
            myDonuts.add(new CakeDonut(myFlavor,quantity));
        }
        else if(selected.equals(getString(R.string.vanilla_select))) {
            myType = getString(R.string.cake);
            myFlavor = getString(R.string.vanilla);
            myDonuts.add(new CakeDonut(myFlavor,quantity));
        }
        else if(selected.equals(getString(R.string.strawberry_select))) {
            myType = getString(R.string.cake);
            myFlavor = getString(R.string.strawberry);
            myDonuts.add(new CakeDonut(myFlavor,quantity));
        }
        else if(selected.equals(getString(R.string.chamomile_select))) {
            myType = getString(R.string.yeast);
            myFlavor = getString(R.string.chamomile);
            myDonuts.add(new YeastDonut(myFlavor,quantity));
        }
        else if(selected.equals(getString(R.string.earl_grey_select))) {
            myType = getString(R.string.yeast);
            myFlavor = getString(R.string.earl_grey);
            myDonuts.add(new YeastDonut(myFlavor,quantity));
        }
        else if(selected.equals(getString(R.string.oolong_select))) {
            myType = getString(R.string.yeast);
            myFlavor = getString(R.string.oolong);
            myDonuts.add(new YeastDonut(myFlavor,quantity));
        }
        else if(selected.equals(getString(R.string.acai_select))) {
            myType = getString(R.string.donut_hole);
            myFlavor = getString(R.string.acai);
            myDonuts.add(new DonutHole(myFlavor,quantity));
        }
        else if(selected.equals(getString(R.string.apple_select))) {
            myType = getString(R.string.donut_hole);
            myFlavor = getString(R.string.apple);
            myDonuts.add(new DonutHole(myFlavor,quantity));
        }
        else if(selected.equals(getString(R.string.apricot_select))) {
            myType = getString(R.string.donut_hole);
            myFlavor = getString(R.string.apricot);
            myDonuts.add(new DonutHole(myFlavor,quantity));
        }

        //Refresh DonutLV
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, myDonuts);
        donutLV.setAdapter(adapter);

        //Update Price
        subtotal += myDonuts.get(myDonuts.size() - 1).getPrice();
        subtotalTV.setText(String.format("$" + "%.2f",subtotal));
    }

    /**
     * Functionality for clicking Remove button
     * Removes a donut from list of donuts.
     * @param view current view
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
                        if( myDonuts.remove(removeSelected)) {
                            //Refresh DonutLV
                            ArrayAdapter adapter = new ArrayAdapter(donutLV.getContext(), android.R.layout.simple_list_item_single_choice, myDonuts);
                            donutLV.setAdapter(adapter);
                            //Update Price
                            subtotal -= removeSelected.getPrice();
                            subtotalTV.setText(String.format("$" + "%.2f", subtotal));
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
     * Functionality for clicking Add to Order button
     * Adds a list of donuts to the current order.
     * @param view current view
     */
    public void addToOrderClick(View view) {
        if(myDonuts.isEmpty()) {
            AlertDialog.Builder emptyAdd_alertDialogBuilder = new AlertDialog.Builder(this);
            emptyAdd_alertDialogBuilder.setTitle(R.string.add_donuts_title);
            emptyAdd_alertDialogBuilder
                    .setMessage(R.string.add_empty_donuts)
                    .setCancelable(false)
                    .setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                    });
            AlertDialog emptyAddAlert = emptyAdd_alertDialogBuilder.create();
            emptyAddAlert.show();
            return;
        }

        AlertDialog.Builder addDonuts_alertDialogBuilder = new AlertDialog.Builder(this);
        addDonuts_alertDialogBuilder.setTitle(R.string.add_donuts_title);
        addDonuts_alertDialogBuilder
                .setMessage(R.string.add_donuts_msg)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        for(com.cs213p5.MenuItem item: myDonuts) {
                            myOrder.add(item);
                        }
                        myDonuts.clear();
                        //Refresh DonutLV
                        ArrayAdapter adapter = new ArrayAdapter(donutLV.getContext(), android.R.layout.simple_list_item_single_choice, myDonuts);
                        donutLV.setAdapter(adapter);
                        //Update Price
                        subtotal = 0;
                        subtotalTV.setText(R.string._0_00);
                        Toast.makeText(getApplicationContext(),R.string.order_added,Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),R.string.donuts_not_added,Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog addDonutAlert = addDonuts_alertDialogBuilder.create();
        addDonutAlert.show();
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