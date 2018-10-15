package com.example.markable.footballapptest.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.example.markable.footballapptest.Classes.Player;
import com.example.markable.footballapptest.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FragmentStatisticBombardier extends Fragment {

    private static final String TAG = "FragBombar";

    private ArrayList<Player> arrayPlayers;

    private ArrayList<Player> newArrayPlayers = new ArrayList<>();

    TableLayout table;

    public static FragmentStatisticBombardier newInstance(ArrayList<Player> arrayList){
        FragmentStatisticBombardier fragment = new FragmentStatisticBombardier();
        Bundle args = new Bundle();
        args.putSerializable("arrayPlayersBombardier", arrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayPlayers = (ArrayList<Player>) getArguments().getSerializable("arrayPlayersBombardier");
        for(int i=0; i<arrayPlayers.size(); i++){
            newArrayPlayers.add(arrayPlayers.get(i));
        }
        Collections.sort(newArrayPlayers, Player.sortGoal);
        Log.i(TAG, "onCreate: длина массива =  " + newArrayPlayers.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistic_bombardier, container, false);
        table = view.findViewById(R.id.tableForBombardier);
        Log.i(TAG, "onCreateView: новая ветка");
        return view;
    }


}
