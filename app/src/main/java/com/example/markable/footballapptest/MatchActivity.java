package com.example.markable.footballapptest;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.markable.footballapptest.Classes.ConnectWithServer;
import com.example.markable.footballapptest.Classes.ImageFromServer;
import com.example.markable.footballapptest.Classes.MessageToJson;
import com.example.markable.footballapptest.Classes.Player;
import com.example.markable.footballapptest.Classes.PrevMatches;
import com.example.markable.footballapptest.Classes.PublicConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class MatchActivity extends AppCompatActivity {

    //final String IP = "10.0.2.2";
    final String IP = PublicConstants.IP;

    private static final String TAG = "MatchAcrivity";

    ImageFromServer imageHome;
    ImageFromServer imageVisit;
    PrevMatches matches;

    LinearLayout layout_home;
    LinearLayout layout_guest;
    LinearLayout.LayoutParams textViewParams;
    TextView division;
    TextView tour;
    TextView count;
    TextView nameTeamHome;
    TextView nameTeamVisit;
    ImageView imageTeamHome;
    ImageView imageTeamVisit;

    private ArrayList<Player> playersInMatch = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_content);
        Bundle args = getIntent().getExtras();
        if(args!=null){
            matches = (PrevMatches) args.getSerializable("information");
            imageHome = args.getParcelable("imageHome");
            imageVisit = args.getParcelable("imageVisit");
            Log.i(TAG, "onCreate: " + matches.toString() + imageVisit.getNameImage() + imageHome.getNameImage());
        }

        layout_home = findViewById(R.id.match_layoutHome);
        layout_guest = findViewById(R.id.match_layoutGuest);
        division = findViewById(R.id.match_nameDivision);
        division.setText(matches.getNameDivision());
        tour = findViewById(R.id.match_tour);
        tour.setText("Тур " + String.valueOf(matches.getIdTour()));
        count = findViewById(R.id.match_count);
        count.setText(String.valueOf(matches.getGoalHome()) + " : " + String.valueOf(matches.getGoalVisit()));
        nameTeamHome = findViewById(R.id.match_teamHome);
        nameTeamHome.setText(matches.getTeamHome());
        nameTeamVisit = findViewById(R.id.match_teamGuest);
        nameTeamVisit.setText(matches.getTeamVisit());
        imageTeamHome = findViewById(R.id.match_imageHome);
        imageTeamHome.setImageBitmap(imageHome.getBitmapImageBig());
        imageTeamVisit = findViewById(R.id.match_imageGuest);
        imageTeamVisit.setImageBitmap(imageVisit.getBitmapImageBig());

        textViewParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        textViewParams.gravity = Gravity.CENTER;
        Log.i(TAG, "onCreate: id_match" + matches.getIdMatch());

        new MainServerConnect().execute(matches.getIdMatch());
    }

    public class MainServerConnect extends AsyncTask<Integer, Void, String> {

        ConnectWithServer connect = new ConnectWithServer();
        Gson gson = new Gson();
        int idMatch;
        String  fromServer;

        @Override
        protected String doInBackground(Integer... integers) {
            Log.i(TAG, "doInBackground: начало потока!!!!!!!!!!!!!!!!!!!!!");
            idMatch = integers[0];
            Log.i(TAG, "Матч id = " + idMatch);
            MessageToJson message = new MessageToJson("player", idMatch);
            try{
                connect.openConnection();
                fromServer = connect.responseFromServer(gson.toJson(message));
                Log.i(TAG, "Данные от сервера \n" + fromServer);
                playersInMatch.clear();
                playersInMatch = gson.fromJson(fromServer, new TypeToken<ArrayList<Player>>(){}.getType());
                Log.i(TAG, "Массив = " + playersInMatch.size());
                connect.closeConnection();
                return "success";
            }catch (Exception e) {
                Log.i(TAG, "ERROR \n" + e.getMessage());
                connect.closeConnection();
                connect = null;
                return "bad"; //если какакя-то ошибка возвращаем плохо
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("success")){
                addLayout(playersInMatch);
            }else{
                Toast.makeText(getApplicationContext(),"Не удалось получить данные", Toast.LENGTH_LONG).show();
            }
        }
    }

    void addLayout(ArrayList<Player> players){
        Context context = MatchActivity.this;
        String assistHome = "";
        String assistGuest = "";
        for(Player p : players){
            if(p.getPlayerTeam().equals(nameTeamHome.getText())){

                if(p.getGoal() > 0){//если есть голы у игрока
                    TextView textView = new TextView(context);
                    textView.setGravity(11); //выравнивание по центру
                    textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.footbal_icon,0);
                    textView.setText(p.getPlayerName() + "(" + p.getGoal() + ")");
                    textView.setLayoutParams(textViewParams);
                    layout_home.addView(textView);
                    /*int cnt = p.getGoal();
                    for(int i = 1; i <= cnt; i++){
                        TextView textView = new TextView(context);
                        textView.setText(p.getPlayerName());
                        textView.setGravity(11); //выравнивание по центру
                        textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.footbal_icon,0);
                        layout_home.addView(textView);
                    }*/
                }
                if(p.getYellowCard()>0){
                    TextView textView = new TextView(context);
                    textView.setText(p.getPlayerName());
                    textView.setGravity(11); //выравнивание по центру
                    textView.setLayoutParams(textViewParams);
                    //layout_home.addView(textView);
                    if(p.getYellowCard() == 1){//Желтые карточки
                        textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.yellow_card,0);
                        layout_home.addView(textView);
                    }else{
                        textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.red_yellow_card,0);
                        layout_home.addView(textView);
                    }
                }
                if(p.getRedCard() > 0){
                    TextView textView = new TextView(context);
                    textView.setText(p.getPlayerName());
                    textView.setGravity(11); //выравнивание по центру
                    textView.setLayoutParams(textViewParams);
                    textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.red_card,0);
                    layout_home.addView(textView);
                }
                if(p.getAssist() > 0){
                    assistHome+=p.getPlayerName()+'('+p.getAssist()+')' + ", ";
                }
                if(p.getPenalty() > 0){
                    TextView textView = new TextView(context);
                    textView.setText(p.getPlayerName() + "(" + p.getPenalty() +")" + "(пен.)");
                    textView.setGravity(11); //выравнивание по центру
                    textView.setLayoutParams(textViewParams);
                    textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.penalty,0);
                    layout_home.addView(textView);
                }
                if(p.getPenalty_out() > 0){
                    //CharSequence text = Html.fromHtml("<s>" + p.getPlayerName() + "(" + p.getPenalty_out() +")" + "</s>");
                    TextView textView = new TextView(context);
                    textView.setText(p.getPlayerName() + "(" + p.getPenalty_out() +")(пен.)");
                    textView.setGravity(11); //выравнивание по центру
                    textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.penalty_out,0);
                    textView.setLayoutParams(textViewParams);
                    layout_home.addView(textView);
                }
                if(p.getOwn_goal() > 0){
                    TextView textView = new TextView(context);
                    textView.setText(p.getPlayerName() + "(" + p.getOwn_goal() +")(авт.)");
                    textView.setGravity(11); //выравнивание по центру
                    textView.setLayoutParams(textViewParams);
                    textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.own_goal,0);
                    layout_home.addView(textView);
                }
            }else{//конец домашний
                if(p.getGoal() > 0){//если есть голы у игрока
                    TextView textView = new TextView(context);
                    textView.setGravity(11); //выравнивание по центру
                    textView.setLayoutParams(textViewParams);
                    textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.footbal_icon,0);
                    textView.setText(p.getPlayerName() + "(" + p.getGoal() + ")");
                    layout_guest.addView(textView);
                }
                if(p.getYellowCard()>0){
                    TextView textView = new TextView(context);
                    textView.setText(p.getPlayerName() + "(" + p.getYellowCard() + ")");
                    textView.setGravity(11); //выравнивание по центру
                    textView.setLayoutParams(textViewParams);
                    if(p.getYellowCard() == 1){//Желтые карточки
                        textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.yellow_card,0);
                        layout_guest.addView(textView);
                    }else{
                        textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.red_yellow_card,0);
                        layout_guest.addView(textView);
                    }
                }
                if(p.getRedCard() > 0){
                    TextView textView = new TextView(context);
                    textView.setText(p.getPlayerName());
                    textView.setLayoutParams(textViewParams);
                    textView.setGravity(11); //выравнивание по центру
                    textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.red_card,0);
                    layout_guest.addView(textView);
                }
                if(p.getAssist() > 0){
                    assistGuest+=p.getPlayerName()+'('+p.getAssist()+')' + ", ";
                }
                if(p.getPenalty() > 0){
                    TextView textView = new TextView(context);
                    textView.setLayoutParams(textViewParams);
                    textView.setText(p.getPlayerName() + "(" + p.getPenalty() +")" + "(пен.)");
                    textView.setGravity(11); //выравнивание по центру
                    textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.penalty,0);
                    layout_guest.addView(textView);
                }
                if(p.getPenalty_out() > 0){
                    //CharSequence text = Html.fromHtml("<s>" + p.getPlayerName() + "(" + p.getPenalty_out() +")" + "</s>");
                    TextView textView = new TextView(context);
                    textView.setLayoutParams(textViewParams);
                    textView.setText(p.getPlayerName() + "(" + p.getPenalty_out() +")(пен.)");
                    textView.setGravity(11); //выравнивание по центру
                    textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.penalty_out,0);
                    layout_guest.addView(textView);
                }
                if(p.getOwn_goal() > 0){
                    TextView textView = new TextView(context);
                    textView.setLayoutParams(textViewParams);
                    textView.setText(p.getPlayerName() + "(" + p.getOwn_goal() +")(авт.)");
                    textView.setGravity(11); //выравнивание по центру
                    textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.own_goal,0);
                    layout_guest.addView(textView);
                }
            }//конец гостевой команды
        }//Конец перебора игроков
        if(assistHome.length() > 0){
            TextView textView = new TextView(context);
            textView.setLayoutParams(textViewParams);
            textView.setText("Ассистенты: " + assistHome);
            layout_home.addView(textView);
        }
        if(assistGuest.length() > 0){
            TextView textView = new TextView(context);
            textView.setLayoutParams(textViewParams);
            textView.setText("Ассистенты: " + assistGuest);
            layout_guest.addView(textView);
        }
    }//конец метода

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
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy" );
    }
}
