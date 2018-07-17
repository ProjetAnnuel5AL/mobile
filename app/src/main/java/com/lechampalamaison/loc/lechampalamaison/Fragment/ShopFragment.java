package com.lechampalamaison.loc.lechampalamaison.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.lechampalamaison.loc.lechampalamaison.Activity.ItemActivity;
import com.lechampalamaison.loc.lechampalamaison.Model.Category;
import com.lechampalamaison.loc.lechampalamaison.Model.Item;
import com.lechampalamaison.loc.lechampalamaison.Model.Product;
import com.lechampalamaison.loc.lechampalamaison.R;
import com.lechampalamaison.loc.lechampalamaison.api.model.ItemApi;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.CategoryResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ItemPriceMinMaxResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ItemsResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ProductResponse;
import com.lechampalamaison.loc.lechampalamaison.api.service.CategoryClient;
import com.lechampalamaison.loc.lechampalamaison.api.service.ItemClient;
import com.lechampalamaison.loc.lechampalamaison.api.service.ProductClient;
import com.lechampalamaison.loc.lechampalamaison.api.utils.Configuration;
import com.lechampalamaison.loc.lechampalamaison.listarrayadaper.ListShopArrayAdapter;
import com.lechampalamaison.loc.lechampalamaison.listarrayadaper.PlaceAutocompleteAdapter;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ShopFragment extends Fragment {

    Double tmpLat = 0.0;
    Double tmpLng = 0.0;

    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                tmpLat = location.getLatitude();
                tmpLng = location.getLongitude();
                mLocationManager.removeUpdates(mLocationListener);
            } else {
                //Logger.d("Location is null");
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
    private LocationManager mLocationManager;
    public static final int LOCATION_UPDATE_MIN_DISTANCE = 10;
    public static final int LOCATION_UPDATE_MIN_TIME = 5000;

    ListView listViewItem;
    ListShopArrayAdapter adapter;
    public int pagination = 0;
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Configuration.urlApi)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    ItemClient itemClient = retrofit.create(ItemClient.class);
    CategoryClient categoryClient = retrofit.create(CategoryClient.class);
    ProductClient productClient = retrofit.create(ProductClient.class);

    ArrayList<Category> categoryArrayList = new ArrayList<>();
    ArrayList<Product> productArrayList = new ArrayList<>();
    ArrayAdapter<String> adapterCategory;
    ArrayAdapter<String> adapterProduct;
    ArrayList<Item> listItem;
    Spinner spinnerProduct;
    Spinner spinnerCategory;
    List<String> spinnerArrayCategory = new ArrayList<>();
    List<String> spinnerArrayProduct = new ArrayList<>();

    //filter
    String manualSearch;
    String category;
    String product;
    Double lat;
    Double lng;
    Double priceMin;
    Double priceMax;
    int limit;

    //ON save les valeurs temporaire du filtre
    String tmpName = "";
    String tmpCat = "";
    String tmpProd = "";
    Number tmpPriceMax = null;
    Number tmpPriceMin = null;


    private OnFragmentInteractionListener mListener;

    public ShopFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

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


        listViewItem = (ListView) view.findViewById(R.id.listItem);

        adapter = new ListShopArrayAdapter(getContext(), listItem);

        ItemApi itemApi = new ItemApi(0);
        updateListView(itemApi, false);
        listViewItem.setAdapter(adapter);
        listViewItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent myIntent = new Intent(getActivity(), ItemActivity.class);
                myIntent.putExtra("id", String.valueOf(listItem.get(position).getId()));
                startActivity(myIntent);

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
        View views = ((AppCompatActivity) getActivity()).getSupportActionBar().getCustomView();


        return view;
    }

    public void updateListView(ItemApi itemApi, boolean filter) {

        Call<ItemsResponse> call;
        if (filter == false) {
            call = itemClient.itemsWithoutFilter(itemApi.getLimit() * 20);
        } else {
            call = itemClient.itemsFilter(itemApi.getLimit() * 20, itemApi.getManualSearch(), itemApi.getCategory(), itemApi.getProduct(), itemApi.getLat(), itemApi.getLng(), itemApi.getPriceMin(), itemApi.getPriceMax());
        }

        call.enqueue(new Callback<ItemsResponse>() {
            @Override
            public void onResponse(Call<ItemsResponse> call, Response<ItemsResponse> response) {
                if (response.body().getCode() == 0) {
                    if(response.body().getResult().getList().length == 0){
                        pagination+= -1;
                    }
                    for (int i = 0; i < response.body().getResult().getList().length; i++) {
                        listItem.add(new Item(response.body().getResult().getList()[i].getIdItem(), response.body().getResult().getList()[i].getNameItem(), response.body().getResult().getList()[i].getNameCategory() + ", " + response.body().getResult().getList()[i].getNameProduct(), response.body().getResult().getList()[i].getCpItem() + " " + response.body().getResult().getList()[i].getCityItem(), response.body().getResult().getList()[i].getPriceItem(), response.body().getResult().getList()[i].getFileExtensionsItem()));
                    }
                    adapter.notifyDataSetChanged();
                } else {

                }
            }

            @Override
            public void onFailure(Call<ItemsResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void filter(String nom, int category, int product, Number prixMin, Number prixMax, boolean latlng) {
        //Mettre la récupération des inputs
        pagination = 0;
        listItem.clear();
        Double la = null;
        Double ln = null;
        if(latlng == true){
            la = tmpLat;
            ln = tmpLng;
        }

        ItemApi itemApi = new ItemApi(0, nom, category, product, la, ln, prixMin, prixMax);
        updateListView(itemApi, true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_filtre:
                filtreBouton();
                return true;
        }
        return false;
    }

    public interface OnFragmentInteractionListener {
        // TODO: User argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void filtreBouton() {
        // Initialize a new instance of LayoutInflater service
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        View customView = inflater.inflate(R.layout.filtre, null);


        // Initialize a new instance of popup window
        mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        // Set an elevation value for popup window
        // Call requires API level 21
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }


        getCurrentLocation();

        CheckBox checkBox = (CheckBox) customView.findViewById(R.id.checkBoxProche);
        if (checkBox.isChecked()) {

        }

        // Get a reference for the custom view close button
        ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
        Button valideboutton = (Button) customView.findViewById(R.id.filtre_valider);
        EditText editTextName = (EditText) customView.findViewById(R.id.editTextName);


        RangeSeekBar rangeSeekBar = (RangeSeekBar) customView.findViewById(R.id.rangeSeekBar);
        rangeSeekBar.setTextAboveThumbsColorResource(android.R.color.holo_blue_bright);

        // (1) get a reference to the spinner
        if (spinnerArrayCategory.size() != 0) {
            spinnerArrayCategory = new ArrayList<>();
        }
        spinnerCategory = (Spinner) customView.findViewById(R.id.spinnerCategory);

        Call<CategoryResponse> call;
        call = categoryClient.category();
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.body().getCode() == 0) {
                    spinnerArrayCategory.add("Toutes");
                    for (int i = 0; i < response.body().getResult().length; i++) {
                        categoryArrayList.add(new Category(response.body().getResult()[i].getIdCategory(), response.body().getResult()[i].getNameCategory()));
                        spinnerArrayCategory.add(response.body().getResult()[i].getNameCategory());
                    }
                    adapterCategory = new ArrayAdapter<String>(
                            getActivity(),
                            android.R.layout.simple_spinner_item,
                            spinnerArrayCategory
                    );

                    spinnerCategory.setAdapter(adapterCategory);

                    if (!tmpCat.equals("")) {
                        int t = adapterCategory.getPosition(tmpCat);
                        spinnerCategory.setSelection(t);
                    }


                } else {
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // (1) get a reference to the spinner
        if (spinnerArrayProduct.size() != 0) {
            spinnerArrayProduct = new ArrayList<>();
        }
        spinnerProduct = (Spinner) customView.findViewById(R.id.spinnerProduct);

        Call<ProductResponse> call2;
        if (tmpCat.equals("")) {
            call2 = productClient.products();
        } else {
            int idCat = 0;
            for (int i = 0; i < categoryArrayList.size(); i++) {
                if (categoryArrayList.get(i).getNameCategory().equals(tmpCat)) {
                    idCat = categoryArrayList.get(i).getIdCategory();
                    break;
                }
            }

            call2 = productClient.productsFindByCategoryId(idCat);
        }
        call2.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.body().getCode() == 0) {
                    spinnerArrayProduct.add("Tous");
                    for (int i = 0; i < response.body().getResult().length; i++) {
                        productArrayList.add(new Product(response.body().getResult()[i].getIdProduct(), response.body().getResult()[i].getNameProduct(), response.body().getResult()[i].getIdCategoryProduct()));
                        spinnerArrayProduct.add(response.body().getResult()[i].getNameProduct());
                    }

                    adapterProduct = new ArrayAdapter<String>(
                            getActivity(),
                            android.R.layout.simple_spinner_item,
                            spinnerArrayProduct
                    );

                    spinnerProduct.setAdapter(adapterProduct);
                    if (!tmpProd.equals("")) {
                        int t = adapterProduct.getPosition(tmpProd);
                        spinnerProduct.setSelection(t);
                    }

                } else {
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                        Toast.LENGTH_SHORT).show();
            }
        });


        if (!tmpName.equals("")) {
            editTextName.setText(tmpName);
        }


        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!spinnerCategory.getSelectedItem().toString().equals("Toutes") && !spinnerCategory.getSelectedItem().toString().equals(tmpCat)) {
                    int idCat = 0;
                    for (int i = 0; i < categoryArrayList.size(); i++) {
                        if (categoryArrayList.get(i).getNameCategory().equals(spinnerCategory.getSelectedItem().toString())) {
                            idCat = categoryArrayList.get(i).getIdCategory();
                            break;
                        }
                    }
                    spinnerArrayProduct = new ArrayList<>();
                    spinnerArrayProduct.add("Tous");
                    for (int i = 0; i < productArrayList.size(); i++) {
                        if (productArrayList.get(i).getIdCategoryProduct() == idCat) {
                            String name = productArrayList.get(i).getNameProduct();
                            spinnerArrayProduct.add(productArrayList.get(i).getNameProduct());
                        }

                    }
                    adapterProduct = new ArrayAdapter<String>(
                            getActivity(),
                            android.R.layout.simple_spinner_item,
                            spinnerArrayProduct
                    );
                    if (!tmpProd.equals("")) {
                        spinnerProduct.setSelection(adapterProduct.getPosition(tmpProd));
                    }

                    spinnerProduct.setAdapter(adapterProduct);

                } else if (spinnerCategory.getSelectedItem().toString().equals(tmpCat)) {
                    if (!tmpProd.equals("")) {
                        int t = adapterProduct.getPosition(tmpProd);
                        spinnerProduct.setSelection(t);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        Call<ItemPriceMinMaxResponse> call3;


        call3 = itemClient.itemPriceMinMax();

        call3.enqueue(new Callback<ItemPriceMinMaxResponse>() {
            @Override
            public void onResponse(Call<ItemPriceMinMaxResponse> call, Response<ItemPriceMinMaxResponse> response) {
                if (response.body().getCode() == 0) {

                    rangeSeekBar.setRangeValues(0.0, response.body().getResult().getMaxPrice());
                    rangeSeekBar.setSelectedMaxValue(response.body().getResult().getMaxPrice());
                    rangeSeekBar.setSelectedMinValue(0.0);
                    if (tmpPriceMin != null) {
                        rangeSeekBar.setSelectedMinValue(tmpPriceMin);
                    }
                    if (tmpPriceMax != null) {
                        rangeSeekBar.setSelectedMaxValue(tmpPriceMax);
                    }

                } else {
                }
            }

            @Override
            public void onFailure(Call<ItemPriceMinMaxResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                        Toast.LENGTH_SHORT).show();
            }
        });


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


                if (!editTextName.getText().toString().equals("")) {
                    tmpName = editTextName.getText().toString();
                } else {
                    tmpName = "";
                }

                int idCat = 0;
                String cat = spinnerCategory.getSelectedItem().toString();
                if (!cat.equals("Toutes")) {
                    tmpCat = cat;
                    for (int i = 0; i < categoryArrayList.size(); i++) {
                        if (cat.equals(categoryArrayList.get(i).getNameCategory())) {
                            idCat = categoryArrayList.get(i).getIdCategory();
                            break;
                        }
                    }
                } else {
                    tmpCat = "";
                }

                int idProduct = 0;
                String prod = spinnerProduct.getSelectedItem().toString();
                if (prod != "Tous") {
                    tmpProd = prod;
                    for (int i = 0; i < productArrayList.size(); i++) {
                        if (prod.equals(productArrayList.get(i).getNameProduct())) {
                            idProduct = productArrayList.get(i).getIdProduct();
                            break;
                        }
                    }
                } else {
                    tmpProd = "";
                }

                tmpPriceMin = rangeSeekBar.getSelectedMinValue();
                tmpPriceMax = rangeSeekBar.getSelectedMaxValue();

                filter(editTextName.getText().toString(), idCat, idProduct, tmpPriceMin, tmpPriceMax, checkBox.isChecked());
                mPopupWindow.dismiss();

            }
        });

        mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER, 0, 0);
        mPopupWindow.setFocusable(true);
        mPopupWindow.update();
    }



    public void getCurrentLocation() {
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location = null;
        if (!(isGPSEnabled || isNetworkEnabled)) {

        } else {
            if (isNetworkEnabled) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if (isGPSEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        if (location != null) {
            /*Logger.d(String.format("getCurrentLocation(%f, %f)", location.getLatitude(),
                    location.getLongitude()));*/

        }
    }


}
