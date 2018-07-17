package com.lechampalamaison.loc.lechampalamaison.Activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lechampalamaison.loc.lechampalamaison.Fragment.CartFragment;
import com.lechampalamaison.loc.lechampalamaison.Model.Cart;
import com.lechampalamaison.loc.lechampalamaison.Model.CartItem;
import com.lechampalamaison.loc.lechampalamaison.Model.Item;
import com.lechampalamaison.loc.lechampalamaison.R;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ItemResponse;
import com.lechampalamaison.loc.lechampalamaison.api.service.ItemClient;
import com.lechampalamaison.loc.lechampalamaison.api.utils.Configuration;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.lechampalamaison.loc.lechampalamaison.Activity.LoginActivity.PREFS_NAME_USER;

public class ItemActivity extends AppCompatActivity  implements OnMapReadyCallback {

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Configuration.urlApi)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    ItemClient itemClient = retrofit.create(ItemClient.class);

    SharedPreferences sharedpreferences;

    //champ pour pannier
    public double priceItem = 0.0;

    public int id;
    public int qteItem;
    public Double qteMaxItem;
    public String unitItem;
    public String categoryItem;
    public String productItem;
    public String titleItem;
    public double shippingCostItem;
    public String deliveryTimeItem;
    public int idDeliveryItem;
    public String descriptionItem;
    public String localisationItem;

    public TextView textViewTitre;
    public TextView catProductItem;
    public TextView producerInfoItem;
    public TextView textViewPrice;
    public TextView textViewDesc;
    public TextView textViewPricePort;

    private MapView mapView;
    private GoogleMap gmap;

    @Override
    public void onMapReady(GoogleMap map) {
        gmap = map;
    }

    public void setMapItem(Double lat, Double lng, String address, String city, String cp){
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting the position for the marker
        markerOptions.position(new LatLng(lat, lng));

        markerOptions.title(address);
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));
        gmap.addMarker(markerOptions);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Slider slider = findViewById(R.id.slider);

        final Intent intent = getIntent();
        int idItem  = Integer.parseInt(intent.getStringExtra("id"));
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        textViewTitre = (TextView) findViewById(R.id.textViewTitre);
        catProductItem = (TextView) findViewById(R.id.catProductItem);
        producerInfoItem = (TextView) findViewById(R.id.producerInfoItem);
        textViewPrice = (TextView) findViewById(R.id.textViewPrice);
        textViewPricePort = (TextView) findViewById(R.id.textViewPricePort);
        textViewDesc = (TextView) findViewById(R.id.textViewDesc);
        List<Slide> slideList = new ArrayList<>();

        Bundle mapViewBundle = null;
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Call<ItemResponse> call;
        call = itemClient.item(idItem);

        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                if(response.body().getCode() == 0){
                    textViewTitre.setText(response.body().getResult().getInfoItem().getNameItem());
                    catProductItem.setText(response.body().getResult().getInfoItem().getNameCategory()+", "+response.body().getResult().getInfoItem().getNameProduct());

                    int nbComment = response.body().getResult().getStars().length;
                    Double totalStar = 0.0;
                    for(int i =0; i<nbComment; i++){
                        totalStar+= response.body().getResult().getStars()[i].getStarComment();
                    }
                    Double moyenne = 0.0;
                    if(nbComment>0){
                        moyenne = ((totalStar/(nbComment*5))*5);
                        NumberFormat format2= NumberFormat.getInstance();
                        format2.setMinimumFractionDigits(1);
                        producerInfoItem.setText(response.body().getResult().getInfoItem().getLoginUser() +" ("+format2.format(moyenne)+" étoiles)");
                    }else{
                        producerInfoItem.setText(response.body().getResult().getInfoItem().getLoginUser() +" (Pas encore d'évaluation)");
                    }

                    producerInfoItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            Intent intentFiche = new Intent(getApplicationContext(), FicheProducerActivity.class);
                            int id  = response.body().getResult().getInfoItem().getIdProducer();
                            intentFiche.putExtra("idProducer",String.valueOf(response.body().getResult().getInfoItem().getIdProducer()));

                            startActivityForResult(intentFiche, 0);
                            //finish();
                            //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        }
                    });

                    String[] latlng= response.body().getResult().getInfoItem().getLocationItem().split(",");
                    Double lat =  Double.parseDouble(latlng[0]);
                    Double lng =  Double.parseDouble(latlng[1]);
                    setMapItem(lat,lng, response.body().getResult().getInfoItem().getAddressItem(), response.body().getResult().getInfoItem().getCityItem(), response.body().getResult().getInfoItem().getCpItem());
                    Double price = (double) Math.round(response.body().getResult().getInfoItem().getPriceItem() * 100) / 100;

                    Double shippingCost = (double) Math.round(response.body().getResult().getInfoItem().getShippingCostItem() * 100) / 100;
                    NumberFormat format = NumberFormat.getInstance();
                    format.setMinimumFractionDigits(2);
                    textViewPrice.setText(format.format(price)  + "€");
                    textViewPricePort.setText("Frais de port :" +format.format(shippingCost)  + "€");

                    textViewDesc.setText(response.body().getResult().getInfoItem().getDescriptionItem());
                    String[] ext = response.body().getResult().getInfoItem().getFileExtensionsItem().split(";");
                    for(int i = 0; i<ext.length; i++){
                        String url = Configuration.urlApi+"itemPhotos/"+idItem+"/"+i+"_ms.jpg";

                        slideList.add(new Slide(i,Configuration.urlApi+"itemPhotos/"+idItem+"/img_resize/"+i+"_ms."+ext[i] , getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
                    }

                    slider.addSlides(slideList);

                    //on set les champ pannier :

                    priceItem = price;
                    qteItem = 1;
                    qteMaxItem = response.body().getResult().getInfoItem().getQuatityMaxOrderItem();
                    unitItem = response.body().getResult().getInfoItem().getNameUnit();
                    categoryItem =  response.body().getResult().getInfoItem().getNameCategory();
                    productItem = response.body().getResult().getInfoItem().getNameProduct();
                    titleItem = response.body().getResult().getInfoItem().getNameItem();
                    shippingCostItem = response.body().getResult().getInfoItem().getShippingCostItem();
                    deliveryTimeItem = response.body().getResult().getInfoItem().getDeliveryTimeItem();
                    descriptionItem =  response.body().getResult().getInfoItem().getDescriptionItem();
                    localisationItem = response.body().getResult().getInfoItem().getLocationItem();
                    idDeliveryItem = response.body().getResult().getInfoItem().getIdDeliveryItem();


                    System.out.println("test");
                }else{

                }
            }
            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                        Toast.LENGTH_SHORT).show();
            }
        });


        ImageButton addItem = findViewById(R.id.imageButton);

        addItem.setOnClickListener((View v) -> {

            //on vérifie que pas dans le panier;
            sharedpreferences = getSharedPreferences(PREFS_NAME_USER, Context.MODE_PRIVATE);
            String jsonCartSaved = sharedpreferences.getString("cart", null);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<CartItem>>() {}.getType();
            List<CartItem> itemExistant = gson.fromJson(jsonCartSaved, type);

            boolean isInCart = false;
            if (itemExistant != null){
                for (int i = 0; i < itemExistant.size(); i++) {
                    if (itemExistant.get(i).getItem().getId() == idItem) {
                        isInCart = true;
                        break;
                    }
                }
            }

            if(isInCart == true){
                Toast.makeText(this, "Produit déjà dans le pannier. Rdv sur dans le pannier pour modifier la quantité.", Toast.LENGTH_SHORT).show();
            }else{
                 CartItem item1 = new CartItem(new Item(null, idItem, qteItem, qteMaxItem, unitItem, categoryItem, productItem, titleItem, priceItem, priceItem, shippingCostItem, idDeliveryItem, deliveryTimeItem, descriptionItem, localisationItem, "")
                         , 1);

                String jsonCart;
                List<CartItem> itemList = new ArrayList<>();

                if (CartFragment.itemList != null) {
                    CartFragment.itemList.add(item1);
                    jsonCart = gson.toJson(CartFragment.itemList);
                } else {
                    itemList.add(item1);
                    jsonCart = gson.toJson(itemList);
                }

                SharedPreferences sharedPref = ItemActivity.this.getSharedPreferences(PREFS_NAME_USER, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString("cart", jsonCart);
                editor.apply();
                Toast.makeText(this, "Produit ajouté au pannier.", Toast.LENGTH_SHORT).show();
            }

        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
