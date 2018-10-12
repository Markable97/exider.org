package com.example.markable.footballapptest.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.markable.footballapptest.Fragments.FragmentStatisticAssistant;
import com.example.markable.footballapptest.Fragments.FragmentStatisticBombardier;
import com.example.markable.footballapptest.Fragments.FragmentStatisticSquadList;

public class FragmentPageAdapterForStatistic extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"Состав", "Бомбардиры", "Ассистент"};

    public FragmentPageAdapterForStatistic(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:  return  new FragmentStatisticSquadList().newInstance();
            case 1:  return  new FragmentStatisticBombardier().newInstance();
            case 2:  return  new FragmentStatisticAssistant().newInstance();
            default: return  new FragmentStatisticSquadList().newInstance();

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
