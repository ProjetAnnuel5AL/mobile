package com.lechampalamaison.loc.lechampalamaison.Activity;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lechampalamaison.loc.lechampalamaison.Model.Comment;
import com.lechampalamaison.loc.lechampalamaison.R;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ProducerResponse;
import com.lechampalamaison.loc.lechampalamaison.api.service.ItemClient;
import com.lechampalamaison.loc.lechampalamaison.api.service.ProducerClient;
import com.lechampalamaison.loc.lechampalamaison.api.utils.Configuration;
import com.lechampalamaison.loc.lechampalamaison.listarrayadaper.ListCommentArrayAdapter;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FicheProducerActivity extends AppCompatActivity implements OnMapReadyCallback {

    private int idProducer;

    ListView listViewComment;
    ListCommentArrayAdapter adapterComment;
    ArrayList<Comment> listComment;

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Configuration.urlApi)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    ProducerClient producerClient = retrofit.create(ProducerClient.class);

    private MapView mapView;
    private GoogleMap gmap;

    public ImageView imageViewProducer;

    public TextView textViewLoginProducer;
    public TextView textViewNameProducer;
    public TextView textViewEvaluationProducer;
    public TextView textViewDescProducer;
    public TextView textViewAddressProducer;

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
        setContentView(R.layout.activity_fiche_producer);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        final Intent intent = getIntent();
        idProducer  = Integer.parseInt(intent.getStringExtra("idProducer"));

        listComment = new ArrayList<>();
        listViewComment = findViewById(R.id.listComment);
        adapterComment = new ListCommentArrayAdapter(this, listComment);
        listViewComment.setAdapter(adapterComment);

        imageViewProducer = (ImageView) findViewById(R.id.imageViewProducer);
        textViewLoginProducer = (TextView) findViewById(R.id.textViewLoginProducer);
        textViewNameProducer = (TextView) findViewById(R.id.textViewNameProducer);
        textViewEvaluationProducer = (TextView) findViewById(R.id.textViewEvaluationProducer);
        textViewDescProducer = (TextView) findViewById(R.id.textViewDescProducer);
        textViewAddressProducer = (TextView) findViewById(R.id.textViewAddressProducer);

        Bundle mapViewBundle = null;
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        loadFicheProducer();

    }

    public void loadFicheProducer(){

        Call<ProducerResponse> call;
        call = producerClient.producerGetPublicInformations(idProducer);
        call.enqueue(new Callback<ProducerResponse>() {
            @Override
            public void onResponse(Call<ProducerResponse> call, Response<ProducerResponse> response) {
                if(response.body().getCode() == 0){

                    String[] ext =  response.body().getResult().getAvatarProducer().split("\\.");
                    String urlImg = Configuration.urlApi+"producerAvatar/"+idProducer+"/img_resize/avatar_small."+ext[ext.length-1];
                    Picasso.get().load(urlImg).into(imageViewProducer);
                    textViewLoginProducer.setText(response.body().getResult().getLoginUser());
                    textViewNameProducer.setText(response.body().getResult().getFirstNameProducer() +" " +response.body().getResult().getLastNameProducer());
                    textViewDescProducer.setText(response.body().getResult().getDescriptionProducer());
                    textViewAddressProducer.setText(response.body().getResult().getAddressProducer() );

                    String strEval ="";
                    int nbComment = response.body().getResult().getComment().length;
                    Double totalStar = 0.0;
                    for(int i =0; i<nbComment; i++){
                        totalStar+=response.body().getResult().getComment()[i].getStarComment();
                    }
                    Double moyenne;
                    if(nbComment>0){
                        moyenne = ((totalStar/(nbComment*5))*5);

                        Double eval = (double) Math.round(moyenne * 100) / 100;
                        NumberFormat format= NumberFormat.getInstance();
                        format.setMinimumFractionDigits(1);
                        strEval =  format.format(eval) + " Etoile(s) ("+nbComment+" évaluations).";
                    }else{

                        strEval ="Pas encore d'évaluation.";

                    }
                    textViewEvaluationProducer.setText(strEval);

                    String[] latlng= response.body().getResult().getLocationProducer().split(",");
                    Double lat =  Double.parseDouble(latlng[0]);
                    Double lng =  Double.parseDouble(latlng[1]);
                    setMapItem(lat,lng, response.body().getResult().getAddressProducer(),"", "");


                    for (int i = 0; i<response.body().getResult().getComment().length; i++){
                        listComment.add(new Comment(response.body().getResult().getComment()[i].getDateComment(),
                                response.body().getResult().getComment()[i].getLoginUser(), response.body().getResult().getComment()[i].getStarComment(), response.body().getResult().getComment()[i].getComment()));
                    }
                    adapterComment.notifyDataSetChanged();
                }else{

                }
            }
            @Override
            public void onFailure(Call<ProducerResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                        Toast.LENGTH_SHORT).show();
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
