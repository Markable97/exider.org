package com.example.markable.footballapptest.Adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.markable.footballapptest.Fragments.FragmentProtocolTeam;

public class FragmentPageAdapterProtocol extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    Context context;
    String[] teamNames;

    public FragmentPageAdapterProtocol(FragmentManager fm, Context context, String[] teamNames) {
        super(fm);
        this.context = context;
        this.teamNames = teamNames;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentProtocolTeam.newInstance(teamNames[position], position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return teamNames[position];
    }
}
