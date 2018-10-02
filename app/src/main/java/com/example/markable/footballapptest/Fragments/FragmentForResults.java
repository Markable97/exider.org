package com.example.markable.footballapptest.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.markable.footballapptest.Adapters.RecyclerViewForResults;
import com.example.markable.footballapptest.Classes.NextMatches;
import com.example.markable.footballapptest.Classes.PrevMatches;
import com.example.markable.footballapptest.Classes.TournamentTable;
import com.example.markable.footballapptest.R;
import com.example.markable.footballapptest.UpdateFragListener;
import java.util.ArrayList;

public class FragmentForResults extends Fragment implements UpdateFragListener {

    private static final String TAG = "FragRes";

    RecyclerView recyclerView;
    RecyclerViewForResults adapter;
   // TextView textView;


    private ArrayList<PrevMatches> newPrevMatches = new ArrayList<>();

    public static FragmentForResults newInstance (ArrayList<PrevMatches> prevRes){
        Log.i(TAG, "NewInstance: " + prevRes);
        FragmentForResults fragment = new FragmentForResults();
        Bundle args = new Bundle();
        args.putSerializable("results", prevRes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "OnCreate:");
        newPrevMatches = (ArrayList<PrevMatches>) getArguments().getSerializable("results");
        //fromActivity = getArguments().getString("results");
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_results, container, false);

        recyclerView = view.findViewById(R.id.listForResults);
        Log.i(TAG, "OnCreateView: Загрузка окна фрагмента ");
        Log.i(TAG, "onCreateView: Начало новой ветки c новым адаптером");
        //textView = view.findViewById(R.id.textView_results);
        //textView.setText(fromActivity);
        adapter = new RecyclerViewForResults(getActivity(), newPrevMatches);
        recyclerView.setAdapter(adapter);
        /*if(newPrevMatches.size() != 0){
            update(null, newPrevMatches, null);
        }else {
            //textView.setText("Чисто проверить! ");
        }*/



        return view;
    }

    @Override
    public void update(ArrayList<TournamentTable> divTable, ArrayList<PrevMatches> prevResults, ArrayList<NextMatches> calendar) {
        Log.i(TAG, "Interface: " + prevResults);
        this.newPrevMatches = prevResults;
        adapter.update(newPrevMatches);
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
