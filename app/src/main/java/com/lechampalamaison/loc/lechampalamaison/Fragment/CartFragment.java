package com.lechampalamaison.loc.lechampalamaison.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.lechampalamaison.loc.lechampalamaison.Activity.DeliveryActivity;
import com.lechampalamaison.loc.lechampalamaison.Activity.HomeActivity;
import com.lechampalamaison.loc.lechampalamaison.Model.CartItem;
import com.lechampalamaison.loc.lechampalamaison.Model.Item;
import com.lechampalamaison.loc.lechampalamaison.R;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.QuantityResponse;
import com.lechampalamaison.loc.lechampalamaison.api.service.ItemClient;
import com.lechampalamaison.loc.lechampalamaison.api.utils.Configuration;
import com.lechampalamaison.loc.lechampalamaison.listarrayadaper.CartListAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CartFragment extends Fragment {

    public static List<CartItem> itemList = new ArrayList<>();
    private TextView total;


    private OnFragmentInteractionListener mListener;
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Configuration.urlApi)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    ItemClient itemClient = retrofit.create(ItemClient.class);

    public CartFragment() {

    }

    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_cart);

        CartListAdapter cartListAdapter = new CartListAdapter(itemList);

        this.total = view.findViewById(R.id.tv_total);

        recyclerView.setHasFixedSize(true);
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cartListAdapter);


        remplir();
        calculateTotal();

        Button placeOrderButton = view.findViewById(R.id.btn_placeorder);
        placeOrderButton.setOnClickListener((View v) -> {



            Call<QuantityResponse> call;
            call = itemClient.itemQuantity(itemList);

            call.enqueue(new Callback<QuantityResponse>() {
                @Override
                public void onResponse(@NonNull Call<QuantityResponse> call,@NonNull Response<QuantityResponse> response) {
                    if (response.body().getCode() == 0) {
                        //AZ : ICI verifier les qte
                        Intent myIntent = new Intent(getActivity(), DeliveryActivity.class);
                        startActivity(myIntent);
                    } else {
                       //Msg d erreur
                    }
                }

                @Override
                public void onFailure(@NonNull Call<QuantityResponse> call,@NonNull Throwable t) {
                    Toast.makeText(getContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                            Toast.LENGTH_SHORT).show();
                }
            });



        });

        // Inflate the layout for this fragment
        return view;
    }

    public void remplir() {
        CartItem item1 = new CartItem(new Item(18, "Fromage", "Très bon fromage ma gueule", 25, 1), 1);
        itemList.add(item1);

        CartItem item2 = new CartItem(new Item(19, "Vin", "10 ans d'âge", 100, 1), 1);
        itemList.add(item2);

    }

    public void calculateTotal(){
        double total = 0;

        for(CartItem item : itemList) {
            total += item.getItem().getPrice();
        }

        this.total.setText(String.valueOf(total) + " €");
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
