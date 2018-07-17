package com.lechampalamaison.loc.lechampalamaison.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
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
import com.lechampalamaison.loc.lechampalamaison.Model.Event;
import com.lechampalamaison.loc.lechampalamaison.Model.Producer;
import com.lechampalamaison.loc.lechampalamaison.R;
import com.lechampalamaison.loc.lechampalamaison.api.model.Subscribe;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.CheckFounderResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.CollectiviteResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ProducerGroupEventResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ProducerGroupMemberResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.ProducerGroupSubscriberCheckResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.SubscribeResponse;
import com.lechampalamaison.loc.lechampalamaison.api.service.ProducerGroupClient;
import com.lechampalamaison.loc.lechampalamaison.api.utils.Configuration;
import com.lechampalamaison.loc.lechampalamaison.listarrayadaper.ListEventArrayAdapter;
import com.lechampalamaison.loc.lechampalamaison.listarrayadaper.ListProducerArrayAdapter;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CollectiviteActivity extends AppCompatActivity implements OnMapReadyCallback {

    private int idProducerGroup;
    public static final String PREFS_NAME_USER = "USER";

    boolean sub = false;

    ListView listViewProducer;
    ListProducerArrayAdapter adapterProducer;
    ArrayList<Producer> listProducer;

    ListView listViewEvent;
    ListEventArrayAdapter adapterEvent;
    ArrayList<Event> listEvent;

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Configuration.urlApi)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    ProducerGroupClient producerGroupClient = retrofit.create(ProducerGroupClient.class);

    ImageView imageViewGroupProducer;
    TextView textViewTitreGroupProducer;
    TextView textViewDescGroupProducer;
    TextView textViewAddressGroupProducer;
    TextView textViewEmailGroupProducer;
    TextView textViewTelGroupProducer;

    ImageButton imageButtonSub;

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
        setContentView(R.layout.activity_collectivite);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle mapViewBundle = null;
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        final Intent intent = getIntent();
        idProducerGroup  = Integer.parseInt(intent.getStringExtra("id"));

        imageViewGroupProducer = (ImageView) findViewById(R.id.imageViewGroupProducer);
        textViewTitreGroupProducer = (TextView) findViewById(R.id.textViewTitreGroupProducer);
        textViewDescGroupProducer = (TextView) findViewById(R.id.textViewDescGroupProducer);
        textViewAddressGroupProducer = (TextView) findViewById(R.id.textViewAddressGroupProducer);
        textViewEmailGroupProducer = (TextView) findViewById(R.id.textViewEmailGroupProducer);
        textViewTelGroupProducer = (TextView) findViewById(R.id.textViewTelGroupProducer);

        listProducer = new ArrayList<>();
        listViewProducer = findViewById(R.id.listProducer);
        adapterProducer = new ListProducerArrayAdapter(this, listProducer);
        listViewProducer.setAdapter(adapterProducer);

        listViewProducer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Intent intentFiche = new Intent(getApplicationContext(), FicheProducerActivity.class);
                int id  = listProducer.get(position).getIdProducer();
                intentFiche.putExtra("idProducer",String.valueOf(id));
                startActivityForResult(intentFiche, 0);

            }
        });

        listEvent = new ArrayList<>();
        listViewEvent = findViewById(R.id.listEvent);
        adapterEvent = new ListEventArrayAdapter(this, listEvent);
        listViewEvent.setAdapter(adapterEvent);

        listViewEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Intent intentFiche = new Intent(getApplicationContext(), EventActivity.class);
                int id  = listEvent.get(position).getId();
                intentFiche.putExtra("idEvent",String.valueOf(id));
                startActivityForResult(intentFiche, 0);

            }
        });

        imageButtonSub = (ImageButton) findViewById(R.id.imageButtonSub);

        //on check if subscriber
        imageButtonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME_USER , Context.MODE_PRIVATE);
                String token = sharedPref.getString(getString(R.string.token_key), "");

                Call<SubscribeResponse> call;
                if(sub == false){

                    call = producerGroupClient.producersGroupSubscriber(new Subscribe(idProducerGroup,token));
                    call.enqueue(new Callback<SubscribeResponse>() {
                        @Override
                        public void onResponse(Call<SubscribeResponse> call, Response<SubscribeResponse> response) {
                            if (response.body().getCode() == 0) {
                                Toast.makeText(getApplicationContext(), "Vous vous êtes abonnés au fil d'actualité de ce groupe.",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                            }
                        }
                        @Override
                        public void onFailure(Call<SubscribeResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    sub = true;
                }else{
                    call = producerGroupClient.producersGroupUnsubscriber(new Subscribe(idProducerGroup,token));
                    call.enqueue(new Callback<SubscribeResponse>() {
                        @Override
                        public void onResponse(Call<SubscribeResponse> call, Response<SubscribeResponse> response) {
                            if (response.body().getCode() == 0) {
                                Toast.makeText(getApplicationContext(), "Vous vous êtes désabonnés au fil d'actualité de ce groupe.",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                            }
                        }
                        @Override
                        public void onFailure(Call<SubscribeResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    sub = false;
                }
            }
        });

        loadSubscribe();
        loadGroup();
        loadEvent();

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

    public void loadGroup(){


        Call<CollectiviteResponse> call;
        call = producerGroupClient.producersGroupIdGroup(idProducerGroup);

        call.enqueue(new Callback<CollectiviteResponse>() {
            @Override
            public void onResponse(Call<CollectiviteResponse> call, Response<CollectiviteResponse> response) {
                if (response.body().getCode() == 0) {

                    String urlImg = Configuration.urlApi+"groupAvatar/"+idProducerGroup+"/"+response.body().getResult().getAvatar();
                    Picasso.get().load(urlImg).into(imageViewGroupProducer);

                    textViewTitreGroupProducer.setText(response.body().getResult().getName());
                    textViewDescGroupProducer.setText(response.body().getResult().getDescription());
                    textViewAddressGroupProducer.setText(response.body().getResult().getAdress());
                    textViewEmailGroupProducer.setText(response.body().getResult().getEmail());
                    textViewTelGroupProducer.setText(response.body().getResult().getPhone());
                    setMapItem(response.body().getResult().getLatGroup(),response.body().getResult().getLongGroup());

                    SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME_USER , Context.MODE_PRIVATE);
                    String loginUser = sharedPref.getString(getString(R.string.login_key), "");

                    listProducer.add(new Producer(response.body().getResult().getIdProducer(), response.body().getResult().getLastNameProducer(),
                            response.body().getResult().getFirstNameProducer(), response.body().getResult().getCpProducer(), "Fondateur",
                            response.body().getResult().getAvatarProducer()));

                    loadProducerMember();



                }else{

                }
            }
            @Override
            public void onFailure(Call<CollectiviteResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadProducerMember() {

        Call<ProducerGroupMemberResponse> call;
        call = producerGroupClient.producersGroupMember(idProducerGroup);

        call.enqueue(new Callback<ProducerGroupMemberResponse>() {
            @Override
            public void onResponse(Call<ProducerGroupMemberResponse> call, Response<ProducerGroupMemberResponse> response) {
                if (response.body().getCode() == 0) {

                    for (int i =0; i<response.body().getResult().length; i++){

                        listProducer.add(new Producer(response.body().getResult()[i].getIdProducer(), response.body().getResult()[i].getLastNameProducer(),
                                response.body().getResult()[i].getFirstNameProducer(), response.body().getResult()[i].getCpProducer(), "Membre",
                                response.body().getResult()[i].getAvatarProducer()));


                    }
                    adapterProducer.notifyDataSetChanged();

                } else {

                }
            }

            @Override
            public void onFailure(Call<ProducerGroupMemberResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadEvent(){

        Call<ProducerGroupEventResponse> call;
        call = producerGroupClient.producersGroupEvent(idProducerGroup);

        call.enqueue(new Callback<ProducerGroupEventResponse>() {
            @Override
            public void onResponse(Call<ProducerGroupEventResponse> call, Response<ProducerGroupEventResponse> response) {
                if (response.body().getCode() == 0) {

                    for (int i =0; i<response.body().getResult().length; i++){
                        listEvent.add(new Event(response.body().getResult()[i].getIdEvent(), response.body().getResult()[i].getNameEvent(),
                                response.body().getResult()[i].getDescriptionEvent(), response.body().getResult()[i].getDateEvent()));
                      

                    }
                    adapterEvent.notifyDataSetChanged();

                } else {

                }
            }

            @Override
            public void onFailure(Call<ProducerGroupEventResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void loadSubscribe(){
        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME_USER , Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.token_key), "");
        String loginUser = sharedPref.getString(getString(R.string.login_key), "");
        //Si on est producteur membre du groupe on ne peut pas subscribe

        Call<SubscribeResponse> call;
        call = producerGroupClient.producerGroupMemberCheck(idProducerGroup, token);

        call.enqueue(new Callback<SubscribeResponse>() {
            @Override
            public void onResponse(Call<SubscribeResponse> call, Response<SubscribeResponse> response) {
                if (response.body().getCode() == 0 ) {
                    imageButtonSub.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<SubscribeResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                        Toast.LENGTH_SHORT).show();
            }
        });


        Call<CheckFounderResponse> call3;
        call3 = producerGroupClient.producerGroupFounderCheck(loginUser, token);

        call3.enqueue(new Callback<CheckFounderResponse>() {
            @Override
            public void onResponse(Call<CheckFounderResponse> call, Response<CheckFounderResponse> response) {
                if (response.body().getCode() == 0) {
                    //On vérifie si un des groupe à l'id de cette page
                    for (int i =0; i< response.body().getResult().length; i++){
                        if (idProducerGroup == response.body().getResult()[i].getId()){
                            imageButtonSub.setVisibility(View.INVISIBLE);
                            break;
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<CheckFounderResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                        Toast.LENGTH_SHORT).show();
            }
        });


        Call<ProducerGroupSubscriberCheckResponse> call2;
        call2 = producerGroupClient.producerGroupSubscriberCheck(idProducerGroup, token);

        call2.enqueue(new Callback<ProducerGroupSubscriberCheckResponse>() {
            @Override
            public void onResponse(Call<ProducerGroupSubscriberCheckResponse> call, Response<ProducerGroupSubscriberCheckResponse> response) {
                if (response.body().getCode() == 0 ) {
                    //mettre le bon logo
                    sub= true;
                } else {
                    sub = false;
                }
            }

            @Override
            public void onFailure(Call<ProducerGroupSubscriberCheckResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                        Toast.LENGTH_SHORT).show();
            }
        });



    }
}
