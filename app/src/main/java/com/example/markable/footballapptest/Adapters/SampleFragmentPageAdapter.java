package com.example.markable.footballapptest.Adapters;

import android.content.Context;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;


import com.example.markable.footballapptest.Classes.NextMatches;
import com.example.markable.footballapptest.Classes.PrevMatches;
import com.example.markable.footballapptest.Classes.TournamentTable;
import com.example.markable.footballapptest.Fragments.FragmentForCalendar;
import com.example.markable.footballapptest.Fragments.FragmentForTable;
import com.example.markable.footballapptest.Fragments.FragmentForResults;
import com.example.markable.footballapptest.UpdateFragListener;

import java.util.ArrayList;


public class SampleFragmentPageAdapter extends FragmentStatePagerAdapter {


    //private final FragmentForTable fragmentForTable = new FragmentForTable().newInstance();
    //private final FragmentForResults fragmentForResults = new FragmentForResults().newInstance();
    //private final FragmentForCalendar fragmentForCalendar = new FragmentForCalendar().newInstance();

    private static final String TAG = "PageAdap";
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"Календарь", "Результаты", "Таблица"};
    private Context context;
    private ArrayList<TournamentTable> table;
    private ArrayList<PrevMatches> results;
    private ArrayList<NextMatches> calendar;

    public SampleFragmentPageAdapter(FragmentManager fm, Context context, ArrayList<TournamentTable> divTable, ArrayList<PrevMatches> prevResults,
                                     ArrayList<NextMatches> calendar) {
        super(fm);
        this.context = context;
        this.table = divTable;
        this.results = prevResults;
        this.calendar = calendar;
        Log.i(TAG, "Adapter Конструктор: " + "divTable = " + this.table + "\n prevResults =" + this.results
                + "\n calendar = " + this.calendar);
    }

    @Override
    public Fragment getItem(int position) {
        Log.i(TAG, "GetItem: создание вкладок");
        switch (position){
            case 0: return new FragmentForCalendar().newInstance(calendar);
            case 1: return new FragmentForResults().newInstance(results);
            case 2: return new FragmentForTable().newInstance(table);
            default: return new FragmentForTable().newInstance(table);
        }

    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        Log.i(TAG, "Adapter: getItemPosition отправка в интерфейс");
        if(object instanceof UpdateFragListener){
            ((UpdateFragListener) object).update(table, results,calendar);
        }

        return super.getItemPosition(object);
    }

    public void update(ArrayList<TournamentTable> divTable, ArrayList<PrevMatches> prevReslts, ArrayList<NextMatches> calendar){
        Log.i(TAG, "Adapter для Interface: получил от шлавного фрагмента");
        this.table = divTable;
        this.results = prevReslts;
        this.calendar = calendar;
        //обновляет - вызов getItemPosition
        notifyDataSetChanged();
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
