package com.example.markable.footballapptest.Adapters;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


import com.example.markable.footballapptest.Fragments.FragmentForCalendar;
import com.example.markable.footballapptest.Fragments.FragmentForTable;
import com.example.markable.footballapptest.Fragments.FragmentForResults;
import com.example.markable.footballapptest.UpdateFragListener;


public class SampleFragmentPageAdapter extends FragmentPagerAdapter {


    private final FragmentForTable fragmentForTable = new FragmentForTable().newInstance();
    private final FragmentForResults fragmentForResults = new FragmentForResults().newInstance();
    private final FragmentForCalendar fragmentForCalendar = new FragmentForCalendar().newInstance();

    private static final String TAG = "PageAdap";
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"Таблица", "Результаты", "Календарь"};
    private Context context;
    private String divTable;
    private String prevReslts;

    public SampleFragmentPageAdapter(FragmentManager fm, Context context, String divTable, String prevResults) {
        super(fm);
        this.context = context;
        this.divTable = divTable;
        this.prevReslts = prevResults;
        Log.i(TAG, "Adapter Конструктор: " + "divTable = " + this.divTable + "\n prevResults =" + this.prevReslts);
    }

    @Override
    public Fragment getItem(int position) {
        Log.i(TAG, "GetItem: создание вкладок");
        switch (position){
            case 0: return fragmentForTable;
            case 1: return fragmentForResults;
            case 2: return fragmentForCalendar;
            default: return fragmentForTable;
        }

    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        Log.i(TAG, "Adapter: getItemPosition отправка в интерфейс");
        if(object instanceof UpdateFragListener){
            ((UpdateFragListener) object).update(divTable, prevReslts);
            ((UpdateFragListener) object).updateTable(divTable);
        }

        return super.getItemPosition(object);
    }

    public void update(String divTable, String prevReslts){
        Log.i(TAG, "Adapter для Interface: получил от шлавного фрагмента");
        this.divTable = divTable;
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
