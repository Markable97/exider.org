package com.example.markable.footballapptest.Fragments;

import android.content.Context;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.markable.footballapptest.Classes.Player;
import com.example.markable.footballapptest.R;

import java.util.ArrayList;

public class FragmentStatisticSquadList extends Fragment {

    private static final String TAG = "FragSquad";

    private ArrayList<Player> arrayPlayers;

    TableLayout table;
    private int goalkeepers;
    private int defenders;
    private int halfbacks;
    private int forwards;
    private int _50dp;
    private int _1dp;
    private int whiteColor;
    private int yellowCard;
    private int redCard;

    public static FragmentStatisticSquadList newInstance(ArrayList<Player> arrayList){
        FragmentStatisticSquadList fragment = new FragmentStatisticSquadList();
        Bundle args = new Bundle();
        args.putSerializable("arrayPlayers", arrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final float scale = context.getResources().getDisplayMetrics().density;
        _50dp = (int) (49*scale*0.5f);
        _1dp = (int)(1*scale*0.5f);

    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayPlayers = (ArrayList<Player>) getArguments().getSerializable("arrayPlayers");
        Log.i(TAG, "onCreate: длина массива =  " + arrayPlayers.toString());
        goalkeepers = countGoalkeeper(arrayPlayers);
        defenders = countDefender(arrayPlayers);
        halfbacks = countHalfback(arrayPlayers);
        forwards = countForward(arrayPlayers);
        whiteColor = ContextCompat.getColor(getActivity(), R.color.backCell);
        yellowCard = R.drawable.popup_yc;
        redCard = R.drawable.popup_rc;
    }

    int countDefender (ArrayList<Player> array){
        int count = 0;
        for(int i = 0; i < array.size(); i++){
            if(array.get(i).getAmplua().equalsIgnoreCase("Защитник")){
                count++;
            }
        }
        Log.i(TAG, "countDefender: " + count);
        return count;
    }
    int countGoalkeeper (ArrayList<Player> array){
        int count = 0;
        for(int i = 0; i < array.size(); i++){
            if(array.get(i).getAmplua().equalsIgnoreCase("Вратарь")){
                count++;
            }
        }
        Log.i(TAG, "countGoalkeeper: " + count);
        return count;
    }
    int countHalfback (ArrayList<Player> array){
        int count = 0;
        for(int i = 0; i < array.size(); i++){
            if(array.get(i).getAmplua().equalsIgnoreCase("Полузащитник")){
                count++;
            }
        }
        Log.i(TAG, "countHalfback: " + count);
        return count;
    }
    int countForward (ArrayList<Player> array){
        int count = 0;
        for(int i = 0; i < array.size(); i++){
            if(array.get(i).getAmplua().equalsIgnoreCase("Нападающий")){
                count++;
            }
        }
        Log.i(TAG, "countForward: " + count);
        return count;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic_squad, container, false);
        Log.i(TAG, "onCreateView: Новая ветка");
        table = view.findViewById(R.id.table_squad);

        int numberPlayer = 0;
        for(int i = -1; i < arrayPlayers.size() + 4; i++){
            Log.i(TAG, "onCreateView: I ================================= " + i);
            TableRow tableRow = new TableRow(getActivity());
            tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            for(int j = 0; j < 7; j++){
                if(i==-1){//шапка таблицы
                    TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    //params.gravity = Gravity.CENTER;
                    params.setMargins(_1dp, _1dp, _1dp, _1dp);
                    if(j < 5){
                        TextView textView = new TextView(getActivity());
                        textView.setLayoutParams(params);
                        textView.setGravity(Gravity.CENTER);
                        textView.setBackgroundColor(whiteColor);
                        switch (j){
                            case 0: textView.setText("#"); break;
                            case 1: textView.setText("ФИО");break;
                            case 2: textView.setText("Игры");break;
                            case 3: textView.setText("Голы");break;
                            case 4: textView.setText("Пасы");break;
                        }
                        Log.i(TAG, "onCreateView: Создание J++++)"  + j);
                        tableRow.addView(textView, j);
                    }else{
                        ImageView imageView = new ImageView(getActivity());
                        imageView.setLayoutParams(params);
                        imageView.setBackgroundColor(whiteColor);
                        switch (j){
                            case 5: imageView.setImageResource(yellowCard); break;
                            case 6: imageView.setImageResource(redCard); break;
                        }
                        Log.i(TAG, "onCreateView: Создание J++++)"  + j);
                        tableRow.addView(imageView, j);
                    }
                }else{//все остальное
                    Log.i(TAG, "onCreateView: остальное i = " + i + "J========" + j);
                    TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    //params.gravity = Gravity.CENTER;
                    params.setMargins(_1dp, _1dp, _1dp, _1dp);
                    TextView textView = new TextView(getActivity());
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundColor(whiteColor);
                    if(i==0 && j==0){//шапка вратаря
                        params.span = 7;
                        textView.setLayoutParams(params);
                        textView.setText("Вратарь");
                        Log.i(TAG, "onCreateView: Шаааааааапка i = " + i);
                        Log.i(TAG, "onCreateView: Создание J++++)"  + j);
                        tableRow.addView(textView, j);
                    }else if((i>0 && i<(arrayPlayers.size() + 4 - forwards - halfbacks - defenders - 3)) ){//список вратарей
                        textView.setLayoutParams(params);
                        Log.i(TAG, "onCreateView: Вратари i ="  + i + "# = "+ numberPlayer);
                        Player player = arrayPlayers.get(numberPlayer);
                        switch(j){
                            case 0: textView.setText(String.valueOf(numberPlayer + 1)); break;
                            case 1:
                                LinearLayout linearLayout = new LinearLayout(getActivity());
                                LinearLayout.LayoutParams paramsLa = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT, 1f);
                                paramsLa.gravity = Gravity.CENTER;
                                linearLayout.setBackgroundColor(whiteColor);
                                linearLayout.setLayoutParams(params);
                                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                ImageView imageView = new ImageView(getActivity());
                                //imageView.setLayoutParams(paramsLa);
                                imageView.setImageBitmap(player.getPlayerImage());
                                TextView tv = new TextView(getActivity());
                                tv.setGravity(Gravity.CENTER);
                                tv.setLayoutParams(paramsLa);
                                tv.setText(player.getPlayerName());
                                //textView.setText(player.getPlayerName());
                                linearLayout.addView(imageView);
                                linearLayout.addView(tv);
                                tableRow.addView(linearLayout);
                                numberPlayer++; break;
                            case 2: textView.setText(String.valueOf(player.getGames())); break;
                            case 3: textView.setText(String.valueOf(player.getGoal())); break;
                            case 4: textView.setText(String.valueOf(player.getAssist())); break;
                            case 5: textView.setText(String.valueOf(player.getYellowCard())); break;
                            case 6: textView.setText(String.valueOf(player.getRedCard())); break;
                        }
                        Log.i(TAG, "onCreateView: Создание J++++)"  + j);
                        if(j!=1){
                            tableRow.addView(textView, j);
                        }
                    } else if (i > goalkeepers && i <= (arrayPlayers.size() + 4 - forwards - halfbacks - defenders - 3) && j==0) {//шапка защитников
                        params.span = 7;
                        textView.setLayoutParams(params);
                        textView.setText("Защитник");
                        Log.i(TAG, "onCreateView: Шааааапка i = " + i);
                        Log.i(TAG, "onCreateView: Создание J++++)"  + j);
                        tableRow.addView(textView, j);
                    } else if (i > goalkeepers + 1  && i < (arrayPlayers.size() + 4 - forwards - halfbacks  - 2) ){//список защитников
                        textView.setLayoutParams(params);
                        Player player = arrayPlayers.get(numberPlayer);
                        switch(j){
                            case 0: textView.setText(String.valueOf(numberPlayer + 1)); break;
                            case 1:
                                LinearLayout linearLayout = new LinearLayout(getActivity());
                                LinearLayout.LayoutParams paramsLa = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT, 1f);
                                paramsLa.gravity = Gravity.CENTER;
                                linearLayout.setBackgroundColor(whiteColor);
                                linearLayout.setLayoutParams(params);
                                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                ImageView imageView = new ImageView(getActivity());
                                //imageView.setLayoutParams(paramsLa);
                                imageView.setImageBitmap(player.getPlayerImage());
                                TextView tv = new TextView(getActivity());
                                tv.setGravity(Gravity.CENTER);
                                tv.setLayoutParams(paramsLa);
                                tv.setText(player.getPlayerName());
                                //textView.setText(player.getPlayerName());
                                linearLayout.addView(imageView);
                                linearLayout.addView(tv);
                                tableRow.addView(linearLayout);
                                numberPlayer++; break;
                            case 2: textView.setText(String.valueOf(player.getGames())); break;
                            case 3: textView.setText(String.valueOf(player.getGoal())); break;
                            case 4: textView.setText(String.valueOf(player.getAssist())); break;
                            case 5: textView.setText(String.valueOf(player.getYellowCard())); break;
                            case 6: textView.setText(String.valueOf(player.getRedCard())); break;
                        }
                        Log.i(TAG, "onCreateView: Защитники  i ="  + i + "# = "+ numberPlayer);
                        Log.i(TAG, "onCreateView: Создание J++++)"  + j);
                        if(j!=1){
                            tableRow.addView(textView, j);
                        }
                    } else  if ((i > goalkeepers + 1 + defenders) && (i <= (arrayPlayers.size() + 4 - forwards - halfbacks  - 2)) && (j==0) ){//шапка полу
                        params.span = 7;
                        textView.setLayoutParams(params);
                        textView.setText("Полузащитник");
                        Log.i(TAG, "onCreateView: Шааааапка полу i = " + i);
                        Log.i(TAG, "onCreateView: Создание J++++)"  + j);
                        tableRow.addView(textView, j);
                    } else if((i > goalkeepers + 1 + defenders + 1) && (i < (arrayPlayers.size() + 4 - forwards - 1))){//список полу
                        textView.setLayoutParams(params);
                        Log.i(TAG, "onCreateView: Полу  i ="  + i + "# = "+ numberPlayer);
                        Player player = arrayPlayers.get(numberPlayer);
                        switch(j){
                            case 0: textView.setText(String.valueOf(numberPlayer + 1)); break;
                            case 1:
                                LinearLayout linearLayout = new LinearLayout(getActivity());
                                LinearLayout.LayoutParams paramsLa = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT, 1f);
                                paramsLa.gravity = Gravity.CENTER;
                                linearLayout.setBackgroundColor(whiteColor);
                                linearLayout.setLayoutParams(params);
                                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                ImageView imageView = new ImageView(getActivity());
                                //imageView.setLayoutParams(paramsLa);
                                imageView.setImageBitmap(player.getPlayerImage());
                                TextView tv = new TextView(getActivity());
                                tv.setGravity(Gravity.CENTER);
                                tv.setLayoutParams(paramsLa);
                                tv.setText(player.getPlayerName());
                                //textView.setText(player.getPlayerName());
                                linearLayout.addView(imageView);
                                linearLayout.addView(tv);
                                tableRow.addView(linearLayout);
                                numberPlayer++; break;
                            case 2: textView.setText(String.valueOf(player.getGames())); break;
                            case 3: textView.setText(String.valueOf(player.getGoal())); break;
                            case 4: textView.setText(String.valueOf(player.getAssist())); break;
                            case 5: textView.setText(String.valueOf(player.getYellowCard())); break;
                            case 6: textView.setText(String.valueOf(player.getRedCard())); break;
                        }
                        Log.i(TAG, "onCreateView: Создание J++++)"  + j);
                        if(j!=1){
                            tableRow.addView(textView, j);
                        }
                    } else if((i > goalkeepers + 1 + defenders + 1 + halfbacks) && (i <= (arrayPlayers.size() + 4 - forwards - 1)) && (j==0)){//шапка нап
                        Log.i(TAG, "onCreateView: Шааааапка напа i = " + i);
                        params.span = 7;
                        textView.setLayoutParams(params);
                        textView.setText("Нападающий");
                        Log.i(TAG, "onCreateView: Создание J++++)"  + j);
                        tableRow.addView(textView, j);
                    } else if((i > goalkeepers + 1 + defenders + 1 + halfbacks + 1) && (i< arrayPlayers.size() + 4)){
                        Log.i(TAG, "onCreateView: Нап  i ="  + i + "# = "+ numberPlayer);
                        textView.setLayoutParams(params);
                        Player player = arrayPlayers.get(numberPlayer);
                        switch(j){
                            case 0: textView.setText(String.valueOf(numberPlayer + 1)); break;
                            case 1:
                                LinearLayout linearLayout = new LinearLayout(getActivity());
                                LinearLayout.LayoutParams paramsLa = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT, 1f);
                                paramsLa.gravity = Gravity.CENTER;
                                linearLayout.setBackgroundColor(whiteColor);
                                linearLayout.setLayoutParams(params);
                                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                ImageView imageView = new ImageView(getActivity());
                                //imageView.setLayoutParams(paramsLa);
                                imageView.setImageBitmap(player.getPlayerImage());
                                TextView tv = new TextView(getActivity());
                                tv.setGravity(Gravity.CENTER);
                                tv.setLayoutParams(paramsLa);
                                tv.setText(player.getPlayerName());
                                //textView.setText(player.getPlayerName());
                                linearLayout.addView(imageView);
                                linearLayout.addView(tv);
                                tableRow.addView(linearLayout);
                                break;
                            case 2: textView.setText(String.valueOf(player.getGames())); break;
                            case 3: textView.setText(String.valueOf(player.getGoal())); break;
                            case 4: textView.setText(String.valueOf(player.getAssist())); break;
                            case 5: textView.setText(String.valueOf(player.getYellowCard())); break;
                            case 6: textView.setText(String.valueOf(player.getRedCard())); break;
                        }
                        Log.i(TAG, "onCreateView: Создание J++++)"  + j);
                        if(j!=1){
                            tableRow.addView(textView, j);
                        }
                    }
                }
            }
            Log.i(TAG, "onCreateView: Создание i+++++++++++++++++++)"  + i);
            table.addView(tableRow, i+1);
        }

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
