package com.example.markable.footballapptest.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.example.markable.footballapptest.Classes.Player;
import com.example.markable.footballapptest.Fragments.FragmentStatisticAssistant;
import com.example.markable.footballapptest.Fragments.FragmentStatisticBombardier;
import com.example.markable.footballapptest.Fragments.FragmentStatisticSquadList;

import java.util.ArrayList;

public class FragmentPageAdapterForStatistic extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"Состав", "Бомбардиры", "Ассистент"};

    private ArrayList<Player> arrayPlayers;

    public FragmentPageAdapterForStatistic(FragmentManager fm, ArrayList<Player> arrayPlayers) {
        super(fm);
        this.arrayPlayers = arrayPlayers;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:  return  new FragmentStatisticSquadList().newInstance(arrayPlayers);
            case 1:  return  new FragmentStatisticBombardier().newInstance(arrayPlayers);
            case 2:  return  new FragmentStatisticAssistant().newInstance(arrayPlayers);
            default: return  new FragmentStatisticSquadList().newInstance(arrayPlayers);

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
