package com.lechampalamaison.loc.lechampalamaison.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lechampalamaison.loc.lechampalamaison.Activity.DeliveryActivity;
import com.lechampalamaison.loc.lechampalamaison.Activity.LoginActivity;
import com.lechampalamaison.loc.lechampalamaison.Model.CartItem;
import com.lechampalamaison.loc.lechampalamaison.Model.Item;
import com.lechampalamaison.loc.lechampalamaison.R;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.QuantityResponse;
import com.lechampalamaison.loc.lechampalamaison.api.service.ItemClient;
import com.lechampalamaison.loc.lechampalamaison.api.utils.Configuration;
import com.lechampalamaison.loc.lechampalamaison.listarrayadaper.CartListAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.lechampalamaison.loc.lechampalamaison.Activity.LoginActivity.PREFS_NAME_USER;


public class CartFragment extends Fragment {

    public static List<CartItem> itemList = new ArrayList<>();
    private TextView total;
    private RecyclerView recyclerView;
    private CartListAdapter cartListAdapter;

    SharedPreferences sharedpreferences;

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

        sharedpreferences = getActivity().getSharedPreferences(PREFS_NAME_USER, Context.MODE_PRIVATE);
        String jsonCart = sharedpreferences.getString("cart", null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<CartItem>>() {}.getType();
        itemList = gson.fromJson(jsonCart, type);

        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.recycler_cart);

        total = view.findViewById(R.id.tv_total);

        cartListAdapter = new CartListAdapter(itemList, total, getContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cartListAdapter);

        Button placeOrderButton = view.findViewById(R.id.btn_placeorder);
        placeOrderButton.setOnClickListener((View v) -> {
            if (!itemList.isEmpty()) {
                Call<QuantityResponse> call;
                call = itemClient.itemQuantity(itemList);

                call.enqueue(new Callback<QuantityResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<QuantityResponse> call, @NonNull Response<QuantityResponse> response) {
                        int responseSize;

                        if (response.body().getResult() == null) {
                            responseSize = 0;
                        } else {
                            responseSize = response.body().getResult().length;
                        }

                        if (itemList.size() > responseSize) {
                            List<Integer> toRemove = new ArrayList<>();

                            for (CartItem item : itemList) {
                                int itemId = item.getItem().getId();
                                int i = 0;

                                while ((i < responseSize) && (itemId != response.body().getResult()[i].getIdItem())) {
                                    i++;
                                }

                                if (i == responseSize) {
                                    toRemove.add(0);
                                }
                            }

                            for (int pos : toRemove) {
                                cartListAdapter.removeItem(pos);
                            }

                            Toast.makeText(getContext(), "Certains produits ont dû être retirés de votre panier", Toast.LENGTH_LONG).show();
                        } else {
                            if (response.body().getCode() == 0) {
                                boolean overQuantity = false;

                                for (QuantityResponse.QuantityResult result : response.body().getResult()) {
                                    for (CartItem item : itemList) {
                                        if (item.getItem().getId() == result.getIdItem() && item.getQuantity() > result.getQuantityItem()) {
                                            Toast.makeText(getContext(), "Le produit " + item.getItem().getTitle() + " est indisponible pour la quantité demandée", Toast.LENGTH_LONG).show();
                                            overQuantity = true;
                                        }

                                        if (overQuantity) {
                                            break;
                                        }
                                    }

                                    if (overQuantity) {
                                        break;
                                    }
                                }

                                if (!overQuantity) {
                                    Intent myIntent = new Intent(getActivity(), DeliveryActivity.class);
                                    startActivity(myIntent);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<QuantityResponse> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                                Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), "Votre panier est vide", Toast.LENGTH_LONG).show();
            }
        });

        // Inflate the layout for this fragment
        return view;
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
