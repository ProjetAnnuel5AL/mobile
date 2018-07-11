package com.lechampalamaison.fragement;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lechampalamaison.R;

import com.lechampalamaison.api.model.apiResponse.FindOrdersResponse;
import com.lechampalamaison.api.model.apiResponse.ItemsResponse;
import com.lechampalamaison.api.service.ItemClient;
import com.lechampalamaison.api.service.OrderClient;
import com.lechampalamaison.api.utils.Configuration;
import com.lechampalamaison.listarrayadaper.ListOrdersArrayAdapter;
import com.lechampalamaison.listarrayadaper.ListShopArrayAdapter;
import com.lechampalamaison.model.Item;
import com.lechampalamaison.model.Order;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrdersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String PREFS_NAME_USER = "USER";

    ListView listViewOrders;
    ListOrdersArrayAdapter adapter;


    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Configuration.urlApi)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    OrderClient orderClient = retrofit.create(OrderClient.class);

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Order> listOrders;

    private OnFragmentInteractionListener mListener;

    public OrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new OrdersFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        listOrders = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        listViewOrders = (ListView)view.findViewById(R.id.listOrders);

        adapter = new ListOrdersArrayAdapter(getContext(), listOrders);
        listViewOrders.setAdapter(adapter);

        loadOrder();

        return view;
    }

    public void loadOrder(){

        Call<FindOrdersResponse> call;

        SharedPreferences sharedPref = getContext().getSharedPreferences(PREFS_NAME_USER , Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.token_key), "");
        String loginUser = sharedPref.getString(getString(R.string.login_key), "");

        call = orderClient.findOrders(loginUser, token);


        call.enqueue(new Callback<FindOrdersResponse>() {
            @Override
            public void onResponse(Call<FindOrdersResponse> call, Response<FindOrdersResponse> response) {
                if(response.body().getCode() == 0){
                    for(int i =0; i< response.body().getResult().getOrders().length; i++){
                        listOrders.add(
                                new Order(response.body().getResult().getOrders()[i].getIdOrder(), response.body().getResult().getOrders()[i].getDateOrder(),response.body().getResult().getOrders()[i].getTotalOrder(), response.body().getResult().getStatus()[i]));
                    }
                    adapter.notifyDataSetChanged();
                }else{

                }
            }
            @Override
            public void onFailure(Call<FindOrdersResponse> call, Throwable t) {
            }
        });
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: User argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
