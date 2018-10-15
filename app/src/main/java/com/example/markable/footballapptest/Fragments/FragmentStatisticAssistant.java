package com.example.markable.footballapptest.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.markable.footballapptest.Classes.Player;
import com.example.markable.footballapptest.R;

import java.util.ArrayList;
import java.util.Collections;

public class FragmentStatisticAssistant extends Fragment {

    private static final String TAG = "FragAssist";

    private ArrayList<Player> arrayPlayers;
    private ArrayList<Player> newArrayPlayers = new ArrayList<>();

    public static FragmentStatisticAssistant  newInstance(ArrayList<Player> arrayList){
        FragmentStatisticAssistant fragment = new FragmentStatisticAssistant();
        Bundle args = new Bundle();
        args.putSerializable("arrayPlayers", arrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayPlayers = (ArrayList<Player>) getArguments().getSerializable("arrayPlayers");
        for(int i=0; i<arrayPlayers.size(); i++){
            newArrayPlayers.add(arrayPlayers.get(i));
        }
        Collections.sort(newArrayPlayers, Player.sortAssist);
        Log.i(TAG, "onCreate: длина массива =  " + newArrayPlayers.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistic_assistant, container, false);

        return view;
    }

}
