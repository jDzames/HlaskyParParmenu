package s.ics.upjs.sk.jdzama.hlaskyparparmenu;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

//podla http://developer.android.com/training/implementing-navigation/lateral.html

public class InfoActivity extends FragmentActivity {

    CollectionPagerAdapter mCollectionPagerAdapter;
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        mCollectionPagerAdapter =
                new CollectionPagerAdapter(
                        getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });
        mViewPager.setAdapter(mCollectionPagerAdapter);

        final ActionBar actionBar = getActionBar();
        try {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        }catch (NullPointerException e){
            Log.wtf("INFO ACTIVITY ","tabs asi nepojdu");
        }
        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // show the given tab
                mViewPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };
        // Add 3 tabs, specifying the tab's text and TabListener
        actionBar.addTab(actionBar.newTab()
                .setText("YouTube")
                .setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab()
                .setText("Web")
                .setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab()
                .setText("Autor")
                .setTabListener(tabListener));


    }

    public class CollectionPagerAdapter extends FragmentStatePagerAdapter {
        public CollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i){
                case 0: Fragment fragment1 = new InfoFragment();
                    return fragment1;
                case 1: Fragment fragment2 = new WebFragment();
                    return fragment2;
                case 2: Fragment fragment3 = new AutorFragment();
                    return fragment3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }


}
