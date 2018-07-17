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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lechampalamaison.loc.lechampalamaison.R;
import com.lechampalamaison.loc.lechampalamaison.api.model.User;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.FindEmailResponse;
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

public class ProfilActivity extends AppCompatActivity {


    public static final String PREFS_NAME_USER = "USER";
    private static final String TAG = "profilFragment";


    public ProgressDialog progressDialog;
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Configuration.urlApi)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);


    @BindView(R.id.input_mail_new)
    EditText _mailNewText;
    @BindView(R.id.input_reEnterMail) EditText _reEnterMailText;
    @BindView(R.id.btn_update_mail)Button _btn_update_mail;

    @BindView(R.id.loginLoadTextView)
    TextView _loginLoadTextView;
    @BindView(R.id.mailLoadTextView) TextView _mailLoadTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);

        _btn_update_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMail();
            }
        });
        loadProfil();

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


    public void loadProfil(){
        //On charge l'adresse mail
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Chargement du profil...");
        progressDialog.show();

        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME_USER , Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.token_key), "");
        String loginUser = sharedPref.getString(getString(R.string.login_key), "");

        Call<FindEmailResponse> call = userClient.findEmail(loginUser, token);

        call.enqueue(new Callback<FindEmailResponse>() {
            @Override
            public void onResponse(Call<FindEmailResponse> call, Response<FindEmailResponse> response) {
                if(response.body().getCode() == 0){
                    _loginLoadTextView.setText(loginUser);
                    _mailLoadTextView.setText(response.body().getResult().getEmailUser());
                    progressDialog.dismiss();
                }else{
                    Toast.makeText(getApplicationContext(), "Erreur lors du chargment du profil", Toast.LENGTH_SHORT).show();
                    onUpdateMailFailed();
                }
            }
            @Override
            public void onFailure(Call<FindEmailResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                        Toast.LENGTH_SHORT).show();
                onUpdateMailFailed();
            }
        });

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 2000);
    }

    public void updateMail(){
        Log.d(TAG, "updatePwd");

        if (!validate()) {
            onUpdateMailFailed();
            return;
        }

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Modification de votre adresse mail ...");
        progressDialog.show();

        String mail = _mailNewText.getText().toString();

        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME_USER ,Context.MODE_PRIVATE);

        String token = sharedPref.getString(getString(R.string.token_key), "");
        String loginUser = sharedPref.getString(getString(R.string.login_key), "");

        User user = new User(loginUser, token, null, mail, null, null, null, null, null, null);
        Call<UpdateResponse> call = userClient.update(user);

        call.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                if(response.body().getCode() == 0){
                    onUpdateMailSuccess();
                    _mailLoadTextView.setText(_mailNewText.getText());
                    _mailNewText.setText("");
                    _reEnterMailText.setText("");
                }else{
                    Toast.makeText(getApplicationContext(), "Erreur lors de la modification de l'adresse mail. Veuillez recommencer", Toast.LENGTH_SHORT).show();
                    onUpdateMailFailed();
                }
            }
            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                        Toast.LENGTH_SHORT).show();
                onUpdateMailFailed();
            }
        });


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 2000);
    }

    public void onUpdateMailSuccess() {
        progressDialog.dismiss();
        setResult(RESULT_OK, null);
        Toast.makeText(getApplicationContext(), "Adresse mail modifiée avec succès.", Toast.LENGTH_LONG).show();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void onUpdateMailFailed() {


    }

    public boolean validate() {
        boolean valid = true;

        String mail = _mailNewText.getText().toString();
        String reEnterMail = _reEnterMailText.getText().toString();

        if (mail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            _mailNewText.setError("Veuillez entrer une adresse mail valide.");
            valid = false;
        } else {
            _mailNewText.setError(null);
        }

        if (reEnterMail.isEmpty() || !(reEnterMail.equals(mail))) {
            _reEnterMailText.setError("Les adresses mail saisient ne sont pas identiques.");
            valid = false;
        } else {
            _reEnterMailText.setError(null);
        }

        return valid;
    }
}
