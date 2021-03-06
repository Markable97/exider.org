package com.example.markable.footballapptest.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.markable.footballapptest.Adapters.FragmentPageAdapterForStatistic;
import com.example.markable.footballapptest.Classes.Player;
import com.example.markable.footballapptest.R;
import com.example.markable.footballapptest.ReturnFromFragForAct;
import com.example.markable.footballapptest.TeamActivity;

import java.util.ArrayList;

public class FragmentForTeamStatistic extends Fragment {

    TeamActivity activity;

    private static final String TAG = "FragStatistic";

    ReturnFromFragForAct fromFragForAct;

    ViewPager viewPager;
    TabLayout tabLayout;

    boolean flag = false;

    FragmentPageAdapterForStatistic mAdapter;

    ArrayList<Player> arrayPlayers = new ArrayList<>();
    String teamName;

    /*public static FragmentForTeamStatistic newInstance(String name){
        Log.i(TAG, "NewInstance: " + name);
        FragmentForTeamStatistic fragment = new FragmentForTeamStatistic();
        Bundle args = new Bundle();
        args.putString("nameTeam", name);
        fragment.setArguments(args);
        return fragment;
    }*/

    public static FragmentForTeamStatistic newInstance(){

        FragmentForTeamStatistic fragment = new FragmentForTeamStatistic();

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ReturnFromFragForAct){
            fromFragForAct = (ReturnFromFragForAct) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate: " );
       /* if(getArguments().getSerializable("nameTeamArray" ) == null){
            Log.i(TAG, "onCreate: длина массива равна 0");
            new ServerConnect().execute(fromBun);
        }else {
            arrayPlayers = activity.getArrayPlayers();
            Log.i(TAG, "onCreate: Длина массива = " + arrayPlayers.size() );
            flag = true;
        }*/


    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_statistic, container, false);
        activity = (TeamActivity)getActivity();
        Log.i(TAG, "onCreateView: ");
        arrayPlayers = activity.getArrayPlayers();
        viewPager = view.findViewById(R.id.viewPager_team_statistic);
        tabLayout = view.findViewById(R.id.statistic_tabs);
        fromFragForAct.sendDataToActivity(arrayPlayers);
        mAdapter = new FragmentPageAdapterForStatistic(getChildFragmentManager(), arrayPlayers);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
        tabLayout.setSelectedTabIndicatorHeight((int) (2 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(getResources().getColor(R.color.enable_tab), getResources().getColor(R.color.colorAccent));

        return view;
    }
    

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG,"OnStart ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "OnResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,"onPause" );
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG,"onStop" );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach");
    }

}
