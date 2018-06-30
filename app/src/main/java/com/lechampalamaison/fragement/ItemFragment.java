package com.lechampalamaison.fragement;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lechampalamaison.R;
import com.lechampalamaison.api.model.apiResponse.ItemResponse;
import com.lechampalamaison.api.model.apiResponse.ItemsResponse;
import com.lechampalamaison.api.service.ItemClient;
import com.lechampalamaison.api.utils.Configuration;
import com.lechampalamaison.model.Item;

import java.text.NumberFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Configuration.urlApi)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    ItemClient itemClient = retrofit.create(ItemClient.class);

    public TextView titleItem;
    public TextView catProductItem;
    public TextView producerInfoItem;
    public TextView priceItem;
    public TextView descriptionItem;

    public ItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemFragment newInstance(String param1, String param2) {
        ItemFragment fragment = new ItemFragment();
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

        View view = inflater.inflate(R.layout.fragment_item, container, false);
        titleItem = (TextView) view.findViewById(R.id.titleItem);
        catProductItem = (TextView) view.findViewById(R.id.catProductItem);
        producerInfoItem = (TextView) view.findViewById(R.id.producerInfoItem);
        priceItem = (TextView) view.findViewById(R.id.priceItem);
        descriptionItem = (TextView) view.findViewById(R.id.descriptionItem);



        int idItem = this.getArguments().getInt("id");

        Call<ItemResponse> call;
        call = itemClient.item(idItem);

        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                if(response.body().getCode() == 0){
                    titleItem.setText(response.body().getResult().getInfoItem().getNameItem());
                    catProductItem.setText(response.body().getResult().getInfoItem().getNameCategory()+", "+response.body().getResult().getInfoItem().getNameProduct());
                    producerInfoItem.setText(response.body().getResult().getInfoItem().getNameItem());

                    Double price = (double) Math.round(response.body().getResult().getInfoItem().getPriceItem() * 100) / 100;
                    NumberFormat format= NumberFormat.getInstance();
                    format.setMinimumFractionDigits(2);
                    priceItem.setText("Prix : " +format.format(price)  + "€");

                    descriptionItem.setText("Descirption :\n" + response.body().getResult().getInfoItem().getDescriptionItem());
                }else{

                }
            }
            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
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