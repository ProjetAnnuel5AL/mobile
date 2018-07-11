package com.lechampalamaison.fragement;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.lechampalamaison.R;
import com.lechampalamaison.activity.HomeActivity;
import com.lechampalamaison.api.model.ItemApi;
import com.lechampalamaison.api.model.apiResponse.ItemsResponse;
import com.lechampalamaison.api.service.ItemClient;
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

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class ShopFragment extends Fragment {

    ListView listViewItem;
    ListShopArrayAdapter adapter;
    public int pagination = 0;
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Configuration.urlApi)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    ItemClient itemClient = retrofit.create(ItemClient.class);

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



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    private PopupWindow mPopupWindow;

    private FrameLayout mRelativeLayout;
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

        mRelativeLayout = (FrameLayout) view.findViewById(R.id.fragment_shop);
        View views =((AppCompatActivity)getActivity()).getSupportActionBar().getCustomView();
        ImageButton img=(ImageButton)views.findViewById(R.id.action_bar_search);
        img.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Initialize a new instance of LayoutInflater service
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

                // Inflate the custom layout/view
                View customView = inflater.inflate(R.layout.filtre,null);

                /*
                    public PopupWindow (View contentView, int width, int height)
                        Create a new non focusable popup window which can display the contentView.
                        The dimension of the window must be passed to this constructor.

                        The popup does not provide any background. This should be handled by
                        the content view.

                    Parameters
                        contentView : the popup's content
                        width : the popup's width
                        height : the popup's height
                */
                // Initialize a new instance of popup window
                mPopupWindow = new PopupWindow(
                        customView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

                // Set an elevation value for popup window
                // Call requires API level 21
                if(Build.VERSION.SDK_INT>=21){
                    mPopupWindow.setElevation(5.0f);
                }

                // Get a reference for the custom view close button
                ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
                Button valideboutton = (Button) customView.findViewById(R.id.filtre_valider);
                EditText nomEdittext = (EditText) customView.findViewById(R.id.editTextName);

                // (1) get a reference to the spinner
                Spinner spinnerCategory = (Spinner)customView.findViewById(R.id.spinnerCategory);

                List<String> spinnerArray = new ArrayList<>();
                spinnerArray.add("zz");
                spinnerArray.add("zzz");

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_spinner_item,
                        spinnerArray
                );


                spinnerCategory.setAdapter(adapter);

                // Set a click listener for the popup window close button
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        mPopupWindow.dismiss();
                    }
                });

                valideboutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window

                        Toast.makeText(getActivity(),  nomEdittext.getText(), Toast.LENGTH_LONG).show();
                    }
                });
                /*
                    public void showAtLocation (View parent, int gravity, int x, int y)
                        Display the content view in a popup window at the specified location. If the
                        popup window cannot fit on screen, it will be clipped.
                        Learn WindowManager.LayoutParams for more information on how gravity and the x
                        and y parameters are related. Specifying a gravity of NO_GRAVITY is similar
                        to specifying Gravity.LEFT | Gravity.TOP.

                    Parameters
                        parent : a parent view to get the getWindowToken() token from
                        gravity : the gravity which controls the placement of the popup window
                        x : the popup's x location offset
                        y : the popup's y location offset
                */
                // Finally, show the popup window at the center location of root relative layout
                mPopupWindow.showAtLocation( mRelativeLayout,Gravity.CENTER,0,0);
                mPopupWindow.setFocusable(true);
                mPopupWindow.update();
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
        ItemApi itemApi = new ItemApi(0, "BON", null, null, null, null, null, null);
        updateListView(itemApi, true);

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
