package com.lechampalamaison.fragement;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lechampalamaison.R;
import com.lechampalamaison.api.model.ItemApi;
import com.lechampalamaison.api.model.apiResponse.ItemsResponse;
import com.lechampalamaison.api.service.ItemClient;
import com.lechampalamaison.api.service.UserClient;
import com.lechampalamaison.api.utils.Configuration;
import com.lechampalamaison.listarrayadaper.ListShopArrayAdapter;
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
 * {@link ShopFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Configuration.urlApi)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    ItemClient itemClient = retrofit.create(ItemClient.class);

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Item> listItem;

    private OnFragmentInteractionListener mListener;

    public ShopFragment() {
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
    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
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

        //on récupère la liste des items
        listItem = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        ListView listViewItem = (ListView)view.findViewById(R.id.listItem);

        ListShopArrayAdapter adapter = new ListShopArrayAdapter(getContext(), listItem);


        ItemApi itemApi = new ItemApi(0);
        Call<ItemsResponse> call = itemClient.itemsWithouFilter(itemApi.getLimit());

        call.enqueue(new Callback<ItemsResponse>() {
            @Override
            public void onResponse(Call<ItemsResponse> call, Response<ItemsResponse> response) {
                if(response.body().getCode() == 0){
                    for(int i =0; i< response.body().getResult().getList().length; i++){
                        listItem.add(new Item(response.body().getResult().getList()[i].getIdItem(), response.body().getResult().getList()[i].getNameItem(), response.body().getResult().getList()[i].getNameCategory() + ", " + response.body().getResult().getList()[i].getNameProduct(), response.body().getResult().getList()[i].getCpItem() + " " + response.body().getResult().getList()[i].getCityItem(), response.body().getResult().getList()[i].getPriceItem(), response.body().getResult().getList()[i].getFileExtensionsItem()));
                    }
                    listViewItem.setAdapter(adapter);
                }else{

                }
            }
            @Override
            public void onFailure(Call<ItemsResponse> call, Throwable t) {

            }
        });


        listViewItem.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Toast.makeText(getContext(), listItem.get(position).getTitle(),
                        Toast.LENGTH_SHORT).show();

            }
        });

        return view;
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
