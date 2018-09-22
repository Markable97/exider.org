package com.example.markable.footballapptest.Fragments;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markable.footballapptest.Classes.TournamentTable;
import com.example.markable.footballapptest.R;
import com.example.markable.footballapptest.UpdateFragListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class FragmentForTable extends Fragment implements UpdateFragListener{

    private static final String TAG = "FragTest";

    private String fromActivity = null;



    Gson gson = new Gson();

    private ArrayList<TournamentTable> newTournamentTable = new ArrayList<>();

    TextView textView;
    ImageView image;
    private ArrayList<Bitmap> imageBitmap;

    public static FragmentForTable newInstance(String table, ArrayList<Bitmap> image){
        Log.i(TAG, "NewInstance: " + table);
        FragmentForTable fragment = new FragmentForTable();
        Bundle args = new Bundle();
        args.putString("division", table);
        args.putParcelableArrayList("image", image);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "OnCreate:");
        fromActivity = getArguments().getString("division","");
        imageBitmap = getArguments().getParcelableArrayList("image");
        Log.i(TAG, "OnCreate: " + fromActivity);
        Log.i(TAG, "onCreate Bitmap: " + imageBitmap.size());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_table, container, false);
        Log.i(TAG, "OnCreateView: переменная для JSON " + fromActivity);
        Log.i(TAG, "OnCreateView: Загрузка окна фрагмента ");

        Log.i(TAG, "onCreateView: Создание таблицы");

        textView = (TextView) view.findViewById(R.id.textView_test);
        image = view.findViewById(R.id.imageViewTest);
        image.setImageBitmap(imageBitmap.get(0));
        if(fromActivity != null){
            update(fromActivity, null);
        }else {
            textView.setText("Чисто проверить! ");
        }


        return view;
    }


    @Override
    public void update(String divTable, String prevResults) {
        Log.i(TAG, "Interface: Сработал пустой метод");
        this.fromActivity = divTable;
        Log.i(TAG, "Interface: " + fromActivity);
        newTournamentTable.clear();
        newTournamentTable = gson.fromJson(fromActivity, new TypeToken<ArrayList<TournamentTable>>(){}.getType());
        String results = "";
        for(int i = 0; i < newTournamentTable.size(); i++){
            results += newTournamentTable.get(i).getDivisionName() + " " +
                    newTournamentTable.get(i).getTeamName() + " " + newTournamentTable.get(i).getGames()
                    + " " + newTournamentTable.get(i).getPoint() + " " + newTournamentTable.get(i).getWins()
                    + " " + newTournamentTable.get(i).getDraws() + " " + newTournamentTable.get(i).getLosses() + "\n";
        }
        textView.setText(results);
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
