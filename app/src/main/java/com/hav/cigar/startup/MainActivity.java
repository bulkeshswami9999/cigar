package com.hav.cigar.startup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hav.cigar.LogoutActivity;
import com.hav.cigar.activities.PartnerActivity;
import com.hav.cigar.R;
import com.hav.cigar.activities.AboutUsActivity;
import com.hav.cigar.activities.ContactUsActivity;
import com.hav.cigar.activities.MyOrderActivity;
import com.hav.cigar.activities.PrivacyPolicyActivity;
import com.hav.cigar.activities.ProfileActivity;
import com.hav.cigar.activities.TermsandConditionActivity;
import com.hav.cigar.api.APIInterface;
import com.hav.cigar.fragments.ImageListFragment;
import com.hav.cigar.miscellaneous.EmptyActivity;
import com.hav.cigar.notification.NotificationCountSetClass;
import com.hav.cigar.options.CartListActivity;
import com.hav.cigar.options.NotificationActivity;
import com.hav.cigar.options.SearchResultActivity;
import com.hav.cigar.options.WishlistActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.hav.cigar.utility.Constant;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    public static int notificationCountCart = 0;
    APIInterface apiInterface;
    private static String TAG = "MainActivity";
    //TextView ImgTitle1,ImgDesc,ImgPrice;
    ViewPager viewpager1;
    private LinearLayout dots;
    private int dotsCount;
    private ImageView[] dotss;
    int images[] = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4};

    static ViewPager viewPager;
    static TabLayout tabLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(this);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        if (viewPager != null) {
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        MenuItem item = menu.findItem(R.id.action_cart);
        NotificationCountSetClass.setAddToCart(MainActivity.this, item, notificationCountCart);
        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        invalidateOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            startActivity(new Intent(MainActivity.this, SearchResultActivity.class));
            return true;
        } else if (id == R.id.action_notifications) {
            startActivity(new Intent(MainActivity.this, NotificationActivity.class));

        } else if (id == R.id.action_cart) {
            startActivity(new Intent(MainActivity.this, CartListActivity.class));
            return true;
        } else {
            startActivity(new Intent(MainActivity.this, EmptyActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());

        ImageListFragment fragment = new ImageListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 1);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, getString(R.string.item_1));
        fragment = new ImageListFragment();
        bundle = new Bundle();
        bundle.putInt("type", 2);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, getString(R.string.item_2));
        fragment = new ImageListFragment();
        bundle = new Bundle();
        bundle.putInt("type", 3);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, getString(R.string.item_3));
        fragment = new ImageListFragment();
        bundle = new Bundle();
        bundle.putInt("type", 4);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, getString(R.string.item_4));
        fragment = new ImageListFragment();
        bundle = new Bundle();
        bundle.putInt("type", 5);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, getString(R.string.item_5));

        fragment = new ImageListFragment();
        bundle = new Bundle();
        bundle.putInt("type", 6);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, getString(R.string.item_6));
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_item1) {
            viewPager.setCurrentItem(0);
        } else if (id == R.id.nav_item2) {
            viewPager.setCurrentItem(1);
        } else if (id == R.id.nav_item3) {
            viewPager.setCurrentItem(2);
        } else if (id == R.id.nav_item4) {
            viewPager.setCurrentItem(3);
        } else if (id == R.id.nav_item5) {
            viewPager.setCurrentItem(4);
        } else if (id == R.id.nav_item6) {
            viewPager.setCurrentItem(5);
        } else if (id == R.id.nav_item7) {
            //product_list();
            startActivity(new Intent(MainActivity.this, PartnerActivity.class));
        } //else if (id == R.id.cigar_club) {
        //startActivity(new Intent(MainActivity.this, CigarOfTheMonthActivity.class));
        //}
        else if (id == R.id.terms_conditions) {
            startActivity(new Intent(MainActivity.this, TermsandConditionActivity.class));
        } else if (id == R.id.about_us) {
            startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
        } else if (id == R.id.my_orders) {
            startActivity(new Intent(MainActivity.this, MyOrderActivity.class));
        } else if (id == R.id.my_wishlist) {
            startActivity(new Intent(MainActivity.this, WishlistActivity.class));
        } else if (id == R.id.my_cart) {
            startActivity(new Intent(MainActivity.this, CartListActivity.class));
        } else if (id == R.id.privacy_policy) {
            startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
        } else if (id == R.id.contact_us) {
            startActivity(new Intent(MainActivity.this, ContactUsActivity.class));
        } else if (id == R.id.my_account) {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        }else if(id == R.id.logout) {
        //startActivity(new Intent(MainActivity.this, LogoutActivity.class));
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Info");
            builder.setMessage("Do you want to logout ??");
            builder.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferences sharedpreferences = getSharedPreferences(Constant.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    //clearing all data from sharedPreferences
                    editor.clear();
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            startActivity(new Intent(MainActivity.this, EmptyActivity.class)); }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 1) {
            navigationView.setCheckedItem(R.id.nav_item1);
        } else if (position == 2) {
            navigationView.setCheckedItem(R.id.nav_item2);
        } else if (position == 3) {
            navigationView.setCheckedItem(R.id.nav_item3);
        } else if (position == 4) {
            navigationView.setCheckedItem(R.id.nav_item4);
        } else if (position == 5) {
            navigationView.setCheckedItem(R.id.nav_item5);
        } else if (position == 6) {
            navigationView.setCheckedItem(R.id.nav_item6);
        } else if (position == 7) {
            navigationView.setCheckedItem(R.id.nav_item7);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) { }
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();
        public Adapter(FragmentManager fm) {
            super(fm);
        }
        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
        @Override
        public int getCount() {
            return mFragments.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

}
