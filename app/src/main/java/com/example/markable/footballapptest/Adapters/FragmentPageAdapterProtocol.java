package com.example.markable.footballapptest.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.markable.footballapptest.Fragments.FragmentProtocolTeam;
import com.example.markable.footballapptest.UpdateFragListener;

public class FragmentPageAdapterProtocol extends FragmentPagerAdapter {

    private static final String TAG = FragmentPageAdapterProtocol.class.getSimpleName();

    private int option;
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
    public int getItemPosition(@NonNull Object object) {
        Log.i(TAG, "Adapter: getItemPosition отправка в интерфейс");
        if(object instanceof UpdateFragListener){
            ((UpdateFragListener) object).update(this.option);
        }

        return super.getItemPosition(object);
    }

    public void update(int option){
        Log.i(TAG, "Adapter для Interface: получил команду от активности");
        /*this.table = divTable;
        this.results = prevReslts;
        this.calendar = calendar;
        this.image = image;*/
        //обновляет - вызов getItemPosition
        this.option = option;
        notifyDataSetChanged();
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
