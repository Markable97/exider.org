package com.example.markable.footballapptest.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.markable.footballapptest.Adapters.RecyclerViewForResults;
import com.example.markable.footballapptest.Classes.ImageFromServer;
import com.example.markable.footballapptest.Classes.PrevMatches;
import com.example.markable.footballapptest.R;
import com.example.markable.footballapptest.TeamActivity;

import java.util.ArrayList;

public class FragmentForTeamMatches extends Fragment {

    TeamActivity activity;

    private static final String TAG = "FragMatches";
    private ArrayList<PrevMatches> arrayAllMatches = new ArrayList<>();
    private ArrayList<ImageFromServer> arrayImage;

    RecyclerView recyclerView;
    //RecycleViewAllMatches adapter;
    //Попробовать использовать существующий адаптер
    RecyclerViewForResults adapter;

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
        arrayImage = activity.getArrayTeamImage();
        recyclerView = view.findViewById(R.id.listAllMatches);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewForResults.OnFalsePlayedListiner listener = new RecyclerViewForResults.OnFalsePlayedListiner() {
            @Override
            public void playedFalse() {
                Toast.makeText(getActivity(),"Матч еще не сыгран", Toast.LENGTH_SHORT).show();
            }
        };
        //adapter = new RecycleViewAllMatches(getActivity(), arrayAllMatches, arrayImage);
        adapter = new RecyclerViewForResults(/*getActivity(),*/ arrayAllMatches, listener);
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
