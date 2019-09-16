package com.example.markable.footballapptest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

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
    private String nameTeamFromActivity;

    Toolbar toolbar;

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
        toolbar  = (Toolbar) findViewById(R.id.team_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackPressed();// возврат на предыдущий activity
            }
        });
        Bundle args = getIntent().getExtras();
        nameTeam = findViewById(R.id.teamAcrivity_tv_teamName);

        imageTeam = findViewById(R.id.teamActivity_im_image);
        if(args!=null){
            image = args.getParcelable("dateForActivity");
            nameTeamFromActivity = args.getString("dataForActivityName");
            if (image!=null){
                imageTeam.setImageBitmap(image.getBitmapImageBig());
            }
            nameTeam.setText(nameTeamFromActivity);
        }
        container = findViewById(R.id.container_frag_team);
        rb_statistic = findViewById(R.id.rb_statisticPlayers);
        rb_statistic.setChecked(true);
        rb_matches = findViewById(R.id.rb_allMatches);
        radioGroup = findViewById(R.id.radioGroup_team);
        radioGroup.setOnCheckedChangeListener(this);
        rb_statistic.setEnabled(false);
        rb_matches.setEnabled(false);

        new ServerConnect(TeamActivity.this).execute(nameTeamFromActivity);





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

        TeamActivity activity;
        private Context context;
        ProgressDialog progressDialog ;
        public ServerConnect(TeamActivity activity){
            this.activity = activity;
            context = activity;
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Загрузка");
            progressDialog.show();
        }

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
                if(arrayTeamImage!=null){
                    for(PrevMatches match : arrayAllMatches){
                        for(ImageFromServer image : arrayTeamImage){
                            if(image.getNameImage().equals(match.getTeamHome())){
                                match.setImageHome(image);
                            }else if (image.getNameImage().equals(match.getTeamVisit())){
                                match.setImageVisit(image);
                            }
                        }
                    }
                }
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
                rb_statistic.setEnabled(true);
                rb_matches.setEnabled(true);
            }else{
                Toast.makeText(getApplicationContext(),"Не удалось получить данные", Toast.LENGTH_LONG).show();
            }
            progressDialog.dismiss();
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
