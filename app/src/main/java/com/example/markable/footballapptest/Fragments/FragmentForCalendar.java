package com.example.markable.footballapptest.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.markable.footballapptest.Adapters.AdapterForCalendar;
import com.example.markable.footballapptest.Classes.ImageFromServer;
import com.example.markable.footballapptest.Classes.NextMatches;
import com.example.markable.footballapptest.Classes.PrevMatches;
import com.example.markable.footballapptest.Classes.TournamentTable;
import com.example.markable.footballapptest.R;
import com.example.markable.footballapptest.UpdateFragListener;

import java.util.ArrayList;

public class FragmentForCalendar extends Fragment implements UpdateFragListener {

    private static final String TAG = "FragCal";

    private ArrayList<NextMatches> newNextMatches = new ArrayList<>();
    private ArrayList<ImageFromServer> imageBitmap;
    RecyclerView recyclerView;
    AdapterForCalendar adapter;

    public static FragmentForCalendar newInstance(ArrayList<NextMatches> calendar, ArrayList<ImageFromServer> image){
        Log.i(TAG, "newInstance: " + calendar);
        FragmentForCalendar fragment = new FragmentForCalendar();
        Bundle args = new Bundle();
        args.putSerializable("calendar", calendar);
        args.putSerializable("imageForCalendar", image);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "OnCreate:");
        newNextMatches = (ArrayList<NextMatches>) getArguments().getSerializable("calendar");
        imageBitmap = (ArrayList<ImageFromServer>) getArguments().getSerializable("imageForCalendar");
        Log.i(TAG, "onCreate: Массив картинок = " + imageBitmap.size());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_calendar, container, false);
        Log.i(TAG, "onCreateView: начало ветки для календаря");
        Log.i(TAG, "OnCreateView: Загрузка окна фрагмента " );
        Log.i(TAG, "onCreateView: Размер массива " + newNextMatches.size());

        recyclerView = (RecyclerView) view.findViewById(R.id.listForCalendar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterForCalendar(newNextMatches, imageBitmap);
        recyclerView.setAdapter(adapter);

       /* if(newNextMatches.size() != 0){
            update(null, null, newNextMatches);
        }else {

        }*/

        return view;
    }

    @Override
    public void update(ArrayList<TournamentTable> divTable, ArrayList<PrevMatches> prevResults, ArrayList<NextMatches> calendar) {
        Log.i(TAG, "Interface: ");
        this.newNextMatches = calendar;
        adapter.update(newNextMatches, imageBitmap);
        //adapter.notifyDataSetChanged();
        Log.i(TAG, "update: " + newNextMatches);
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
