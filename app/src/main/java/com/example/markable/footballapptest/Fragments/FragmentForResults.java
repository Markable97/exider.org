package com.example.markable.footballapptest.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.markable.footballapptest.Classes.PrevMatches;
import com.example.markable.footballapptest.R;
import com.example.markable.footballapptest.UpdateFragListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;

public class FragmentForResults extends Fragment implements UpdateFragListener {

    private static final String TAG = "FragRes";
    private String fromActivity;

    TextView textView;

    private Gson gson = new Gson();

    private ArrayList<PrevMatches> newPrevMatches = new ArrayList<>();

    public static FragmentForResults newInstance (){
        FragmentForResults fragment = new FragmentForResults();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "OnCreate:");
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_results, container, false);

        Log.i(TAG, "OnCreateView: Загрузка окна фрагмента " + fromActivity);

        textView = view.findViewById(R.id.textView_results);
        //textView.setText(fromActivity);

        if(savedInstanceState == null){
            fromActivity = "[{\"nameDivision\":\"Высший дивизион\",\"idTour\":2,\"teamHome\":\"Регион-13\",\"goalHome\":2,\"goalVisit\":1,\"teamVisit\":\"Трансгарант\"},{\"nameDivision\":\"Высший дивизион\",\"idTour\":2,\"teamHome\":\"Авангард\",\"goalHome\":5,\"goalVisit\":1,\"teamVisit\":\"Бастион\"}]";
        }

        newPrevMatches = gson.fromJson(fromActivity, new TypeToken<ArrayList<PrevMatches>>(){}.getType());
        String results = "";
        for(int i = 0; i < newPrevMatches.size(); i++){
            results += newPrevMatches.get(i).toString() + "\n";
        }
        textView.setText(results);


        return view;
    }

    @Override
    public void update(String divTable, String prevResults) {
        Log.i(TAG, "Interface: " + prevResults);
        this.fromActivity = prevResults;
        newPrevMatches.clear();
        newPrevMatches = gson.fromJson(fromActivity, new TypeToken<ArrayList<PrevMatches>>(){}.getType());
        String results = "";
        for(int i = 0; i < newPrevMatches.size(); i++){
            results += newPrevMatches.get(i).toString() + "\n";
        }
        textView.setText(results);
    }

    @Override
    public void updateTable(String table) {

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
