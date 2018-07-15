package com.lechampalamaison.loc.lechampalamaison.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lechampalamaison.loc.lechampalamaison.Model.Event;
import com.lechampalamaison.loc.lechampalamaison.Model.Producer;
import com.lechampalamaison.loc.lechampalamaison.R;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.EventResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ProducerGroupEventParticiantResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ProducerGroupEventResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ProducerGroupMemberResponse;
import com.lechampalamaison.loc.lechampalamaison.api.service.ProducerGroupClient;
import com.lechampalamaison.loc.lechampalamaison.api.utils.Configuration;
import com.lechampalamaison.loc.lechampalamaison.listarrayadaper.ListProducerEventArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventActivity extends AppCompatActivity  implements OnMapReadyCallback {

    private int idEvent;
    public static final String PREFS_NAME_USER = "USER";

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Configuration.urlApi)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    ProducerGroupClient producerGroupClient = retrofit.create(ProducerGroupClient.class);

    ListView listViewProducerEvent;
    ListProducerEventArrayAdapter adapterProducerEvent;
    ArrayList<Producer> listProducer;

    TextView textViewTitleEvent;
    TextView textViewDateEvent;
    TextView textViewDescEvent;
    TextView textViewAddressEvent;

    private MapView mapView;
    private GoogleMap gmap;

    @Override
    public void onMapReady(GoogleMap map) {
        gmap = map;
    }

    public void setMapItem(Double lat, Double lng ){
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting the position for the marker
        markerOptions.position(new LatLng(lat, lng));

        //markerOptions.title(address);
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));
        gmap.addMarker(markerOptions);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        final Intent intent = getIntent();
        idEvent  = Integer.parseInt(intent.getStringExtra("idEvent"));

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle mapViewBundle = null;
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);


        textViewTitleEvent = (TextView) findViewById(R.id.textViewTitleEvent);
        textViewDateEvent = (TextView) findViewById(R.id.textViewDateEvent);
        textViewDescEvent = (TextView) findViewById(R.id.textViewDescEvent);
        textViewAddressEvent = (TextView) findViewById(R.id.textViewAddressEvent);


        listProducer = new ArrayList<>();
        listViewProducerEvent = findViewById(R.id.listProducerEvent);
        adapterProducerEvent = new ListProducerEventArrayAdapter(this, listProducer);
        listViewProducerEvent.setAdapter(adapterProducerEvent);

        listViewProducerEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Intent intentFiche = new Intent(getApplicationContext(), FicheProducerActivity.class);
                int id  = listProducer.get(position).getIdProducer();
                intentFiche.putExtra("idProducer",String.valueOf(id));
                startActivityForResult(intentFiche, 0);

            }
        });

        loadEvent();
        loadProducer();

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

    public void loadProducer(){

        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME_USER , Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.token_key), "");

        Call<ProducerGroupEventParticiantResponse> call;
        call = producerGroupClient.producersGroupEventParticipant(idEvent, token);

        call.enqueue(new Callback<ProducerGroupEventParticiantResponse>() {
            @Override
            public void onResponse(Call<ProducerGroupEventParticiantResponse> call, Response<ProducerGroupEventParticiantResponse> response) {
                if (response.body().getCode() == 0) {

                    for(int i =0; i<response.body().getResult().length; i++){
                        listProducer.add(new Producer(response.body().getResult()[i].getIdProducer(), response.body().getResult()[i].getLastNameProducer(),
                                response.body().getResult()[i].getFirstNameProducer(), response.body().getResult()[i].getCpProducer(), response.body().getResult()[i].getLibelleParticipant(),
                                response.body().getResult()[i].getAvatarProducer()));
                    }

                    adapterProducerEvent.notifyDataSetChanged();

                } else {

                }
            }

            @Override
            public void onFailure(Call<ProducerGroupEventParticiantResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void loadEvent(){

        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME_USER , Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.token_key), "");

        Call<EventResponse> call;
        call = producerGroupClient.event(idEvent, token);

        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if (response.body().getCode() == 0) {


                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                long time = response.body().getResult().getDateEvent().getTime();
                Date date = new Date(time);
                textViewDateEvent.setText("Date de l'évenement : " + dateFormat.format(date));

                textViewTitleEvent.setText(response.body().getResult().getNameEvent());
                textViewDescEvent.setText(response.body().getResult().getDescriptionEvent());
                textViewAddressEvent.setText(response.body().getResult().getAdressEvent());

                setMapItem(response.body().getResult().getLatEvent(),response.body().getResult().getLongEvent());

                } else {

                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }


}
