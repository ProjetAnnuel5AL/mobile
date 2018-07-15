package com.lechampalamaison.fragement;

import android.app.Activity;
import android.content.Context;
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

import com.lechampalamaison.R;
import com.lechampalamaison.activity.DeliveryActivity;
import com.lechampalamaison.activity.HomeActivity;
import com.lechampalamaison.adapter.CartListAdapter;
import com.lechampalamaison.api.model.apiResponse.QuantityResponse;
import com.lechampalamaison.api.service.ItemClient;
import com.lechampalamaison.api.utils.Configuration;
import com.lechampalamaison.model.CartItem;
import com.lechampalamaison.model.Item;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static List<CartItem> itemList = new ArrayList<>();
    private TextView total;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Configuration.urlApi)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    ItemClient itemClient = retrofit.create(ItemClient.class);

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
            /*
            int qty;

            for (CartItem currentItem : itemList) {
                qty = currentItem.getQuantity();

                Call<QuantityResponse> call;
                call = itemClient.itemQuantity(currentItem.getItem().getId());

                call.enqueue(new Callback<QuantityResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<QuantityResponse> call,@NonNull Response<QuantityResponse> response) {
                        if (response.body().getCode() == 0) {
                            System.out.println("It's ok my man");
                        } else {
                            System.out.println("bad...");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<QuantityResponse> call,@NonNull Throwable t) {
                    }
                });
            }*/

            Intent myIntent = new Intent(getActivity(), DeliveryActivity.class);
            startActivity(myIntent);
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void remplir() {
        CartItem item1 = new CartItem(new Item(1, "Fromage", "Très bon fromage ma gueule", 25), 1);
        itemList.add(item1);

        CartItem item2 = new CartItem(new Item(19, "Vin", "10 ans d'âge", 100), 1);
        itemList.add(item2);
    }

    public void calculateTotal(){
        double total = 0;

        for(CartItem item : itemList) {
            total += item.getItem().getPrice();
        }

        this.total.setText(String.valueOf(total) + " €");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
