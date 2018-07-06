package com.lechampalamaison.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lechampalamaison.R;
import com.lechampalamaison.adapter.CartListAdapter;
import com.lechampalamaison.model.Item;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    public static  List<Item> itemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CartListAdapter cartListAdapter;
    // String jsonCartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recycler_cart);

        cartListAdapter = new CartListAdapter(itemList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cartListAdapter);


        calculateTotal();
        remplir();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void remplir() {
        Item item1 = new Item("Fromage", "Très bon fromage ma gueule", 25);
        itemList.add(item1);

        Item item2 = new Item("Vin", "10 ans d'âge", 100);
        itemList.add(item2);
    }

    public static void calculateTotal(){
        // TODO : Calculate total
    }

    /* public void insertOrder(View view){
        if (total > 0){
            Gson gson = new Gson();
            jsonCartList = gson.toJson(CartListAdapter.selecteditems);

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int response) {
                    switch (response){
                        case DialogInterface.BUTTON_POSITIVE:
                            // Response is "Yes"
                            placeOrderRequest();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            // Response is "No"
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
            builder.setMessage("Do you want to place Order ?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        } else {
            Toast.makeText(CartActivity.this,"No items in Cart !", Toast.LENGTH_LONG).show();
        }
    }*/

    private void placeOrderRequest(){
        // TODO : Send the place order request to the server
    }
}