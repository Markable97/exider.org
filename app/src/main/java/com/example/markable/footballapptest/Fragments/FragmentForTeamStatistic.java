package com.example.markable.footballapptest.Fragments;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.markable.footballapptest.Adapters.FragmentPageAdapterForTeam;
import com.example.markable.footballapptest.R;

public class FragmentForTeamStatistic extends Fragment {


    ViewPager viewPager;
    TabLayout tabLayout;

    FragmentPageAdapterForTeam mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_statistic, container, false);

        viewPager = view.findViewById(R.id.viewPager_team_statistic);
        tabLayout = view.findViewById(R.id.statistic_tabs);
        mAdapter = new FragmentPageAdapterForTeam(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
