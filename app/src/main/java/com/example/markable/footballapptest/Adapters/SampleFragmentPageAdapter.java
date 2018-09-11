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
        Log.i(TAG, "Adapter: " + "divTable = " + this.divTable + "\n prevResults =" + this.prevReslts);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FragmentForTable().newInstance(divTable);
            case 1: return new FragmentForResults().newInstance(prevReslts);
            case 2: return new FragmentForCalendar().newInstance("Вкладка " + position);
            default: return new FragmentForTable().newInstance(divTable);
        }

    }

   /* @Override
    public int getItemPosition(@NonNull Object object) {

        if(object instanceof UpdateFragListener){
            ((UpdateFragListener) object).update(divTable, prevReslts);
        }

        return super.getItemPosition(object);
    }

    public void update(String divTable, String prevReslts){
        this.divTable = divTable;
        this.prevReslts = prevReslts;
        //обновляет - вызов getItemPosition
        notifyDataSetChanged();
    }*/

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
