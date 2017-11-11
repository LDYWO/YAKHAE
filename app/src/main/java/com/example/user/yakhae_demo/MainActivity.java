package com.example.user.yakhae_demo;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.support.v7.widget.SearchView;

public class MainActivity extends AppCompatActivity {

    int SEARCH = 1;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    SearchView searchView;
    static final String[] LIST_MENU = {"LIST1", "LIST2", "LIST3","LIST1", "LIST2", "LIST3","LIST1", "LIST2", "LIST3","LIST1", "LIST2", "LIST3"} ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.main_logo);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(); */
                Intent intent=new Intent(MainActivity.this,WriteReviewActivity.class);
                startActivity(intent);
            }
        });

    }

    Menu menu_main;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu_main=menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home){
            startActivity(new Intent(this, MainActivity.class));
        }
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_search){

            searchView = (SearchView)menu_main.findItem(R.id.action_search).getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    Log.i("Search",s);
                    Intent intent=new Intent(MainActivity.this,SearchResultActivity.class);
                    intent.putExtra("search",s.toString());
                    setResult(SEARCH,intent);
                    startActivity(intent);
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            switch (getArguments().getInt(ARG_SECTION_NUMBER)){
                case 1:
                {
                    View rootView = inflater.inflate(R.layout.fragment_home, container, false);
                    ArrayAdapter Adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU) ;

                    ListView listview = (ListView) rootView.findViewById(R.id.review_listview) ;
                    listview.setAdapter(Adapter);

                    Button category = (Button)rootView.findViewById(R.id.category);
                    category.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getContext(), CategorySearchResultActivity.class));
                        }
                    });

                    Button generalmedicine = (Button)rootView.findViewById(R.id.general_medicine);
                    generalmedicine.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getContext(), GeneralMedinineResultActivity.class));
                        }
                    });

                    Button specialtymedicine = (Button)rootView.findViewById(R.id.specialty_medicine);
                    specialtymedicine.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getContext(), SpecialMedicineResultActivity.class));
                        }
                    });

                    Button morereviewbtn = (Button)rootView.findViewById(R.id.more_review_btn);
                    morereviewbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getContext(), ReviewSearchListActivity.class));
                        }
                    });

                    return rootView;
                }
                case 2:
                {
                    View rootView = inflater.inflate(R.layout.fragment_ranking, container, false);
                    ArrayAdapter Adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU) ;

                    ListView category_listview = (ListView) rootView.findViewById(R.id.category_listview) ;
                    category_listview.setAdapter(Adapter) ;
                    ListView specialty_medicine_listview = (ListView) rootView.findViewById(R.id.specialty_medicine_listview) ;
                    specialty_medicine_listview.setAdapter(Adapter) ;
                    ListView general_medicine_listview = (ListView) rootView.findViewById(R.id.general_medicine_listview) ;
                    general_medicine_listview.setAdapter(Adapter) ;

                    Button category = (Button)rootView.findViewById(R.id.more_ranking_category);
                    category.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getContext(), DrugSearchActivity.class));
                        }
                    });

                    Button generalmedicine = (Button)rootView.findViewById(R.id.more_ranking_general);
                    generalmedicine.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getContext(), DrugSearchActivity.class));
                        }
                    });

                    Button specialtymedicine = (Button)rootView.findViewById(R.id.more_ranking_special);
                    specialtymedicine.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getContext(), DrugSearchActivity.class));
                        }
                    });


                    return rootView;
                }
                case 3:
                {
                    View rootView = inflater.inflate(R.layout.fragment_community, container, false);
                    ArrayAdapter Adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU) ;
                    ListView community_listview = (ListView) rootView.findViewById(R.id.community_listview) ;
                    community_listview.setAdapter(Adapter) ;
                    return rootView;
                }
                default:
                {
                    View rootView = inflater.inflate(R.layout.fragment_home, container, false);
                    ArrayAdapter Adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU) ;

                    ListView listview = (ListView) rootView.findViewById(R.id.review_listview) ;
                    listview.setAdapter(Adapter) ;

                    return rootView;
                }
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
