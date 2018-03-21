package com.cryptobuddy.ryanbridges.cryptobuddy.currencylist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.widget.Toast;

import com.cryptobuddy.ryanbridges.cryptobuddy.R;
import com.cryptobuddy.ryanbridges.cryptobuddy.TextDrawable;
import com.cryptobuddy.ryanbridges.cryptobuddy.models.rest.CMCCoin;
import com.cryptobuddy.ryanbridges.cryptobuddy.news.NewsListActivity;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

/**
 * Created by Ryan on 1/21/2018.
 */

public class CurrencyListTabsActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        FavoriteCurrencyListFragment.AllCoinsListUpdater, AllCurrencyListFragment.FavoritesListUpdater {

    private SectionsPagerAdapterCurrencyList mSectionsPagerAdapter;
    public ViewPager mViewPager;
    public static String baseImageURL = "";
    public static String SYMBOL = "SYMBOL";
    private Toolbar mToolbar;
    boolean doubleBackToExitPressedOnce = false;
    public static String IMAGE_URL_FORMAT = "https://s2.coinmarketcap.com/static/img/coins/32x32/%s.png";
    public final static String DAY = "24h";
    public final static String WEEK = "7d";
    public final static String HOUR = "1h";
    public final static String SORT_SETTING = "sort_setting";
    public AppCompatActivity context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list_tabs);
        context = this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_currency_list);
        setSupportActionBar(mToolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.currency_list_tabs);
        mViewPager = (ViewPager) findViewById(R.id.currency_list_tabs_container);
        TextDrawable t = new TextDrawable(this);
        t.setText("ART");
        t.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        t.setTextColor(Color.BLACK);
        t.setTextSize(10);
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(t).build();

        final Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withSelectedItem(1)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                    new PrimaryDrawerItem().withIdentifier(1).withName(R.string.Home).withIcon(FontAwesome.Icon.faw_home),
                    new PrimaryDrawerItem().withIdentifier(2).withName(R.string.News).withIcon(FontAwesome.Icon.faw_newspaper),
                    new PrimaryDrawerItem().withIdentifier(3).withName("About").withIcon(FontAwesome.Icon.faw_question_circle),
                    new PrimaryDrawerItem().withIdentifier(4).withName("Open Source").withIcon(FontAwesome.Icon.faw_github_square),
                    new PrimaryDrawerItem().withIdentifier(5).withName("Rate on Google Play").withIcon(FontAwesome.Icon.faw_thumbs_up)
                )
                .withTranslucentStatusBar(false)
                .build();
        drawer.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                switch (position) {
                    case 2:
                        drawer.closeDrawer();
                        drawer.setSelection(1);
                        startActivity(new Intent(context, NewsListActivity.class));
                        return true;
                    default:
                        return true;
                }
            }
        });

        mSectionsPagerAdapter = new SectionsPagerAdapterCurrencyList(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        Fragment fragment = mSectionsPagerAdapter.getFragment(position);
        if (fragment != null) {
            fragment.onResume();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tap back again to exit.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void removeFavorite(CMCCoin coin) {
        FavoriteCurrencyListFragment frag = (FavoriteCurrencyListFragment) mSectionsPagerAdapter.getFragment(1);
        if (frag != null) {
            frag.removeFavorite(coin);
        }
    }

    public void addFavorite(CMCCoin coin) {
        FavoriteCurrencyListFragment frag = (FavoriteCurrencyListFragment) mSectionsPagerAdapter.getFragment(1);
        if (frag != null) {
            frag.addFavorite(coin);
        }
    }

    public void allCoinsModifyFavorites(CMCCoin coin) {
        AllCurrencyListFragment frag = (AllCurrencyListFragment) mSectionsPagerAdapter.getFragment(0);
        if (frag != null) {
            frag.getAdapter().notifyDataSetChanged();
        }
    }

    public void performFavsSort() {
        FavoriteCurrencyListFragment frag = (FavoriteCurrencyListFragment) mSectionsPagerAdapter.getFragment(1);
        if (frag != null) {
            frag.performFavsSort();
        }
    }

    public void performAllCoinsSort() {
        AllCurrencyListFragment frag = (AllCurrencyListFragment) mSectionsPagerAdapter.getFragment(0);
        if (frag != null) {
            frag.performAllCoinsSort();
        }
    }

}
