package com.cs213p5;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * MainActivity class. Displays the main navigation menu for RU cafe.
 * @author Brian Chang, Chris Lam
 */
public class MainActivity extends AppCompatActivity {

    /**
    * Sets title and displays MainActivity view
    * @param savedInstanceState keeps track of activity state
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.ru_cafe);
        setContentView(R.layout.activity_main);
    }


   /**
    * Opens DonutActivity view
    * @param view current view
    */
    public void openDonut(View view) {
        Intent intent = new Intent(this, DonutActivity.class);
        startActivity(intent);
    }

    /**
     * Opens CoffeeActivity view
     * @param view current view
     */
    public void openCoffee(View view) {
        Intent intent = new Intent(this, CoffeeActivity.class);
        startActivity(intent);
    }

    /**
     * Opens OrderActivity view
     * @param view current view
     */
    public void openOrder(View view) {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    /**
     * Opens StoreOrderActivity view
     * @param view current view
     */
    public void openStoreOrder(View view) {
        Intent intent = new Intent(this, StoreOrderActivity.class);
        startActivity(intent);
    }
}