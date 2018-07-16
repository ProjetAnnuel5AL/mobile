package com.lechampalamaison.loc.lechampalamaison.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.lechampalamaison.loc.lechampalamaison.Fragment.CartFragment;
import com.lechampalamaison.loc.lechampalamaison.Fragment.CollectiviteFragment;
import com.lechampalamaison.loc.lechampalamaison.Fragment.HomeFragment;
import com.lechampalamaison.loc.lechampalamaison.Fragment.ShopFragment;

import com.lechampalamaison.loc.lechampalamaison.R;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private BroadcastReceiver mRegistrationBroadcastReceiver;
    SharedPreferences sharedpreferences;
    public static final String mypreferenceOption = "Option";
    String tokenFCM = "";
    public static final String FirstUpdateOption = "FirstUpdatedKey";
    public static final String FCMOption = "FcmKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sharedpreferences = getSharedPreferences(mypreferenceOption,Context.MODE_PRIVATE);

        if (sharedpreferences.contains(FirstUpdateOption)) {
            if (!(sharedpreferences.getBoolean(FirstUpdateOption, true))) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(FirstUpdateOption, true);
                editor.commit();

            }
        }

        initUI();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_pwd) {
            Intent intent = new Intent(this, UpdatePasswordActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_mail) {
            Intent intent = new Intent(this, ProfilActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_address) {
            Intent intent = new Intent(this, AddressActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_order) {
            Intent intent = new Intent(this, OrdersActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout){
            SharedPreferences sharedPref = HomeActivity.this.getSharedPreferences("USER", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(getString(R.string.isLoggedin_key), false);
            editor.commit();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void initUI() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0 :
                        return new HomeFragment();
                    case 1 :
                        return new CollectiviteFragment();
                    case 2 :
                        return new ShopFragment();
                    case 3 :
                        return new CartFragment();
                    default:
                        return new HomeFragment();
                }
            }
        });

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_home_black_24dp),
                        Color.parseColor(colors[0])
                ).title("Accueil")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_group_black_24dp),
                        Color.parseColor(colors[2])
                ).title("collectivite")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_shopping_basket_black_24dp),
                        Color.parseColor(colors[4])
                ).title("Le champ")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_shopping_cart_black_24dp),
                        Color.parseColor(colors[3])
                ).title("Pannier")
                        .badgeTitle(Integer.toString(4))
                        .build()
        );



        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }


        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {

                final NavigationTabBar.Model model = navigationTabBar.getModels().get(3);
                navigationTabBar.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        model.showBadge();
                    }
                }, 0 * 100);

            }
        }, 500);


    }



}
