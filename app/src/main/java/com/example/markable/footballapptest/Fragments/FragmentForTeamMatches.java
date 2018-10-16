package com.example.markable.footballapptest.Fragments;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.markable.footballapptest.Classes.AllMatchesForTeam;
import com.example.markable.footballapptest.R;

import java.util.ArrayList;

public class FragmentForTeamMatches extends Fragment {


    private static final String TAG = "FragMatches";
    private ArrayList<AllMatchesForTeam> arrayAllMatches = new ArrayList<>();

    public static FragmentForTeamMatches newInstance(ArrayList<AllMatchesForTeam> list){
        FragmentForTeamMatches fragment = new FragmentForTeamMatches();
        Bundle args = new Bundle();
        args.putSerializable("allMatches", list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayAllMatches = (ArrayList<AllMatchesForTeam>) getArguments().getSerializable("allMatches");
        Log.i(TAG, "onCreate: длинна массива " + arrayAllMatches.size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_matches, container, false);



        return view;
    }
}
