package com.lechampalamaison.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.support.v7.app.ActionBar;

import com.lechampalamaison.R;
import com.lechampalamaison.fragement.CartFragment;
import com.lechampalamaison.fragement.ShopFragment;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = (@NonNull MenuItem item) -> {
        switch (item.getItemId()) {
            case R.id.navigation_producer:
                return true;
            case R.id.navigation_item:
                showFragment(new ShopFragment());
                return true;
            case R.id.navigation_dashboard:
                return true;
            case R.id.navigation_notifications:
                showFragment(new CartFragment());
                return true;
        }

        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // getActionBar().setIcon(R.drawable.logo_small);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        }

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        // View view =getSupportActionBar().getCustomView();
    }
    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }
}
