package com.example.markable.footballapptest.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.example.markable.footballapptest.Classes.PlayerView;
import com.example.markable.footballapptest.R;
import com.example.markable.footballapptest.ReturnFromFragForAct;
import com.example.markable.footballapptest.UpdateFragListener;

import java.util.ArrayList;

public class FragmentProtocolTeam  extends Fragment implements UpdateFragListener{

    private static final String TAG = FragmentProtocolTeam.class.getSimpleName();

    private static final String KEY = "protocol_name";
    private static final String KEY_2 = "protocol_position";

    private ReturnFromFragForAct returnData;

    private int _50dp;
    private int _1dp;
    private int whiteColor;
    private int yellowCard;
    private int redCard;

    AddProtocolActivity activity;

    TableLayout table;

    String teamName;
    ArrayList<PlayerView> playerViews = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();

    private static final int REQUEST_GOAL = 1;
    private static final int REQUEST_ANOTHER_ONE = 2;
    public void openDialog(Player player) {
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentByTag("dialog_goal");
        if (fragment == null) {
            DialogGoal dialog = DialogGoal.newInstance(player);
            dialog.setTargetFragment(this, REQUEST_GOAL);
            dialog.show(getFragmentManager(), "dialog_goal");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_GOAL:
                    int goal = data.getIntExtra(DialogGoal.TAG_GOAL, 0);
                    int penalty = data.getIntExtra(DialogGoal.TAG_PENALTY, 0);
                    int penaltyOut = data.getIntExtra(DialogGoal.TAG_PENALTY_OUT, 0);
                    int ownGoal = data.getIntExtra(DialogGoal.TAG_GOAL_OWN, 0);
                    int idPlayer = data.getIntExtra(DialogGoal.TAG_PLAYER, 0);
                    Log.i(TAG, "Данны из диалога голов "+ idPlayer + " " + goal + " " + penalty + " " + penaltyOut +  " " + ownGoal);
                    updateUI(idPlayer, goal, penalty, penaltyOut, ownGoal);
                    break;
                case REQUEST_ANOTHER_ONE:
                    //...
                    break;
                //обработка других requestCode
            }
        }
    }

    void updateUI(int idPlayer, int goal, int penalty, int penaltyOut, int ownGoal){
        for(Player p : players){
            if(p.getIdPlayer()==idPlayer){
                p.getPlayerView().goal.setText("*");
                p.setGoal(goal);
                p.setOwn_goal(ownGoal);
                p.setPenalty(penalty);
                p.setPenalty_out(penaltyOut);
            }
        }
    }
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
        returnData = (ReturnFromFragForAct) context;
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
        createProtocolTable();
        return view;
    }

    void createProtocolTable(){
        //Сначала очистить
        table.removeAllViews();
        //Создание таблицы для занесения протокола
        for(int i = -1; i < players.size(); i++){
            TableRow tableRow = new TableRow(getActivity());
            tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            PlayerView playerView = null;
            Player player = null;
            if(i > -1){
                playerView = new PlayerView(teamName);
                player = players.get(i);
            }
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
                    } //конец создания шапки
                }else{ //заполнения основной таблицы
                    TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    //params.gravity = Gravity.CENTER;

                    params.setMargins(_1dp, _1dp, _1dp, _1dp);
                    if(j == 2){ //Имя
                        TextView textView = new TextView(getActivity());
                        textView.setGravity(Gravity.CENTER);
                        textView.setBackgroundColor(whiteColor);
                        textView.setLayoutParams(params);
                        textView.setText(String.valueOf(player.getPlayerName()));
                        playerView.setPlayerName(textView);
                        tableRow.addView(textView, j);
                    }else if(j == 0){ //checkBox
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
                        if(player.getInGame() == 1){
                            checkBox.setChecked(true);
                        }
                        //checkBox.setGravity(Gravity.CENTER);
                        checkBox.setBackgroundColor(whiteColor);
                        checkBox.setLayoutParams(lin_params);
                        linearLayout.addView(checkBox);
                        playerView.setInGame(checkBox);
                    }else{//активность
                        EditText editText = new EditText(getActivity());
                        editText.setGravity(Gravity.CENTER);
                        editText.setBackgroundColor(whiteColor);
                        editText.setLayoutParams(params);
                        editText.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                        editText.setMaxLines(1);
                        switch (j){
                            case 1:
                                editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(2) });
                                if(player.getInGame() == 1 && player.getNumber() > 0){
                                    editText.setText(String.valueOf(player.getNumber()));
                                }
                                playerView.setNumber(editText);
                                break;
                            case 3:
                                editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(2) });
                                final Player finalPlayer = player;
                                editText.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View view, MotionEvent motionEvent) {
                                        openDialog(finalPlayer);
                                        return true;
                                    }
                                });
                                if(player.getInGame() == 1 && (player.getGoal() > 0 || player.getOwn_goal() > 0 ||
                                        player.getPenalty() > 0 || player.getPenalty_out() > 0)){
                                    editText.setText("*");
                                }
                                playerView.setGoal(editText);
                                break;
                            case 4:
                                editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(2) });
                                if(player.getInGame() == 1 && player.getAssist() > 0){
                                    editText.setText(String.valueOf(player.getAssist()));
                                }
                                playerView.setAssist(editText);
                                break;
                            case 5:
                                editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                                if(player.getInGame() == 1 && player.getYellowCard() > 0){
                                    editText.setText(String.valueOf(player.getYellowCard()));
                                }
                                playerView.setYellowCard(editText);
                                break;
                            case 6:
                                editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                                if(player.getInGame() == 1 && player.getRedCard() > 0){
                                    editText.setText(String.valueOf(player.getRedCard()));
                                }
                                playerView.setRedCard(editText);
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
            if(i > -1){
                player.setPlayerView(playerView);
            }
            table.addView(tableRow, i+1);
        }
        Log.i(TAG, "Список игроков команлы: " + teamName + "\n" + players.size() );
    }

    @Override
    public void update(int option) {
        Log.i(TAG, "Активность передала команду");
        if(option == 3){
            Log.i(TAG, "Вернуть данные протокола");
            returnData.sendDataToActivity(players);
        }else{
            Log.i(TAG, "Очистка протокола");
            createProtocolTable();
        }

    }
}
