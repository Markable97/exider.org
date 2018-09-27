package com.example.markable.footballapptest.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.markable.footballapptest.Classes.TournamentTable;
import com.example.markable.footballapptest.R;
import com.example.markable.footballapptest.UpdateFragListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class FragmentForTable extends Fragment implements UpdateFragListener{

    private static final String TAG = "FragTest";

    private String fromActivity = null;

    //private final float scale = getBaseContext().getResources().getDisplayMetrics().density;

    int _50dp;

    Gson gson = new Gson();

    private ArrayList<TournamentTable> newTournamentTable = new ArrayList<>();

    TableLayout tableLay;
    //TextView textView;

    public static FragmentForTable newInstance(ArrayList<TournamentTable> table){
        Log.i(TAG, "NewInstance: " + table);
        FragmentForTable fragment = new FragmentForTable();
        Bundle args = new Bundle();
        args.putSerializable("table", table);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final float scale = context.getResources().getDisplayMetrics().density;
        _50dp = (int) (50*scale*0.5f);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "OnCreate:");
        newTournamentTable = (ArrayList<TournamentTable>) getArguments().getSerializable("table");
        //fromActivity = getArguments().getString("division","");
        Log.i(TAG, "OnCreate: " + fromActivity);
        Log.i(TAG, "OnCreate: длина массива " + newTournamentTable.size() );

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_table, container, false);
        Log.i(TAG, "OnCreateView: переменная для JSON " + fromActivity);
        Log.i(TAG, "OnCreateView: Загрузка окна фрагмента ");

        Log.i(TAG, "onCreateView: Создание таблицы");
        tableLay = view.findViewById(R.id.tableForDiv);
        //textView = (TextView) view.findViewById(R.id.textView_test);
        //LinearLayout linearLayout = view.findViewById(R.id.layout_table);
        /*ViewGroup.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for(int i = 0; i < newTournamentTable.size() ; i++){
            ImageView imageView = new ImageView(getContext());
            imageView.setImageBitmap(newTournamentTable.get(i).getImage());
            imageView.setLayoutParams(imageParams);
            linearLayout.addView(imageView);
        }*/
        //image = view.findViewById(R.id.imageViewTest);
        //image.setImageBitmap(imageBitmap.get(0));
        if(newTournamentTable.size() != 0){
            update(newTournamentTable, null);
        }else {
            //textView.setText("Чисто проверить! ");
        }

        Log.i(TAG, "createTable: Создание таблицы");
        int countColumn = 10;
        Log.i(TAG, "createTable: размер = " + newTournamentTable.size());
        for(int i = 0; i < newTournamentTable.size(); i++){
            Log.i(TAG, "createTable: Строка = " + i);
            TableRow tableRow = new TableRow(getActivity());
            tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //tableRow.setBackgroundResource();
            for(int j = 0; j < countColumn; j++){
                Log.i(TAG, "createTable: Столбец = " + j);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                params.gravity = Gravity.CENTER;
                TextView tv = new TextView(getActivity());
                tv.setLayoutParams(params);
                tv.setGravity(Gravity.CENTER);
                switch (j){
                    case 0: tv.setText(String.valueOf(i+1));break;
                    case 1:
                        TableRow.LayoutParams paramsImage = new TableRow.LayoutParams(_50dp, _50dp,1.0f);
                        paramsImage.gravity = Gravity.CENTER;
                        ImageView imageView = new ImageView(getActivity());
                        imageView.setLayoutParams(paramsImage);
                        imageView.setImageBitmap(newTournamentTable.get(i).getImage());
                        tableRow.addView(imageView, j);
                        break;
                    case 2: tv.setText(newTournamentTable.get(i).getTeamName());break;
                    case 3: tv.setText(newTournamentTable.get(i).getGames());break;
                    case 4: tv.setText(newTournamentTable.get(i).getWins());break;
                    case 5: tv.setText(newTournamentTable.get(i).getDraws());break;
                    case 6: tv.setText(newTournamentTable.get(i).getLosses());break;
                    case 7: tv.setText(newTournamentTable.get(i).getGoalsScored());break;
                    case 8: tv.setText(newTournamentTable.get(i).getGoalsConceded());break;
                    case 9: tv.setText(newTournamentTable.get(i).getPoint());break;
                }
                Log.i(TAG, "onCreateView: TV = " + tv.getText());
                if(j!=1){
                    tableRow.addView(tv, j);
                }
            }
            tableLay.addView(tableRow, i);
        }
        return view;
    }

/*
    public void createTable(){
        Log.i(TAG, "createTable: Создание таблицы");
        int countColumn = 10;
        Log.i(TAG, "createTable: размер = " + newTournamentTable.size());
        for(int i = 0; i < newTournamentTable.size(); i++){
            Log.i(TAG, "createTable: Строка = " + i);
            TableRow tableRow = new TableRow(getContext());
            tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //tableRow.setBackgroundResource();
            for(int j = 0; j < countColumn; j++){
                Log.i(TAG, "createTable: Столбец = " + j);
                TextView tv = new TextView(getContext());
                tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
                switch (j){
                    case 0: tv.setText(i+"1");break;
                    case 1:
                        ImageView imageView = new ImageView(getContext());
                        imageView.setLayoutParams(new LinearLayout.LayoutParams(_50dp,_50dp,1.0f));
                        imageView.setImageBitmap(newTournamentTable.get(i).getImage());
                        tableRow.addView(tv, j);
                        break;
                    case 2: tv.setText(newTournamentTable.get(i).getTeamName());break;
                    case 3: tv.setText(String.valueOf(newTournamentTable.get(i).getGames()));break;
                    case 4: tv.setText(String.valueOf(newTournamentTable.get(i).getWins()));break;
                    case 5: tv.setText(String.valueOf(newTournamentTable.get(i).getDraws()));break;
                    case 6: tv.setText(String.valueOf(newTournamentTable.get(i).getLosses()));break;
                    case 7: tv.setText(String.valueOf(newTournamentTable.get(i).getGoalsScored()));break;
                    case 8: tv.setText(String.valueOf(newTournamentTable.get(i).getGoalsConceded()));break;
                    case 9: tv.setText(String.valueOf(newTournamentTable.get(i).getPoint()));break;
                }
                if(j!=1){
                    tableRow.addView(tv, j);
                }
            }
            table.addView(tableRow, i);
        }
    }
*/
    @Override
    public void update(ArrayList divTable, ArrayList prevResults) {
        Log.i(TAG, "Interface: Сработал пустой метод");
        this.newTournamentTable = divTable;
        /*Log.i(TAG, "Interface: " + newTournamentTable.size());
        String results = "";
        for(int i = 0; i < newTournamentTable.size(); i++){
            results += newTournamentTable.get(i).toString();
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
