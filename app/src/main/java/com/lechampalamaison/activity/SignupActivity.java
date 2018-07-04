package com.lechampalamaison.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.lechampalamaison.R;
import com.lechampalamaison.api.model.apiResponse.SignupResponse;
import com.lechampalamaison.api.model.Signup;
import com.lechampalamaison.api.service.UserClient;
import com.lechampalamaison.api.utils.Configuration;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    public ProgressDialog progressDialog;
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Configuration.urlApi)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);

    @BindView(R.id.input_login) EditText _loginText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);


        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Création du compte ...");
        progressDialog.show();

        String login = _loginText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        Signup signup = new Signup(login, email, password);

        Call<SignupResponse> call = userClient.signup(signup);

        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if (response.body().getCode() == 0){
                    onSignupSuccess();
                } else{
                    if (response.body().getCode() == 4) {
                        Toast.makeText(SignupActivity.this, "Ce nom d'utilistateur n'est pas disponible.", Toast.LENGTH_SHORT).show();
                        onSignupFailed();
                    }else  if (response.body().getCode() == 5){
                        Toast.makeText(SignupActivity.this, "Cette adresse email n'est pas disponible.", Toast.LENGTH_SHORT).show();
                        onSignupFailed();
                    }else{
                        Toast.makeText(SignupActivity.this, "Erreur lors de l'inscription. Veuillez recommmencer ultérieurement.", Toast.LENGTH_SHORT).show();
                        onSignupFailed();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Erreur lors de l'inscription. Veuillez recommmencer ultérieurement.", Toast.LENGTH_SHORT).show();
                onSignupFailed();
            }
        });

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        //onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 2000);
    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        progressDialog.dismiss();
        setResult(RESULT_OK, null);
        Toast.makeText(getBaseContext(), "Inscription validée ! Merci de consulter votre boite mail pour valider votre inscrption. Cela peut prendre plusieurs minutes", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void onSignupFailed() {

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String login = _loginText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (login.isEmpty() || login.length() < 3) {
            _loginText.setError("Au moins 3 caractères.");
            valid = false;
        } else {
            _loginText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Veuillez entrer une adresse mail valide.");
            valid = false;
        } else {
            _emailText.setError(null);
        }
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[&\"'(\\-_)=+?\\.^$])(?=\\S+$).{8,}$";


        if (password.isEmpty() || password.length() < 8 || !Pattern.matches(regex, password)) {
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