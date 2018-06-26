package com.lechampalamaison;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.lechampalamaison.api.model.AuthResponse;
import com.lechampalamaison.api.model.Login;
import com.lechampalamaison.api.service.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Authentification extends AppCompatActivity {

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://vps536743.ovh.net:8888/")
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();

    UserClient userClient = retrofit.create(UserClient.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentification);


        findViewById(R.id.connexion_button).setOnClickListener((view) -> login());
    }

    private void login(){
        Login login = new Login("iplowplow", "Test1234'");
        Call<AuthResponse> call = userClient.login(login);

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.body().getCode() == 0){
                    Toast.makeText(Authentification.this, response.body().getResult().getToken(), Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(Authentification.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(Authentification.this, "Error :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
