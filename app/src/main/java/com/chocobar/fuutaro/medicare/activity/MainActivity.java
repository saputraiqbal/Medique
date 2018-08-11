package com.chocobar.fuutaro.medicare.activity;

import android.app.SearchManager;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.chocobar.fuutaro.medicare.AsyncTasks.SearchDokter;
import com.chocobar.fuutaro.medicare.AsyncTasks.SearchFaskes;
import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.fragment.DokterFragment;
import com.chocobar.fuutaro.medicare.fragment.FaskesFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        mViewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabLayout);
        setSupportActionBar(toolbar);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(new DokterFragment(), "Dokter");
        mSectionsPagerAdapter.addFragment(new FaskesFragment(), "Faskes");
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(this);
        return true;
    }



    @Override
    public boolean onQueryTextSubmit(String query) {
        query = query.toLowerCase();
        PagerAdapter pagerAdapter = mViewPager.getAdapter();
        for(int i = 0; i < pagerAdapter.getCount(); i++){
            Fragment viewPagerFrag = (Fragment)mViewPager.getAdapter().instantiateItem(mViewPager, i);
            if(viewPagerFrag != null && viewPagerFrag.isAdded()){
                if(viewPagerFrag instanceof DokterFragment){
                    DokterFragment dokterFragment = (DokterFragment) viewPagerFrag;
                    if(dokterFragment != null)
                        dokterFragment.beginSearch(query);
                }
                else if(viewPagerFrag instanceof FaskesFragment){
                    FaskesFragment faskesFragment = (FaskesFragment) viewPagerFrag;
                    if(faskesFragment != null)
                        faskesFragment.beginSearch(query);
                }
            }
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String searchInput = newText.toLowerCase();
        PagerAdapter pagerAdapter = mViewPager.getAdapter();
        for(int i = 0; i < pagerAdapter.getCount(); i++){
            Fragment viewPagerFrag = (Fragment)mViewPager.getAdapter().instantiateItem(mViewPager, i);
            if(viewPagerFrag != null && viewPagerFrag.isAdded()){
                if(viewPagerFrag instanceof DokterFragment){
                    DokterFragment dokterFragment = (DokterFragment) viewPagerFrag;
                    if(dokterFragment != null)
                        dokterFragment.beginSearch(searchInput);
                }
                else if(viewPagerFrag instanceof FaskesFragment){
                    FaskesFragment faskesFragment = (FaskesFragment) viewPagerFrag;
                    if(faskesFragment != null)
                        faskesFragment.beginSearch(searchInput);
                }
            }
        }
        return false;
    }

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
