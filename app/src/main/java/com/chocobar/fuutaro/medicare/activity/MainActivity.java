package com.chocobar.fuutaro.medicare.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.fragment.MainDokterFragment;
import com.chocobar.fuutaro.medicare.fragment.MainFaskesFragment;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import static com.chocobar.fuutaro.medicare.STATIC_VALUES.IMG_PROFILE;
import static com.chocobar.fuutaro.medicare.STATIC_VALUES.LOGIN_STATUS;
import static com.chocobar.fuutaro.medicare.STATIC_VALUES.USER_ID;
import static com.chocobar.fuutaro.medicare.STATIC_VALUES.USERNAME;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ActionBarDrawerToggle mToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView navView;
    private View headerView;
    private Menu menu;
    private TextView txtNavName;
    private Intent intent;
    private CircleImageView imgProfile;

    boolean doubleBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
    }

    public void setupUI() {
        toolbar = findViewById(R.id.toolbar);
        mViewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabLayout);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nvView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        headerView = navView.getHeaderView(0);
        menu = navView.getMenu();
        txtNavName = headerView.findViewById(R.id.tv_nama);
        imgProfile = headerView.findViewById(R.id.img_profile);

        setNavMenu();

        navView.setNavigationItemSelectedListener(this);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(new MainDokterFragment(), "Dokter");
        mSectionsPagerAdapter.addFragment(new MainFaskesFragment(), "Faskes");
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void setNavMenu() {
        if(USER_ID.equals("0")){
            txtNavName.setText("Guest");
            navView.getMenu().clear();
            navView.inflateMenu(R.menu.menu_login);
        }
        else{
            txtNavName.setText(USERNAME);
            String stringBase64 = IMG_PROFILE.substring(IMG_PROFILE.indexOf(",") + 1);
            byte[] avatarByte = Base64.decode(stringBase64, Base64.DEFAULT);
            Bitmap imgDecode = BitmapFactory.decodeByteArray(avatarByte,0, avatarByte.length);
            imgProfile.setImageBitmap(imgDecode);
            navView.getMenu().clear();
            navView.inflateMenu(R.menu.menu_navigation);
        }
    }

    //implement NavigationView.OnNavigationItemSelectedListener
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_login:
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_account:
                intent = new Intent(getApplicationContext(), UserConfigurationActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_search:
                intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_history:
                intent = new Intent(getApplicationContext(), RiwayatActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
                alert.setMessage("Apakah Anda yakin ingin keluar?");
                alert.setButton(AlertDialog.BUTTON_POSITIVE, "Keluar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LOGIN_STATUS = 0;
                        USER_ID = "0";
                        USERNAME = "";
                        IMG_PROFILE = "";
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}});
                alert.show();
                break;
        }
        return true;
    }

    //implement Fragment Activity
    @Override
    public void onBackPressed() {
        if(doubleBackPressed){
            super.onBackPressed();
            return;
        }
        this.doubleBackPressed = true;
        Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackPressed = false;
            }
        }, 2000);
    }

    //implements menu settings
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
            return true;
        switch (item.getItemId()){
            case  R.id.action_search:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }
    //end implements menu settings

    /**
     * A placeholder fragment containing a simple view.
     */

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragment = new ArrayList<>();
        private final List<String> mFragmentTitle = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragment.get(position);
        }

        @Override
        public int getCount() {
            return mFragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitle.get(position);
        }

        public void addFragment(Fragment fragment, String title){
            mFragment.add(fragment);
            mFragmentTitle.add(title);
        }
    }
}
