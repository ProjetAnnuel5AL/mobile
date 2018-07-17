package com.lechampalamaison.loc.lechampalamaison.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lechampalamaison.loc.lechampalamaison.R;
import com.lechampalamaison.loc.lechampalamaison.api.model.User;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.FindAddressReponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.UpdateResponse;
import com.lechampalamaison.loc.lechampalamaison.api.service.UserClient;
import com.lechampalamaison.loc.lechampalamaison.api.utils.Configuration;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddressActivity extends AppCompatActivity {

    public static final String PREFS_NAME_USER = "USER";
    private static final String TAG = "addressActivity";
    User userSave = new User();
    public ProgressDialog progressDialog;
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Configuration.urlApi)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);


    @BindView(R.id.input_lastName)
    EditText _lastName;
    @BindView(R.id.input_firstName)
    EditText _firstName;
    @BindView(R.id.input_address)
    EditText _address;
    @BindView(R.id.checkBoxSexH)
    CheckBox _checkBoxSexH;
    @BindView(R.id.checkBoxSexF)
    CheckBox _checkBoxSexF;
    @BindView(R.id.input_city)
    EditText _city;
    @BindView(R.id.input_cp)
    EditText _cp;
    @BindView(R.id.btn_save_address)
    Button _btnSaveAddress;
    @BindView(R.id.isSaved)
    TextView _isSavedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);

        _btnSaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAddress();
            }
        });

        _checkBoxSexH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _checkBoxSexF.setChecked(false);
            }
        });
        _checkBoxSexF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _checkBoxSexH.setChecked(false);
            }
        });


        loadAddress();

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


    public void loadAddress(){
        //On charge l'adresse mail
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Récupération de votre adresse...");
        progressDialog.show();

        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME_USER , Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.token_key), "");
        String loginUser = sharedPref.getString(getString(R.string.login_key), "");

        Call<FindAddressReponse> call = userClient.findAddress(loginUser, token);

        call.enqueue(new Callback<FindAddressReponse>() {
            @Override
            public void onResponse(Call<FindAddressReponse> call, Response<FindAddressReponse> response) {
                if(response.body().getCode() == 0){
                    if(response.body().getResult().getLastNameUser() == null ||response.body().getResult().getLastNameUser().equals("")){
                        _isSavedView.setText("Pas encore d'adresse de livraison enregistré. Remplissez le formulaire ci-dessous pour en enregistrer une.");
                    }else{

                        userSave.setLastNameUser(response.body().getResult().getLastNameUser());
                        userSave.setFirstNameUser(response.body().getResult().getFirstNameUser());
                        userSave.setAddressUser(response.body().getResult().getAddressUser());
                        userSave.setCityUser(response.body().getResult().getCityUser());
                        userSave.setCpUser(response.body().getResult().getCpUser());
                        userSave.setSexUser(response.body().getResult().getSexUser());

                        _lastName.setText(userSave.getLastNameUser());
                        _firstName.setText(userSave.getFirstNameUser());
                        _address.setText(userSave.getAddressUser());
                        _city.setText(userSave.getCityUser());
                        _cp.setText(userSave.getCpUser());


                        if(response.body().getResult().getSexUser().equals("H")){
                            _checkBoxSexH.setChecked(true);

                        }else{
                            _checkBoxSexF.setChecked(true);
                        }

                    }
                    progressDialog.dismiss();
                }else{

                    Toast.makeText(getApplicationContext(), "Erreur lors du chargement de l'adresse", Toast.LENGTH_SHORT).show();
                    onUpdateAddressFailed();
                }
            }
            @Override
            public void onFailure(Call<FindAddressReponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                        Toast.LENGTH_SHORT).show();
                onUpdateAddressFailed();
            }
        });

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 2000);
    }

    public void updateAddress(){
        Log.d(TAG, "updateAddress");

        if (!validate()) {
            onUpdateAddressFailed();
            return;
        }

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Modification de l'adresse de livraison ...");
        progressDialog.show();

        String lastName = _lastName.getText().toString();
        String firstName = _firstName.getText().toString();
        String address = _address.getText().toString();
        String city = _city.getText().toString();
        String cp = _cp.getText().toString();
        String sex ="H";
        if(_checkBoxSexF.isChecked()){
            sex ="F";
        }

        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME_USER ,Context.MODE_PRIVATE);

        String token = sharedPref.getString(getString(R.string.token_key), "");
        String loginUser = sharedPref.getString(getString(R.string.login_key), "");

        User user = new User(loginUser, token, null, null, lastName, firstName, sex, address, city, cp);
        Call<UpdateResponse> call = userClient.update(user);

        call.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                if(response.body().getCode() == 0){
                    onUpdateAddressSuccess();
                    _isSavedView.setText("");
                    userSave.setLastNameUser(lastName);
                    userSave.setFirstNameUser(firstName);
                    userSave.setAddressUser(address);
                    userSave.setCityUser(city);
                    userSave.setCpUser(cp);

                    if(_checkBoxSexF.isChecked()){
                        userSave.setSexUser("H");
                    }else{
                        userSave.setSexUser("F");
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Erreur lors de la modification de l'adresse de livraison. Veuillez recommencer", Toast.LENGTH_SHORT).show();
                    onUpdateAddressFailed();
                }
            }
            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                        Toast.LENGTH_SHORT).show();
                onUpdateAddressFailed();
            }
        });


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 2000);
    }

    public void onUpdateAddressSuccess() {
        progressDialog.dismiss();
        this.setResult(this.RESULT_OK, null);
        Toast.makeText(getApplicationContext(), "Adresse de livraison enregistrée avec succès.", Toast.LENGTH_LONG).show();
        this.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void onUpdateAddressFailed() {


    }

    public boolean validate() {
        boolean valid = true;

        String lastName = _lastName.getText().toString();
        String firstName = _firstName.getText().toString();
        String address = _address.getText().toString();
        String city = _city.getText().toString();
        String cp = _cp.getText().toString();
        int cpInt = Integer.parseInt(cp);
        if(userSave.getLastNameUser() != null){
            if(userSave.getLastNameUser().equals(lastName) && userSave.getFirstNameUser().equals(firstName) && userSave.getAddressUser().equals(address)
                    && userSave.getCityUser().equals(city) && userSave.getCpUser().equals(cp)){
                Toast.makeText(getApplicationContext(), "Aucune modification détectée.", Toast.LENGTH_SHORT).show();
                valid = false;
            }

        }

        if (lastName.isEmpty()) {
            _lastName.setError("Veuillez entrer un nom.");
            valid = false;
        } else {
            _lastName.setError(null);
        }
        if (firstName.isEmpty()) {
            _firstName.setError("Veuillez entrer un prénom.");
            valid = false;
        } else {
            _firstName.setError(null);
        }
        if (address.isEmpty()) {
            _address.setError("Veuillez entrer une adresse.");
            valid = false;
        } else {
            _address.setError(null);
        }
        if (city.isEmpty()) {
            _city.setError("Veuillez entrer une ville.");
            valid = false;
        } else {
            _city.setError(null);
        }
        if (cp.isEmpty() || cp.length()>5) {
            _cp.setError("Veuillez entrer un code postal valide.");
            valid = false;
        } else {
            _cp.setError(null);
        }

        if(_checkBoxSexF.isChecked() == false && _checkBoxSexH.isChecked() == false){
            _checkBoxSexF.setError("Veuillez renseigner votre sexe.");
            valid = false;
        }else{
            _checkBoxSexF.setError(null);
        }

        return valid;
    }

}
