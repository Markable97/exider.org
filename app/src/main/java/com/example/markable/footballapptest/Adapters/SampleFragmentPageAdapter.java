package com.example.markable.footballapptest.Adapters;

import android.content.Context;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.markable.footballapptest.Fragments.FragmentForCalendar;
import com.example.markable.footballapptest.Fragments.FragmentForDiv;
import com.example.markable.footballapptest.Fragments.FragmentForResults;


public class SampleFragmentPageAdapter extends FragmentPagerAdapter {




    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"Таблица", "Результаты", "Календарь"};
    private Context context;

    public SampleFragmentPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FragmentForDiv().newInstance("Вкладка " + position);
            case 1: return new FragmentForResults().newInstance("Вкладка " + position);
            case 2: return new FragmentForCalendar().newInstance("Вкладка " + position);
            default: return new FragmentForDiv().newInstance("Вкладка " + position);
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
