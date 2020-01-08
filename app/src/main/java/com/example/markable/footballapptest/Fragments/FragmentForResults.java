package com.example.markable.footballapptest.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.markable.footballapptest.Adapters.RecyclerViewForResults;
import com.example.markable.footballapptest.Classes.ImageFromServer;
import com.example.markable.footballapptest.Classes.PrevMatches;
import com.example.markable.footballapptest.MainActivity;
import com.example.markable.footballapptest.R;
import com.example.markable.footballapptest.UpdateFragListener;
import java.util.ArrayList;

public class FragmentForResults extends Fragment implements UpdateFragListener {

    private static final String TAG = "FragRes";

    RecyclerView recyclerView;
    RecyclerViewForResults adapter;
   // TextView textView;

    MainActivity activity ;

    private ArrayList<PrevMatches> newPrevMatches;
    private ArrayList<ImageFromServer> imageBitmap;

    public static FragmentForResults newInstance (){

        FragmentForResults fragment = new FragmentForResults();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "OnCreate:");

        //fromActivity = getArguments().getString("results");
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_results, container, false);

        recyclerView = view.findViewById(R.id.listForResults);
        Log.i(TAG, "OnCreateView: Загрузка окна фрагмента ");
        Log.i(TAG, "onCreateView: Начало новой ветки c новым адаптером");
        activity = (MainActivity)getActivity();
        newPrevMatches = activity.getPrevResultsMatch();
        imageBitmap = activity.getImageArray();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecyclerViewForResults(/*getActivity(),*/newPrevMatches);
        recyclerView.setAdapter(adapter);
        /*if(newPrevMatches.size() != 0){
            update(null, newPrevMatches, null);
        }else {
            //textView.setText("Чисто проверить! ");
        }*/



        return view;
    }

    @Override
    public void update(int option) {
        newPrevMatches = activity.getPrevResultsMatch();
        Log.i(TAG, "update: Списка матчей" + newPrevMatches.toString());
        imageBitmap = activity.getImageArray();
        adapter.update(newPrevMatches, imageBitmap);
        //RecyclerViewForResults adapter = new RecyclerViewForResults(getActivity(), newPrevMatches);
        //recyclerView.setAdapter(adapter);
        //lisrView.setAdapter(new ListViewArrayAdapterForResult(getActivity(), newPrevMatches));
        /*String results = "";
        for(int i = 0; i < newPrevMatches.size(); i++){
            results += newPrevMatches.get(i).toString() + "\n";
        }
        textView.setText(results);*/
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
