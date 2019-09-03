package com.example.markable.footballapptest.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.markable.footballapptest.Adapters.SampleFragmentPageAdapter;
import com.example.markable.footballapptest.Classes.ImageFromServer;
import com.example.markable.footballapptest.Classes.NextMatches;
import com.example.markable.footballapptest.Classes.PrevMatches;
import com.example.markable.footballapptest.Classes.PublicConstants;
import com.example.markable.footballapptest.Classes.TournamentTable;
import com.example.markable.footballapptest.MainActivity;
import com.example.markable.footballapptest.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class FragmentMain extends Fragment {

    private static final String TAG = "FragMain";
    View view;

    private ArrayList<ImageFromServer> imageArray = new ArrayList<>();
    private ArrayList<TournamentTable> tournamentTable = new ArrayList<>();
    private ArrayList<PrevMatches> prevResultsMatch = new ArrayList<>();
    private ArrayList<NextMatches> nextResultsMatch = new ArrayList<>();

    SampleFragmentPageAdapter mAdapter;

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "OnCreate: ");
    }


    public void update (){
        Log.i(TAG, "Interface: Передаче Pager-у");
        //new ServerConnectTestDouble().execute(idDivision);
        mAdapter.update(PublicConstants.OPTION_UPDATE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        Log.i(TAG, "OnCreateView: Загрузка главного фрагмента");

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.sliding_tabs);

        MainActivity activity = (MainActivity)getActivity();
        nextResultsMatch = activity.getNextResultsMatch();
        prevResultsMatch = activity.getPrevResultsMatch();
        tournamentTable = activity.getTournamentTable();
        mAdapter = new SampleFragmentPageAdapter(getChildFragmentManager(), getContext());
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
       // new ServerConnectTest().execute("1");
       // update("1");
        /*while (table.equals("")){
        }
        if (!table.equals("")){
            mAdapter = new SampleFragmentPageAdapter(getChildFragmentManager(), getContext(), table, prevResults, imageBitmap);
            Log.i(TAG, "Зашел в if. Поля основного класса\n " + "Таблица ="  + table + "\n Результаты =" + prevResults);
            ViewPager viewPager = view.findViewById(R.id.viewPager);
            //viewPager.setAdapter(new SampleFragmentPageAdapter(getChildFragmentManager(), getContext(), table, prevResults));
            viewPager.setAdapter(mAdapter);
            TabLayout tabLayout = view.findViewById(R.id.sliding_tabs);
            tabLayout.setupWithViewPager(viewPager);
        }*/

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
