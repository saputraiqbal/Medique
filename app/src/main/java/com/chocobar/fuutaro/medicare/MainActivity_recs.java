package com.chocobar.fuutaro.medicare;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v4.app.FragmentManager;
import android.view.Window;

import com.chocobar.fuutaro.medicare.fragment.DokterFragment;
//import com.chocobar.fuutaro.medicare.fragment.DokterFilterFragment;


public class MainActivity_recs extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    //initiate some objects
//    public static RecyclerView rView;
//    public static AdapterDokter adapter;
//    private ArrayList<Dokter> arrayList = new ArrayList<Dokter>();

    //initiate widget objects
    private MenuItem search, filter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_recs);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);


        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("DOKTER"));
        tabLayout.addTab(tabLayout.newTab().setText("FASKES"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        //set RecyclerView and adapter
//        rView = findViewById(R.id.listData);
//        rView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        rView.setLayoutManager(new LinearLayoutManager(this));
//        //call SearchDokter AsyncTask for populate result search
//        new SearchDokter(MainActivity_recs.this).execute("", "0", "0", "0");
//        adapter = new AdapterDokter(arrayList, MainActivity_recs.this);
//        rView.setAdapter(adapter);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    DokterFragment tabDokter = new DokterFragment();
                    return tabDokter;
                case 1:
                    FaskesFragment_recs tabFaskes = new FaskesFragment_recs();
                    return tabFaskes;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0: return "Dokter";
                case 1: return "Faskes";
            }
            return null;
        }
    }

    //applied for create option menu at TitleBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        search = menu.findItem(R.id.action_search);
        filter = menu.findItem(R.id.action_filter);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(this);
        filter.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                showSearchFilter();
                return true;
            }
        });
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
//        if(userInput.isEmpty()){
//            arrayList.clear();
//            new WebServiceSearch(MainActivity_recs.this).execute("0");
//
//        }
////        else
//            arrayList.clear();
//            new SearchDokter(MainActivity_recs.this).execute(userInput, "0", "0", "0");
//            rView.setAdapter(adapter);

//        Dokter dokter = new Dokter();
//        for(String name : names){
//            if(name.toLowerCase().contains(userInput)){
//
//            }
//
//        }//            new WebServiceSearch(MainActivity_recs.this).execute(userInput);
        return false;
    }

//    private void showSearchFilter(){
//        //call DokterFilterFragment to show DialogFragment
//        DialogFragment searchFrag = new DokterFilterFragment();
//        searchFrag.show(getSupportFragmentManager(), "fragment_search_filter");
//    }
}
