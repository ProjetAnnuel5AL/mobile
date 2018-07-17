package com.lechampalamaison.loc.lechampalamaison.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lechampalamaison.loc.lechampalamaison.Fragment.CartFragment;
import com.lechampalamaison.loc.lechampalamaison.Model.CartItem;
import com.lechampalamaison.loc.lechampalamaison.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.lechampalamaison.loc.lechampalamaison.Activity.LoginActivity.PREFS_NAME_USER;


public class PaymentDetailsActivity extends AppCompatActivity {

    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        CartFragment.itemList.clear();

        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        List<CartItem> itemList = new ArrayList<>();
        Gson gson = new Gson();
        String jsonCart = gson.toJson(itemList);

        editor.putString("cart", jsonCart);
        editor.apply();
        DeliveryActivity.fa.finish();


        Toast.makeText(this, "Commande passée avec succès", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
