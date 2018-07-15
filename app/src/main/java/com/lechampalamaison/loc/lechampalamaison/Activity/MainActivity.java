package com.lechampalamaison.loc.lechampalamaison.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lechampalamaison.loc.lechampalamaison.R;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME_USER = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = this.getSharedPreferences(PREFS_NAME_USER , Context.MODE_PRIVATE);
        boolean isLogged = sharedPref.getBoolean(getString(R.string.isLoggedin_key), false);
        String token = sharedPref.getString(getString(R.string.token_key), "");
        String login = sharedPref.getString(getString(R.string.login_key), "");
        long date = sharedPref.getLong(getString(R.string.date_token_key),0 );
        int type = sharedPref.getInt(getString(R.string.type_key), 0);

        long milisDay = 86400000;
        Date dateToday = new Date(System.currentTimeMillis()); //or simply new Date();
        long millisToday = dateToday.getTime();

        Intent intent;


        if(isLogged == false || millisToday - date >  milisDay){
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
