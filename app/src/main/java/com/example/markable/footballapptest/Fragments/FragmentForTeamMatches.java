package com.example.markable.footballapptest.Fragments;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.markable.footballapptest.Adapters.RecycleViewAllMatches;
import com.example.markable.footballapptest.Classes.AllMatchesForTeam;
import com.example.markable.footballapptest.R;
import com.example.markable.footballapptest.TeamActivity;

import java.util.ArrayList;

public class FragmentForTeamMatches extends Fragment {

    TeamActivity activity;

    private static final String TAG = "FragMatches";
    private ArrayList<AllMatchesForTeam> arrayAllMatches = new ArrayList<>();

    RecyclerView recyclerView;
    RecycleViewAllMatches adapter;

    public static FragmentForTeamMatches newInstance(){
        FragmentForTeamMatches fragment = new FragmentForTeamMatches();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate:");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_matches, container, false);

        activity = (TeamActivity)getActivity();
        arrayAllMatches = activity.getArrayAllMatches();
        recyclerView = view.findViewById(R.id.listAllMatches);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecycleViewAllMatches(arrayAllMatches);
        recyclerView.setAdapter(adapter);


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
