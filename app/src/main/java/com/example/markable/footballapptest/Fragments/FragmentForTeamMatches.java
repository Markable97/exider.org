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

import java.util.ArrayList;

public class FragmentForTeamMatches extends Fragment {


    private static final String TAG = "FragMatches";
    private ArrayList<AllMatchesForTeam> arrayAllMatches = new ArrayList<>();

    RecyclerView recyclerView;
    RecycleViewAllMatches adapter;

    public static FragmentForTeamMatches newInstance(ArrayList<AllMatchesForTeam> list){
        FragmentForTeamMatches fragment = new FragmentForTeamMatches();
        Bundle args = new Bundle();
        args.putSerializable("allMatches", list);
        Log.i(TAG, "newInstance: " + list.toString());
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
