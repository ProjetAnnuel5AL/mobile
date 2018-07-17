package com.lechampalamaison.loc.lechampalamaison.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lechampalamaison.loc.lechampalamaison.Model.Order;
import com.lechampalamaison.loc.lechampalamaison.R;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.FindOrdersResponse;
import com.lechampalamaison.loc.lechampalamaison.api.service.OrderClient;
import com.lechampalamaison.loc.lechampalamaison.api.utils.Configuration;
import com.lechampalamaison.loc.lechampalamaison.listarrayadaper.ListOrdersArrayAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrdersActivity extends AppCompatActivity {

    public static final String PREFS_NAME_USER = "USER";

    ListView listViewOrders;
    ListOrdersArrayAdapter adapter;
    TextView textViewNoOrder;

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Configuration.urlApi)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    OrderClient orderClient = retrofit.create(OrderClient.class);
    ArrayList<Order> listOrders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        listOrders = new ArrayList<>();
        listViewOrders = (ListView) findViewById(R.id.listOrders);

        adapter = new ListOrdersArrayAdapter(this, listOrders);
        listViewOrders.setAdapter(adapter);

        textViewNoOrder = (TextView) findViewById(R.id.textViewNoOrder);
        loadOrder();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void loadOrder(){

        Call<FindOrdersResponse> call;

        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME_USER , Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.token_key), "");
        String loginUser = sharedPref.getString(getString(R.string.login_key), "");

        call = orderClient.findOrders(loginUser, token);

        call.enqueue(new Callback<FindOrdersResponse>() {
            @Override
            public void onResponse(Call<FindOrdersResponse> call, Response<FindOrdersResponse> response) {
                if(response.body().getCode() == 0){
                    if( response.body().getResult().getOrders().length == 0){
                        textViewNoOrder.setText("Aucune commande");
                    }

                    for(int i =0; i< response.body().getResult().getOrders().length; i++){
                        listOrders.add( new Order(response.body().getResult().getOrders()[i].getIdOrder(), response.body().getResult().getOrders()[i].getDateOrder(),response.body().getResult().getOrders()[i].getTotalOrder(), response.body().getResult().getStatus()[i]));
                    }
                    adapter.notifyDataSetChanged();
                }else{

                        textViewNoOrder.setText("Aucune commande");

                }
            }
            @Override
            public void onFailure(Call<FindOrdersResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


}
