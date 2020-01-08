package com.example.markable.footballapptest.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.markable.footballapptest.Classes.Player;
import com.example.markable.footballapptest.Classes.PlayerInStatistic;
import com.example.markable.footballapptest.R;

import java.util.ArrayList;

public class FragmentStatisticSquadList extends Fragment {

    private static final String TAG = "FragSquad";

    private ArrayList<Player> arrayPlayers;
    private ArrayList<PlayerInStatistic> playersForTable = new ArrayList<>();
    private ArrayList<PlayerInStatistic> goalkeepers = new ArrayList<>();
    private ArrayList<PlayerInStatistic> defenders = new ArrayList<>();
    private ArrayList<PlayerInStatistic> halfbacks = new ArrayList<>();
    private ArrayList<PlayerInStatistic> forwards = new ArrayList<>();
    private ArrayList<PlayerInStatistic> ussual = new ArrayList<>();
    TableLayout table;
    private int cntGoalkeepers;
    private int cntDefenders;
    private int cntHalfbacks;
    private int cntForwards;
    private int cntUssual;
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
        sortPlayers();
        /*cntGoalkeepers = countGoalkeeper(arrayPlayers);
        cntDefenders = countDefender(arrayPlayers);
        cntHalfbacks = countHalfback(arrayPlayers);
        cntForwards = countForward(arrayPlayers);*/
        whiteColor = ContextCompat.getColor(getActivity(), R.color.backCell);
        yellowCard = R.drawable.popup_yc;
        redCard = R.drawable.popup_rc;
    }

    void sortPlayers(){
        for(Player p : arrayPlayers){
            switch (p.getAmplua()){
                case "Вратарь":
                    goalkeepers.add(new PlayerInStatistic("Cell", p));
                    cntGoalkeepers++;
                    break;
                case "Защитник":
                    defenders.add(new PlayerInStatistic("Cell", p));
                    cntDefenders++;
                    break;
                case "Полузащитник":
                    halfbacks.add(new PlayerInStatistic("Cell", p));
                    cntHalfbacks++;
                    break;
                case "Нападающий":
                    forwards.add(new PlayerInStatistic("Cell", p));
                    cntForwards++;
                    break;
                case "Полевой игрок":
                    ussual.add(new PlayerInStatistic("Cell", p));
                    cntUssual++;
                    break;
                default:
                    break;
            }
        }
        playersForTable.add(new PlayerInStatistic("Head"));
        if(cntGoalkeepers > 0 ){
            playersForTable.add(new PlayerInStatistic("Amplua", "Вратарь"));
            playersForTable.addAll(goalkeepers);
        }
        if(cntDefenders > 0 ){
            playersForTable.add(new PlayerInStatistic("Amplua", "Защитник"));
            playersForTable.addAll(defenders);
        }
        if(cntHalfbacks > 0 ){
            playersForTable.add(new PlayerInStatistic("Amplua", "Полузащитник"));
            playersForTable.addAll(halfbacks);
        }
        if(cntForwards > 0 ){
            playersForTable.add(new PlayerInStatistic("Amplua", "Нападающий"));
            playersForTable.addAll(forwards);
        }
        if(cntUssual > 0 ){
            playersForTable.add(new PlayerInStatistic("Amplua", "Полевой игрок"));
            playersForTable.addAll(ussual);
        }
    }

    /*int countDefender (ArrayList<Player> array){
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
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic_squad, container, false);
        Log.i(TAG, "onCreateView: Создание таблицы");
        table = view.findViewById(R.id.table_squad);
        Log.i(TAG, playersForTable.toString());
        int cnt = 1;
        for(PlayerInStatistic p : playersForTable){
            TableRow tableRow = new TableRow(getActivity());
            tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            //params.gravity = Gravity.CENTER;
            params.setMargins(_1dp, _1dp, _1dp, _1dp);
            switch (p.typeCell){
                case "Head":
                    for(int j = 0; j < 7; j++){
                        if(j < 5){
                            TextView textView = new TextView(getActivity());
                            textView.setLayoutParams(params);
                            textView.setGravity(Gravity.CENTER);
                            textView.setBackgroundColor(whiteColor);
                            switch (j){
                                case 0: textView.setText("#");break;
                                case 1: textView.setText("ФИО");break;
                                case 2: textView.setText("Игры");break;
                                case 3: textView.setText("Голы");break;
                                case 4: textView.setText("Пасы");break;
                            }
                            tableRow.addView(textView);
                        }else{
                            ImageView imageView = new ImageView(getActivity());
                            imageView.setLayoutParams(params);
                            imageView.setBackgroundColor(whiteColor);
                            switch (j){
                                case 5: imageView.setImageResource(yellowCard); break;
                                case 6: imageView.setImageResource(redCard); break;
                            }
                            //     Log.i(TAG, "onCreateView: Создание J++++)"  + j);
                            tableRow.addView(imageView);
                        }
                    }
                    break;
                case "Amplua":
                    TextView textView1= new TextView(getActivity());
                    textView1.setGravity(Gravity.CENTER);
                    textView1.setBackgroundColor(whiteColor);
                    params.span = 7;
                    textView1.setLayoutParams(params);
                    textView1.setText(p.amplua);
                    tableRow.addView(textView1);
                    break;
                case "Cell":
                    Player player = p.player;
                    for(int j = 0; j < 7; j++){
                        TextView textView = new TextView(getActivity());
                        textView.setLayoutParams(params);
                        textView.setGravity(Gravity.CENTER);
                        textView.setBackgroundColor(whiteColor);
                        switch(j){
                            case 0:
                                textView.setText(String.valueOf(cnt)); tableRow.addView(textView); break;
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
                            case 2: textView.setText(String.valueOf(player.getGames())); tableRow.addView(textView);break;
                            case 3: textView.setText(String.valueOf(player.getGoal())); tableRow.addView(textView);break;
                            case 4: textView.setText(String.valueOf(player.getAssist())); tableRow.addView(textView);break;
                            case 5: textView.setText(String.valueOf(player.getYellowCard())); tableRow.addView(textView);break;
                            case 6: textView.setText(String.valueOf(player.getRedCard())); tableRow.addView(textView);break;
                        }
                    }
                    cnt++;
                    break;
            }
            table.addView(tableRow);
        }

        /*int numberPlayer = 0;
        boolean flag;
        for(int i = -1; i < arrayPlayers.size() + 4; i++){
            //Log.i(TAG, "onCreateView: I ================================= " + i);
            flag = false;
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
                   //     Log.i(TAG, "onCreateView: Создание J++++)"  + j);
                        tableRow.addView(textView, j);
                    }else{
                        ImageView imageView = new ImageView(getActivity());
                        imageView.setLayoutParams(params);
                        imageView.setBackgroundColor(whiteColor);
                        switch (j){
                            case 5: imageView.setImageResource(yellowCard); break;
                            case 6: imageView.setImageResource(redCard); break;
                        }
                   //     Log.i(TAG, "onCreateView: Создание J++++)"  + j);
                        tableRow.addView(imageView, j);
                    }
                }else{//все остальное
                 //   Log.i(TAG, "onCreateView: остальное i = " + i + "J========" + j);
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
                       // Log.i(TAG, "onCreateView: Шаааааааапка i = " + i);
                       // Log.i(TAG, "onCreateView: Создание J++++)"  + j);
                        tableRow.addView(textView, j);
                    }else if((i>0 && i<(arrayPlayers.size() + 4 - cntForwards - cntHalfbacks - cntDefenders - 3)) ){//список вратарей
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
                                flag = true; break;
                            case 2: textView.setText(String.valueOf(player.getGames())); break;
                            case 3: textView.setText(String.valueOf(player.getGoal())); break;
                            case 4: textView.setText(String.valueOf(player.getAssist())); break;
                            case 5: textView.setText(String.valueOf(player.getYellowCard())); break;
                            case 6: textView.setText(String.valueOf(player.getRedCard())); break;
                        }
                       // Log.i(TAG, "onCreateView: Создание J++++)"  + j);
                        if(j!=1){
                            tableRow.addView(textView, j);
                        }
                    } else if (i > cntGoalkeepers && i <= (arrayPlayers.size() + 4 - cntForwards - cntHalfbacks - cntDefenders - 3) && j==0) {//шапка защитников
                        params.span = 7;
                        textView.setLayoutParams(params);
                        textView.setText("Защитник");
                      //  Log.i(TAG, "onCreateView: Шааааапка i = " + i);
                        //Log.i(TAG, "onCreateView: Создание J++++)"  + j);
                        tableRow.addView(textView, j);
                    } else if (i > cntGoalkeepers + 1  && i < (arrayPlayers.size() + 4 - cntForwards - cntHalfbacks - 2) ){//список защитников
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
                                flag = true; break;
                            case 2: textView.setText(String.valueOf(player.getGames())); break;
                            case 3: textView.setText(String.valueOf(player.getGoal())); break;
                            case 4: textView.setText(String.valueOf(player.getAssist())); break;
                            case 5: textView.setText(String.valueOf(player.getYellowCard())); break;
                            case 6: textView.setText(String.valueOf(player.getRedCard())); break;
                        }
                       // Log.i(TAG, "onCreateView: Защитники  i ="  + i + "# = "+ numberPlayer);
                       // Log.i(TAG, "onCreateView: Создание J++++)"  + j);
                        if(j!=1){
                            tableRow.addView(textView, j);
                        }
                    } else  if ((i > cntGoalkeepers + 1 + cntDefenders) && (i <= (arrayPlayers.size() + 4 - cntForwards - cntHalfbacks - 2)) && (j==0) ){//шапка полу
                        params.span = 7;
                        textView.setLayoutParams(params);
                        textView.setText("Полузащитник");
                      //  Log.i(TAG, "onCreateView: Шааааапка полу i = " + i);
                       // Log.i(TAG, "onCreateView: Создание J++++)"  + j);
                        tableRow.addView(textView, j);
                    } else if((i > cntGoalkeepers + 1 + cntDefenders + 1) && (i < (arrayPlayers.size() + 4 - cntForwards - 1))){//список полу
                        textView.setLayoutParams(params);
                        //Log.i(TAG, "onCreateView: Полу  i ="  + i + "# = "+ numberPlayer);
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
                                flag = true; break;
                            case 2: textView.setText(String.valueOf(player.getGames())); break;
                            case 3: textView.setText(String.valueOf(player.getGoal())); break;
                            case 4: textView.setText(String.valueOf(player.getAssist())); break;
                            case 5: textView.setText(String.valueOf(player.getYellowCard())); break;
                            case 6: textView.setText(String.valueOf(player.getRedCard())); break;
                        }
                       // Log.i(TAG, "onCreateView: Создание J++++)"  + j);
                        if(j!=1){
                            tableRow.addView(textView, j);
                        }
                    } else if((i > cntGoalkeepers + 1 + cntDefenders + 1 + cntHalfbacks) && (i <= (arrayPlayers.size() + 4 - cntForwards - 1)) && (j==0)){//шапка нап
                       // Log.i(TAG, "onCreateView: Шааааапка напа i = " + i);
                        params.span = 7;
                        textView.setLayoutParams(params);
                        textView.setText("Нападающий");
                        //Log.i(TAG, "onCreateView: Создание J++++)"  + j);
                        tableRow.addView(textView, j);
                    } else if((i > cntGoalkeepers + 1 + cntDefenders + 1 + cntHalfbacks + 1) && (i< arrayPlayers.size() + 4)){
                       // Log.i(TAG, "onCreateView: Нап  i ="  + i + "# = "+ numberPlayer);
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
                                flag = true;
                                break;
                            case 2: textView.setText(String.valueOf(player.getGames())); break;
                            case 3: textView.setText(String.valueOf(player.getGoal())); break;
                            case 4: textView.setText(String.valueOf(player.getAssist())); break;
                            case 5: textView.setText(String.valueOf(player.getYellowCard())); break;
                            case 6: textView.setText(String.valueOf(player.getRedCard())); break;
                        }
                       // Log.i(TAG, "onCreateView: Создание J++++)"  + j);
                        if(j!=1){
                            tableRow.addView(textView, j);
                        }
                    }
                }
            }
            if(flag==true){
                numberPlayer++;
            }
            //Log.i(TAG, "onCreateView: Создание i+++++++++++++++++++)"  + i);
            table.addView(tableRow, i+1);
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
