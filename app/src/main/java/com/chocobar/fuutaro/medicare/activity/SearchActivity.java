package com.chocobar.fuutaro.medicare.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.chocobar.fuutaro.medicare.AsyncTasks.SearchDokter;
import com.chocobar.fuutaro.medicare.AsyncTasks.SearchFaskes;
import com.chocobar.fuutaro.medicare.R;
import com.chocobar.fuutaro.medicare.fragment.MainDokterFragment;
import com.chocobar.fuutaro.medicare.fragment.MainFaskesFragment;
import com.chocobar.fuutaro.medicare.fragment.SearchDokterFragment;
import com.chocobar.fuutaro.medicare.fragment.SearchFaskesFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, TextView.OnEditorActionListener, TextWatcher, View.OnTouchListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;

    private EditText searchText;

    private String title, hint;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        hint = "Telusuri ";

        setupUI();
    }

    public void setupUI() {
        toolbar = findViewById(R.id.toolbar);
        mViewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabLayout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchText = findViewById(R.id.searchTxt);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(new SearchDokterFragment(), "Dokter");
        mSectionsPagerAdapter.addFragment(new SearchFaskesFragment(), "Faskes");
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        title = tabLayout.getTabAt(0).getText().toString();
        searchText.setHint(hint + title);

        tabLayout.addOnTabSelectedListener(this);
        searchText.setOnEditorActionListener(this);
        searchText.addTextChangedListener(this);
        searchText.setOnTouchListener(this);
    }

    private void searchProcess(String s){
        PagerAdapter pageAdp = mViewPager.getAdapter();
        for(int i = 0; i < pageAdp.getCount(); i++){
            Fragment viewPgFrag = (Fragment)mViewPager.getAdapter().instantiateItem(mViewPager, i);
            if(viewPgFrag != null && viewPgFrag.isAdded()){
                if(viewPgFrag instanceof SearchDokterFragment){
                    SearchDokterFragment searchDokterFrag = (SearchDokterFragment) viewPgFrag;
                    searchDokterFrag.dokterSearch(s);
                }
                else if(viewPgFrag instanceof SearchFaskesFragment){
                    SearchFaskesFragment searchFaskesFrag = (SearchFaskesFragment) viewPgFrag;
                    searchFaskesFrag.faskesSearch(s);
                }
            }
        }
    }

    //implement View.OnTouchListener
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;

        if(event.getAction() == MotionEvent.ACTION_UP){
            if(event.getRawX() >= (searchText.getRight() - searchText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                searchText.getText().clear();
                return true;
            }
        }
        return false;
    }

    //implements TextWatcher
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        searchProcess(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    //end implements TextWatcher

    //implements TabLayout.OnTabSelectedListener
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        hint = "Telusuri " + tab.getText().toString();
        searchText.setHint(hint);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    //end implements TabLayout.OnTabSelectedListener

    //implements menu settings
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }
    //end implements menu setting

    //implmenet TextView.OnEditorActionListener
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_SEARCH){
            InputMethodManager mInput = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mInput.hideSoftInputFromWindow(searchText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            return true;
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
