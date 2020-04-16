package com.hokagelab.www.cataloguemovie;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.hokagelab.www.cataloguemovie.db.MovieHelper;
import com.hokagelab.www.cataloguemovie.model.Movie;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Objects;

//By Aris Kurniawan

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView buttonNav;
    FrameLayout mFrame;
    private FragmentSearch fragmentSearch;
    private FragmentHome fragmentHome;
    private FragmentFavorit fragmentFavorit;
    private FragmentComing fragmentCom;
//    private FragmentSetting fragmentSetting;



//    nambah
    private LinkedList<Movie> list;
    private MovieHelper movieHelper;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//  Kekinian

        mFrame = findViewById(R.id.frame_layout);
        buttonNav = findViewById(R.id.navigation);

        fragmentSearch = new FragmentSearch();
        fragmentHome = new FragmentHome();
        fragmentFavorit = new FragmentFavorit();
        fragmentCom = new FragmentComing();

//        fragmentSetting = new FragmentSetting();

        setFragment(fragmentHome);
        getSupportActionBar().setTitle("Home Movie");

        buttonNav.setOnNavigationItemSelectedListener(this);
        BottomNavigationViewHelper.disableShiftMode(buttonNav);

        movieHelper = new MovieHelper(this);
        movieHelper.open();

        list = new LinkedList<>();

        if (savedInstanceState==null){
            FragmentHome fragment = new FragmentHome();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .commit();
        }

    }


    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        return true;
    }

    public static class BottomNavigationViewHelper {
        @SuppressLint("RestrictedApi")
        public static void disableShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    //noinspection RestrictedApi
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    //noinspection RestrictedApi
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("BNVHelper", "Unable to get shift mode field", e);
            } catch (IllegalAccessException e) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e);
            }
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                setFragment(fragmentHome);
                getSupportActionBar().setTitle("Home Movie");
                item.setChecked(true);
                return true;

            case R.id.nav_comingsoon:
                setFragment(fragmentCom);
                getSupportActionBar().setTitle("ComingSoon Movie");
                item.setChecked(true);
                return true;

            case R.id.nav_favorit:
                setFragment(fragmentFavorit);
                getSupportActionBar().setTitle("Favorite Movie");
                item.setChecked(true);
                return true;

            default:
                return false;

        }
    }

    private void setFragment(FragmentHome fragmentHome) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragmentHome);
        fragmentTransaction.commit();

    }

    private void setFragment(FragmentSearch fragmentSearch) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragmentSearch);
        fragmentTransaction.commit();
    }

    private void setFragment(FragmentFavorit fragmentFavorit){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragmentFavorit);
        fragmentTransaction.commit();
    }

    private void setFragment(FragmentComing fragmentCom){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragmentCom);
        fragmentTransaction.commit();
    }

//    private void setFragment(FragmentSetting fragmentSetting){
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.frame_layout, fragmentSetting);
//        fragmentTransaction.commit();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_setting){
            Intent moveIntent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(moveIntent);//            setFragment(fragmentSetting);
            getSupportActionBar().setTitle("Settings");

        }else if (item.getItemId() == R.id.searchId){
            setFragment(fragmentSearch);
            getSupportActionBar().setTitle("Search");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
