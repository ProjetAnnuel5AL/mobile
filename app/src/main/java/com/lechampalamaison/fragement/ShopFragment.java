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
import com.lechampalamaison.adapter.ListShopArrayAdapter;
import com.lechampalamaison.model.Item;

import java.util.ArrayList;

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

    ListView listViewItem;
    ListShopArrayAdapter adapter;
    int pagination = 0;
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Configuration.urlApi)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    ItemClient itemClient = retrofit.create(ItemClient.class);

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Item> listItem;

    //filter
    String manualSearch;
    String category;
    String product;
    Double lat;
    Double lng;
    Double priceMin;
    Double priceMax;
    int limit;

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
        listViewItem = (ListView)view.findViewById(R.id.listItem);

        adapter = new ListShopArrayAdapter(getContext(), listItem);

        ItemApi itemApi = new ItemApi(0);
        updateListView(itemApi, false);
        //filter();
        listViewItem.setAdapter(adapter);
        listViewItem.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {

                /*Toast.makeText(getContext(), listItem.get(position).getTitle(),
                        Toast.LENGTH_SHORT).show();*/
                Bundle bundle = new Bundle();
                bundle.putInt("id", listItem.get(position).getId() );
                ItemFragment itemFragment = new ItemFragment();
                itemFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, itemFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

        listViewItem.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (listViewItem.getLastVisiblePosition() - listViewItem.getHeaderViewsCount() -
                        listViewItem.getFooterViewsCount()) >= (adapter.getCount() - 1)) {
                        pagination++;
                        ItemApi itemApi = new ItemApi(pagination);
                        updateListView(itemApi, false);
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        return view;
    }

    public void updateListView(ItemApi itemApi, boolean filter){

        Call<ItemsResponse> call;
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
        });
    }

    public void filter(){
        //Mettre la récupération des inputs

        pagination = 0;
        listItem.clear();
        ItemApi itemApi = new ItemApi(pagination, "BON", null, null, null, null, null, null);
        updateListView(itemApi, true);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
