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
import android.widget.Toast;

import com.lechampalamaison.loc.lechampalamaison.R;
import com.lechampalamaison.loc.lechampalamaison.api.model.Login;
import com.lechampalamaison.loc.lechampalamaison.api.model.User;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.AuthResponse;
import com.lechampalamaison.loc.lechampalamaison.api.model.apiResponse.UpdateResponse;
import com.lechampalamaison.loc.lechampalamaison.api.service.UserClient;
import com.lechampalamaison.loc.lechampalamaison.api.utils.Configuration;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdatePasswordActivity extends AppCompatActivity {

    private static final String TAG = "UpdatePwdFragment";
    public static final String PREFS_NAME_USER = "USER";
    public ProgressDialog progressDialog;
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Configuration.urlApi)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);


    @BindView(R.id.input_password_old)
    EditText _oldPwdText;
    @BindView(R.id.input_password_new) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword_new) EditText _reEnterPasswordText;
    @BindView(R.id.btn_update_pwd)
    Button _btn_update_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(getApplicationContext(),
                R.style.AppTheme_Dark_Dialog);

        _btn_update_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePwd();
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


    public void updatePwd(){
        Log.d(TAG, "updatePwd");

        if (!validate()) {
            onUpdatePwdFailed();
            return;
        }


        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Modification du mot de passe ...");
        progressDialog.show();

        String odlPwd = _oldPwdText.getText().toString();
        String password = _passwordText.getText().toString();

        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME_USER , Context.MODE_PRIVATE);

        String token = sharedPref.getString(getString(R.string.token_key), "");
        String loginUser = sharedPref.getString(getString(R.string.login_key), "");

        Login login = new Login(loginUser, odlPwd);
        Call<AuthResponse> call = userClient.login(login);

        User user = new User(loginUser, token, password, null, null, null, null, null, null, null);
        Call<UpdateResponse> call2 = userClient.update(user);

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if(response.body().getCode() == 0){

                    call2.enqueue(new Callback<UpdateResponse>() {
                        @Override
                        public void onResponse(Call<UpdateResponse> call2, Response<UpdateResponse> response) {
                            if(response.body().getCode() == 0) {
                                onUpdatePwdSuccess();
                                _oldPwdText.setText("");
                                _passwordText.setText("");
                                _reEnterPasswordText.setText("");
                            }else{
                                Toast.makeText(getApplicationContext(), "Erreur lors de la modification de votre mot de passe. Veuillez recommencer", Toast.LENGTH_SHORT).show();
                                onUpdatePwdFailed();
                            }

                        }

                        @Override
                        public void onFailure(Call<UpdateResponse> call2, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                                    Toast.LENGTH_SHORT).show();
                            onUpdatePwdFailed();
                        }

                    });

                }else{
                    Toast.makeText(getApplicationContext(), "L'ancien mot de passe est incorrect.", Toast.LENGTH_SHORT).show();
                    onUpdatePwdFailed();
                }
            }
            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Impossible de se connecter à internet. Merci de vérifier votre connexion.",
                        Toast.LENGTH_SHORT).show();
                onUpdatePwdFailed();
            }
        });


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 2000);
    }

    public void onUpdatePwdSuccess() {

        progressDialog.dismiss();
        setResult(RESULT_OK, null);
        Toast.makeText(getApplicationContext(), "Mot de passe modifié avec succès.", Toast.LENGTH_LONG).show();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void onUpdatePwdFailed() {


    }

    public boolean validate() {
        boolean valid = true;

        String oldPwd = _oldPwdText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (oldPwd.isEmpty()) {
            _oldPwdText.setError("Saisisez votre mot de passe actuel");
            valid = false;
        } else {
            _oldPwdText.setError(null);
        }

        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[&\"'(\\-_)=+?\\.^$])(?=\\S+$).{8,}$";


        if (password.isEmpty() || password.length() < 8 || Pattern.matches(regex, password) == false ) {
            _passwordText.setError("Mot de passe invalide : 8 caractères minimum, au moins une majuscule et une minuscule, un chiffre et un caractère spéciale ( parmis les suivants : &\\\"'(-_)=+?.^$ ) sont requis ! ");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Les mots de passe saisient ne sont pas identiques.");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
}
