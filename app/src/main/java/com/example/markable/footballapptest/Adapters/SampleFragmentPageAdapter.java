package com.example.markable.footballapptest.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.util.Log;


import com.example.markable.footballapptest.Classes.ImageFromServer;
import com.example.markable.footballapptest.Classes.NextMatches;
import com.example.markable.footballapptest.Classes.PrevMatches;
import com.example.markable.footballapptest.Classes.TournamentTable;
import com.example.markable.footballapptest.Fragments.FragmentForCalendar;
import com.example.markable.footballapptest.Fragments.FragmentForTable;
import com.example.markable.footballapptest.Fragments.FragmentForResults;
import com.example.markable.footballapptest.UpdateFragListener;

import java.util.ArrayList;


public class SampleFragmentPageAdapter extends FragmentPagerAdapter {


    //private final FragmentForTable fragmentForTable = new FragmentForTable().newInstance();
    //private final FragmentForResults fragmentForResults = new FragmentForResults().newInstance();
    //private final FragmentForCalendar fragmentForCalendar = new FragmentForCalendar().newInstance();

    private static final String TAG = "PageAdap";
    private int option;
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"Календарь", "Результаты", "Таблица"};
    private Context context;
    private ArrayList<TournamentTable> table;
    private ArrayList<PrevMatches> results;
    private ArrayList<NextMatches> calendar;
    private ArrayList<ImageFromServer> image;

    public SampleFragmentPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.image = image;
        Log.i(TAG, "Adapter Конструктор: " + "divTable = " + this.table + "\n prevResults =" + this.results
                + "\n calendar = " + this.calendar + "image " + this.image);
    }

    @Override
    public Fragment getItem(int position) {
        Log.i(TAG, "GetItem: создание вкладок");
        switch (position){
            case 0: return new FragmentForCalendar().newInstance();
            case 1: return new FragmentForResults().newInstance();
            case 2: return new FragmentForTable().newInstance();
            default: return new FragmentForTable().newInstance();
        }

    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        Log.i(TAG, "Adapter: getItemPosition отправка в интерфейс");
        if(object instanceof UpdateFragListener){
            ((UpdateFragListener) object).update(this.option);
        }

        return super.getItemPosition(object);
    }

    public void update(int option){
        Log.i(TAG, "Adapter для Interface: получил от шлавного фрагмента");
        /*this.table = divTable;
        this.results = prevReslts;
        this.calendar = calendar;
        this.image = image;*/
        //обновляет - вызов getItemPosition
        this.option = option;
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
