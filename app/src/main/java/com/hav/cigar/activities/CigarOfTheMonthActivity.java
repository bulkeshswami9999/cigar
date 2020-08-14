package com.hav.cigar.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hav.cigar.R;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.Objects;

public class CigarOfTheMonthActivity extends AppCompatActivity {

    ViewPager viewPager;
    DotsIndicator dotsIndicator;
    ArrayList<String> membershipArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cigar_of_the_month);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.viewPager);
        dotsIndicator = findViewById(R.id.dots_indicator);
        membershipArray = new ArrayList<>();
        membershipArray.add("One Month Membership");
        membershipArray.add("Six Month Membership");
        membershipArray.add("Twelve Month Membership");
        PagerAdapter adapter = new PlanAdapter(this, membershipArray);
        viewPager.setAdapter(adapter);
        dotsIndicator.setViewPager(viewPager);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
     static class PlanAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        Activity activity;
        ArrayList<String> image_arraylist;

         PlanAdapter(Activity activity, ArrayList<String> image_arraylist) {
            this.activity = activity;
            this.image_arraylist = image_arraylist;
        }

        @NonNull
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.cigar_month_layout, container, false);
            AppCompatTextView title = view.findViewById(R.id.title);
          AppCompatTextView amount = view.findViewById(R.id.amount);
            title.setText(image_arraylist.get(position));
            if(position == 0){
                amount.setText(R.string.one_month_cost);
            }else if(position == 1){
                amount.setText(R.string.six_months_cost);
            }else if(position == 2){
                amount.setText(R.string.twelve_months_cost);
            }else
                amount.setText("0");


            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return image_arraylist.size();
        }


        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
