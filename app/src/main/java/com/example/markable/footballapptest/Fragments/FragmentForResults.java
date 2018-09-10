package com.example.markable.footballapptest.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.markable.footballapptest.R;

public class FragmentForResults extends Fragment {

    private static final String TAG = "FragRes";
    private String fromActivity;

    public static FragmentForResults newInstance (String data){
        FragmentForResults fragment = new FragmentForResults();

        Bundle args = new Bundle();
        args.putString("results", data);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fromActivity = getArguments().getString("results","");
        Log.i(TAG, "OnCreate: Получение строки из Bundle");

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_results, container, false);

        TextView textView = view.findViewById(R.id.textView_results);
        textView.setText(fromActivity);

        Log.i(TAG, "OnCreateView: Загрузка окна фрагмента " + fromActivity);

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
