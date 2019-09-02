package com.example.markable.footballapptest.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.markable.footballapptest.AddProtocolActivity;
import com.example.markable.footballapptest.Classes.Player;
import com.example.markable.footballapptest.R;

import java.util.ArrayList;

public class FragmentProtocolTeam  extends Fragment {

    private static final String TAG = FragmentProtocolTeam.class.getSimpleName();

    private static final String KEY = "protocol_name";
    private static final String KEY_2 = "protocol_position";

    private int _50dp;
    private int _1dp;
    private int whiteColor;
    private int yellowCard;
    private int redCard;

    AddProtocolActivity activity;

    TableLayout table;

    String teamName;

    ArrayList<Player> players = new ArrayList<>();

    public static FragmentProtocolTeam newInstance(String name, int position){
        FragmentProtocolTeam fragment = new FragmentProtocolTeam();
        Bundle args = new Bundle();
        args.putString(KEY, name);
        args.putInt(KEY_2, position);
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

        whiteColor = ContextCompat.getColor(getActivity(), R.color.backCell);
        yellowCard = R.drawable.popup_yc;
        redCard = R.drawable.popup_rc;
        activity = (AddProtocolActivity)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_protocol_team, container,false);
        table = view.findViewById(R.id.protocol_team);
        if(getArguments()!=null){
            teamName = getArguments().getString(KEY);
            if(getArguments().getInt(KEY_2) == 0){
                players = activity.getPlayerHome();
            }else{
                players = activity.getPlayerVisit();
            }
            //plug(12);
        }
        //Создание таблицы для занесения протокола
        for(int i = -1; i < players.size(); i++){
            TableRow tableRow = new TableRow(getActivity());
            tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            for(int j = 0; j < 7; j++){
                if(i == -1){
                    //Шапка таблицы
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
                            case 0: textView.setText("В игре"); break;
                            case 1: textView.setText("#"); break;
                            case 2: textView.setText("ФИО");break;
                            case 3: textView.setText("Гол");break;
                            case 4: textView.setText("Пас");break;
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
                }else{
                    TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    //params.gravity = Gravity.CENTER;
                    params.setMargins(_1dp, _1dp, _1dp, _1dp);
                    if(j == 2){
                        TextView textView = new TextView(getActivity());
                        textView.setGravity(Gravity.CENTER);
                        textView.setBackgroundColor(whiteColor);
                        textView.setLayoutParams(params);
                        Player player = players.get(i);
                        textView.setText(String.valueOf(player.getPlayerName()));
                        tableRow.addView(textView, j);
                    }else if(j == 0){
                        //Сначала создаем LinerLayout
                        LinearLayout linearLayout = new LinearLayout(getActivity());
                        linearLayout.setOrientation(LinearLayout.VERTICAL);
                        linearLayout.setLayoutParams(params);
                        linearLayout.setBackgroundColor(whiteColor);
                        tableRow.addView(linearLayout, j);
                        //Созадем параметры для этого layout
                        LinearLayout.LayoutParams lin_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.MATCH_PARENT);
                        lin_params.gravity = Gravity.CENTER;
                        CheckBox checkBox = new CheckBox(getActivity());
                        //checkBox.setGravity(Gravity.CENTER);
                        checkBox.setBackgroundColor(whiteColor);
                        checkBox.setLayoutParams(lin_params);
                        linearLayout.addView(checkBox);

                    }else{
                        EditText editText = new EditText(getActivity());
                        editText.setGravity(Gravity.CENTER);
                        editText.setBackgroundColor(whiteColor);
                        editText.setLayoutParams(params);
                        editText.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                        switch (j){
                            case 4:
                            case 5:
                                editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                                break;
                             default:
                                 editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(2) });
                                 break;
                        }

                        tableRow.addView(editText, j);
                    }
                    /*switch (j){
                        case 0: textView.setText(String.valueOf(player.getNumber())); break;
                        case 1: textView.setText(String.valueOf(player.getPlayerName())); break;
                        case 2: textView.setText(String.valueOf(player.getGoal())); break;
                        case 3: textView.setText(String.valueOf(player.getAssist())); break;
                        case 4: textView.setText(String.valueOf(player.getYellowCard())); break;
                        case 5: textView.setText(String.valueOf(player.getRedCard())); break;
                    }*/
                }
            }
            table.addView(tableRow, i+1);
        }

        return view;
    }

    void plug(int n){
        for(int i = 0; i < n; i++){
            players.add(new Player(teamName, "Игрок" + i + " игрок"));
        }
    }
}
