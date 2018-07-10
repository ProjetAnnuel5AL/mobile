package com.lechampalamaison.fragement;

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
import com.lechampalamaison.api.model.ItemApi;
import com.lechampalamaison.api.model.apiResponse.ItemsResponse;
import com.lechampalamaison.api.service.ItemClient;
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

    ListView listViewOrders;
    ListOrdersArrayAdapter adapter;
    int pagination = 0;
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Configuration.urlApi)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Order> listOrders;

    //filter


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


        listOrders.add(new Order());

        //filter();
        listViewOrders.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }

    public void updateListView(ItemApi itemApi, boolean filter){

        /*Call<ItemsResponse> call;
        if(filter == false){
            call = itemClient.itemsWithoutFilter(itemApi.getLimit()*20);
        }else{
            call = itemClient.itemsFilter(itemApi.getLimit()*20, itemApi.getManualSearch(), itemApi.getCategory(), itemApi.getProduct(), itemApi.getLat(), itemApi.getLng(), itemApi.getPriceMin(), itemApi.getPriceMax());
        }

        call.enqueue(new Callback<ItemsResponse>() {
            @Override
            public void onResponse(Call<ItemsResponse> call, Response<ItemsResponse> response) {
                if(response.body().getCode() == 0){

                    for(int i =0; i< response.body().getResult().getList().length; i++){
                        listItem.add(new Item(response.body().getResult().getList()[i].getIdItem(), response.body().getResult().getList()[i].getNameItem(), response.body().getResult().getList()[i].getNameCategory() + ", " + response.body().getResult().getList()[i].getNameProduct(), response.body().getResult().getList()[i].getCpItem() + " " + response.body().getResult().getList()[i].getCityItem(), response.body().getResult().getList()[i].getPriceItem(), response.body().getResult().getList()[i].getFileExtensionsItem()));
                    }
                    adapter.notifyDataSetChanged();
                }else{
                }
            }
            @Override
            public void onFailure(Call<ItemsResponse> call, Throwable t) {
            }
        });*/
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