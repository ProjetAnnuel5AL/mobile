package com.lechampalamaison.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.support.v7.app.ActionBar;

import com.lechampalamaison.R;
import com.lechampalamaison.fragement.AddressFragment;
import com.lechampalamaison.fragement.OrdersFragment;
import com.lechampalamaison.fragement.ProfilFragment;
import com.lechampalamaison.fragement.ShopFragment;
import com.lechampalamaison.fragement.UpdatePwdFragment;

public class HomeActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_producer:
                    img.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_item:
                    showFragment(new ShopFragment());
                    img.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_dashboard:
                    img.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_notifications:
                    img.setVisibility(View.INVISIBLE);
                    return true;
            }
            return false;
        }
    };
    private PopupWindow mPopupWindow;
    private Context mContext;
    private Activity mActivity;
    ImageButton img;

    private ConstraintLayout mRelativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = getApplicationContext();
        mActivity = HomeActivity.this;
        //container
        mRelativeLayout = (ConstraintLayout) findViewById(R.id.activ_home);
        //getActionBar().setIcon(R.drawable.logo_small);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View view =getSupportActionBar().getCustomView();
         img=(ImageButton)view.findViewById(R.id.action_bar_search);
        img.setVisibility(View.INVISIBLE);
        showFragment(new OrdersFragment());

    }
    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }
}
