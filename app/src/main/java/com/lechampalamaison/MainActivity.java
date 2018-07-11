package com.lechampalamaison;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.lechampalamaison.activity.Home2Activity;
import com.lechampalamaison.activity.HomeActivity;
import com.lechampalamaison.activity.LoginActivity;

import java.util.Date;


public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME_USER = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = this.getSharedPreferences(PREFS_NAME_USER ,Context.MODE_PRIVATE);
        boolean isLogged = sharedPref.getBoolean(getString(R.string.isLoggedin_key), false);
        String token = sharedPref.getString(getString(R.string.token_key), "");
        String login = sharedPref.getString(getString(R.string.login_key), "");
        long date = sharedPref.getLong(getString(R.string.date_token_key),0 );
        int type = sharedPref.getInt(getString(R.string.type_key), 0);

        long milisDay = 86400000;
        Date dateToday = new Date(System.currentTimeMillis()); //or simply new Date();
        long millisToday = dateToday.getTime();

        Intent intent;

       /* if(isLogged == false || millisToday - date >  milisDay){
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{ */
            intent = new Intent(this, Home2Activity.class);
            startActivity(intent);
            finish();
      //  }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
