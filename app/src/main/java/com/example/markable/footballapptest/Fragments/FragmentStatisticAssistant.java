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
import com.example.markable.footballapptest.R;

import java.util.ArrayList;
import java.util.Collections;

public class FragmentStatisticAssistant extends Fragment {

    private static final String TAG = "FragAssist";

    private ArrayList<Player> arrayPlayers;
    private ArrayList<Player> newArrayPlayers = new ArrayList<>();
    private TableLayout table;
    private int _1dp;
    private int whiteColor;

    public static FragmentStatisticAssistant  newInstance(ArrayList<Player> arrayList){
        FragmentStatisticAssistant fragment = new FragmentStatisticAssistant();
        Bundle args = new Bundle();
        args.putSerializable("arrayPlayers", arrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final float scale = context.getResources().getDisplayMetrics().density;
        _1dp = (int)(1*scale*0.5f);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayPlayers = (ArrayList<Player>) getArguments().getSerializable("arrayPlayers");
        for(int i=0; i<arrayPlayers.size(); i++){
            newArrayPlayers.add(arrayPlayers.get(i));
        }
        Collections.sort(newArrayPlayers, Player.sortAssist);
        Log.i(TAG, "onCreate: длина массива =  " + newArrayPlayers.toString());
        whiteColor = ContextCompat.getColor(getActivity(), R.color.backCell);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic_assistant, container, false);

        table = view.findViewById(R.id.tableForAssistant);

        int numberPlayer = 0;
        for(int i = -1; i<newArrayPlayers.size(); i++){
            TableRow tableRow = new TableRow(getActivity());
            tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            for(int j = 0; j < 3 ; j++){
                if(i == -1){
                    TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    //params.gravity = Gravity.CENTER;
                    params.setMargins(_1dp, _1dp, _1dp, _1dp);
                    TextView textView = new TextView(getActivity());
                    textView.setLayoutParams(params);
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundColor(whiteColor);
                    switch (j){
                        case 0: textView.setText("№");break;
                        case 1: textView.setText("ФИО");break;
                        case 2: textView.setText("Пасы");break;
                    }
                    tableRow.addView(textView, j);
                }else{
                    Player player = newArrayPlayers.get(i);
                    TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    //params.gravity = Gravity.CENTER;
                    params.setMargins(_1dp, _1dp, _1dp, _1dp);
                    TextView textView = new TextView(getActivity());
                    textView.setLayoutParams(params);
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundColor(whiteColor);
                    switch (j){
                        case 0: textView.setText(String.valueOf(i+1)); break;
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
                        case 2: textView.setText(String.valueOf(player.getAssist())); break;
                    }
                    if(j!=1){
                        tableRow.addView(textView, j);
                    }
                }
            }
            table.addView(tableRow, i+1);
            numberPlayer++;
        }

        return view;
    }

}
