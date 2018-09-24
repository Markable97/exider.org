package com.example.markable.footballapptest.Adapters;

import android.content.Context;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;


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
    private String tabTitles[] = new String[]{"Таблица", "Результаты", "Календарь"};
    private Context context;
    private String divTable;
    private String prevReslts;
    private ArrayList<TournamentTable> table;

    public SampleFragmentPageAdapter(FragmentManager fm, Context context, ArrayList<TournamentTable> divTable, String prevResults) {
        super(fm);
        this.context = context;
        this.table = divTable;
        this.prevReslts = prevResults;
        Log.i(TAG, "Adapter Конструктор: " + "divTable = " + this.divTable + "\n prevResults =" + this.prevReslts);
    }

    @Override
    public Fragment getItem(int position) {
        Log.i(TAG, "GetItem: создание вкладок");
        switch (position){
            case 0: return new FragmentForTable().newInstance(table);
            case 1: return new FragmentForResults().newInstance(prevReslts);
            case 2: return new FragmentForCalendar().newInstance();
            default: return new FragmentForTable().newInstance(table);
        }

    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        Log.i(TAG, "Adapter: getItemPosition отправка в интерфейс");
        if(object instanceof UpdateFragListener){
            ((UpdateFragListener) object).update(table, prevReslts);
        }

        return super.getItemPosition(object);
    }

    public void update(ArrayList<TournamentTable> divTable, String prevReslts){
        Log.i(TAG, "Adapter для Interface: получил от шлавного фрагмента");
        this.table = divTable;
        this.prevReslts = prevReslts;
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
