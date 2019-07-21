package com.example.markable.footballapptest;

import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.markable.footballapptest.Classes.ConnectWithServer;
import com.example.markable.footballapptest.Classes.ImageFromServer;
import com.example.markable.footballapptest.Classes.MessageToJson;
import com.example.markable.footballapptest.Classes.Player;
import com.example.markable.footballapptest.Classes.PrevMatches;
import com.example.markable.footballapptest.Classes.PublicConstants;
import com.example.markable.footballapptest.Fragments.FragmentForTeamMatches;
import com.example.markable.footballapptest.Fragments.FragmentForTeamStatistic;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TeamActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ReturnFromFragForAct {

    final String IP = PublicConstants.IP;

    private static final String TAG = "TeamAcrivity";

    private ImageFromServer image;


    TextView nameTeam;
    ImageView imageTeam;
    RadioButton rb_statistic, rb_matches;
    RadioGroup radioGroup;
    FrameLayout container;

    ArrayList<Player> arrayPlayers = new ArrayList<>();
    ArrayList<PrevMatches> arrayAllMatches = new ArrayList<>();
    ArrayList<ImageFromServer> arrayTeamImage = new ArrayList<>();
    private ArrayList<ImageFromServer> arrayPlayerImage = new ArrayList<>();

    FragmentForTeamStatistic fragStatistic;
    FragmentForTeamMatches fragMatches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_content);
        Bundle args = getIntent().getExtras();
        if(args!=null){
            image = args.getParcelable("dateForActivity");
        }

        new ServerConnect().execute(image.getNameImage());

        nameTeam = findViewById(R.id.teamAcrivity_tv_teamName);
        nameTeam.setText(image.getNameImage());

        imageTeam = findViewById(R.id.teamActivity_im_image);
        imageTeam.setImageBitmap(image.getBitmapImageBig());

        rb_statistic = findViewById(R.id.rb_statisticPlayers);
        rb_statistic.setChecked(true);
        //rb_matches = findViewById(R.id.rb_allMatches);
        radioGroup = findViewById(R.id.radioGroup_team);
        radioGroup.setOnCheckedChangeListener(this);

        container = findViewById(R.id.container_frag_team);

        /*fragStatistic = new FragmentForTeamStatistic().newInstance();
        fragMatches = new FragmentForTeamMatches();


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frag_team, fragStatistic ).commit();*/

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_statisticPlayers:
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_frag_team, fragStatistic ).commit();
                break;
            case R.id.rb_allMatches:
                FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.container_frag_team, fragMatches ).commit();
                break;
        }
    }

    public class ServerConnect extends AsyncTask<String, Void, String >{

        ConnectWithServer connect = new ConnectWithServer();
        Gson gson = new Gson();
        String queryClose = "{\"messageLogic\":\"close\"}";
        String query = "";
        String teamName;
        //final String IP = "10.0.2.2";

        String fromServer = null;
        int countIm = 0;

        @Override
        protected String doInBackground(String... strings) {
            Log.i(TAG, "doInBackground: начало потока!!!!!!!!!!!!!!!!!!!!!");
            teamName = strings[0];
            Log.i(TAG, "Название команды = " + teamName);
            MessageToJson message = new MessageToJson("team",teamName);
            try {
                connect.openConnection(); //открывваем соединение
                fromServer = connect.responseFromServer(gson.toJson(message));//получаем список игроков
                Log.i(TAG, "Данные от сервера состав\n" + fromServer);
                Type t1 = new TypeToken<ArrayList<Player>>(){}.getType();
                arrayPlayers = gson.fromJson(fromServer, t1);
                arrayPlayerImage = connect.fileFromServer();
                if(arrayPlayerImage == null){
                    Log.i(TAG, "Нет фотографий игроков");
                }
                //connect.closeConnection();
                //Теперь загрузка матчей
                //connect.openConnection();
                message.messageLogic = "matches";
                fromServer = connect.responseFromServer(gson.toJson(message));
                Log.i(TAG, "Данные от сервера все матчи \n" + fromServer);
                Type t = new TypeToken<ArrayList<PrevMatches>>(){}.getType();
                arrayAllMatches = gson.fromJson(fromServer, t);
                Log.i(TAG, "doInBackground: all matches = " + arrayAllMatches.toString());
                arrayTeamImage = connect.fileFromServer();
                connect.closeConnection();
                return "success"; //все хорошо
            }catch (Exception e){
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
                fragStatistic = new FragmentForTeamStatistic().newInstance();
                fragMatches = new FragmentForTeamMatches().newInstance();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_frag_team, fragStatistic ).commit();
            }else{
                Toast.makeText(getApplicationContext(),"Не удалось получить данные", Toast.LENGTH_LONG).show();
            }
        }
    }

    public ArrayList<Player> getArrayPlayers() {
        return arrayPlayers;
    }

    public ArrayList<PrevMatches> getArrayAllMatches() {
        return arrayAllMatches;
    }

    public ArrayList<ImageFromServer> getArrayTeamImage() {
        return arrayTeamImage;
    }

    @Override
    public void sendDataToActivity(ArrayList<Player> arrayPlayers) {
        this.arrayPlayers = arrayPlayers;
    }
}
