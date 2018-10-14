package com.example.markable.footballapptest.Fragments;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.example.markable.footballapptest.Classes.Player;
import com.example.markable.footballapptest.R;

import java.util.ArrayList;

public class FragmentStatisticSquadList extends Fragment {

    private static final String TAG = "FragSquad";

    private ArrayList<Player> arrayPlayers;

    TableLayout table;

    public static FragmentStatisticSquadList newInstance(ArrayList<Player> arrayList){
        FragmentStatisticSquadList fragment = new FragmentStatisticSquadList();
        Bundle args = new Bundle();
        args.putSerializable("arrayPlayers", arrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayPlayers = (ArrayList<Player>) getArguments().getSerializable("arrayPlayers");
        Log.i(TAG, "onCreate: длина массива =  " + arrayPlayers.size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic_squad, container, false);

        table = view.findViewById(R.id.table_squad);
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
