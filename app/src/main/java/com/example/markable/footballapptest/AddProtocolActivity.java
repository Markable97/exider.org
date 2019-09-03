package com.example.markable.footballapptest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.markable.footballapptest.Adapters.FragmentPageAdapterProtocol;
import com.example.markable.footballapptest.Classes.ConnectWithServer;
import com.example.markable.footballapptest.Classes.MessageToJson;
import com.example.markable.footballapptest.Classes.NextMatches;
import com.example.markable.footballapptest.Classes.Player;
import com.example.markable.footballapptest.Classes.PublicConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AddProtocolActivity extends AppCompatActivity implements ReturnFromFragForAct{

    private static final String TAG = AddProtocolActivity.class.getSimpleName();

    ConnectWithServer connect = new ConnectWithServer();
    Gson gson = new Gson();

    NextMatches matchProtocol;
    FragmentPageAdapterProtocol mAdapter;
    ArrayList<Player> playerHome = new ArrayList<>();
    ArrayList<Player> playerVisit = new ArrayList<>();
    ArrayList<Player> playerssDB = new ArrayList<>();

    TextView tv_division, tv_tour, tv_teamHome, tv_teamGuest;
    EditText ed_goalHome, ed_goalGuest;

    String[] teamNames = new String[2];

    ViewPager viewPager;
    TabLayout tabLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_result);
        toolbar =  (Toolbar) findViewById(R.id.protocol_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackPressed();// возврат на предыдущий activity
            }
        });
        tv_division = (TextView)findViewById(R.id.protocol_division);
        //tv_tour = (TextView)findViewById(R.id.protocol_tour);
        tv_teamHome = (TextView) findViewById(R.id.protocol_teamHome);
        tv_teamGuest = (TextView) findViewById(R.id.protocol_teamGuest);
        ed_goalHome = (EditText) findViewById(R.id.protocol_goalHome);
        ed_goalGuest = (EditText) findViewById(R.id.protocol_goalGuest);
        Bundle args = getIntent().getExtras();
        if(args != null){
            matchProtocol = (NextMatches) args.getSerializable("protocol");
            Log.i(TAG, "Матч для протокола: " + matchProtocol.toString());
            tv_division.setText(matchProtocol.getNameDivision() + ": тур - " + matchProtocol.getIdTour());
            //tv_tour.setText("Тур - " + matchProtocol.getIdTour());
            tv_teamHome.setText(matchProtocol.getTeamHome());
            tv_teamGuest.setText(matchProtocol.getTeamVisit());
            teamNames[0] = matchProtocol.getTeamHome();
            teamNames[1] = matchProtocol.getTeamVisit();

        }
        viewPager = (ViewPager) findViewById(R.id.protocol_viewPage);
        tabLayout = (TabLayout) findViewById(R.id.protocol_tabs);


        new MainServerConnect().execute(teamNames[0], teamNames[1]);
    }

    public ArrayList<Player> getPlayerHome(){
        return playerHome;
    }

    public ArrayList<Player> getPlayerVisit(){
        return playerVisit;
    }
    private class MainServerConnect extends AsyncTask<String, Void, String> {
        ArrayList<String> fromServer;
        @Override
        protected String doInBackground(String... teams) {
            Log.i(TAG, "doInBackground: начало потока!!!!!!!!!!!!!!!!!!!!!");
            Log.i(TAG,"Командлы  = " + teams[0] + " " + teams[1]);
            MessageToJson message = new MessageToJson("getPlayersProtocol", teams[0]+";"+teams[1]);
            try{
                connect.openConnection();
                fromServer = connect.responseFromServerArray(gson.toJson(message), 2);
                playerHome.clear();
                playerHome = gson.fromJson(fromServer.get(0), new TypeToken<ArrayList<Player>>(){}.getType());
                if(!playerHome.isEmpty()){
                    Log.i(TAG, "Команда хохзяев \n" + playerHome.toString());
                }
                playerVisit.clear();
                playerVisit = gson.fromJson(fromServer.get(1), new TypeToken<ArrayList<Player>>(){}.getType());
                if(!playerHome.isEmpty()){
                    Log.i(TAG, "Команда гостей \n" + playerVisit.toString());
                }
                connect.closeConnection();
                return "success";
            }catch (Exception e){
                return  "bad";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            switch (s){
                case "success":
                    Log.i(TAG, "SUCCESS");
                    mAdapter = new FragmentPageAdapterProtocol(getSupportFragmentManager(), AddProtocolActivity.this, teamNames);
                    viewPager.setAdapter(mAdapter);
                    tabLayout.setupWithViewPager(viewPager);
                    break;
                case "bad":
                    Toast.makeText(getApplicationContext(), "Ошщибка соединния", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_protocol, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.menu_protocol_sent){
            mAdapter.update(PublicConstants.OPTION_SENT);
            Toast.makeText(getApplicationContext(), "Данные отправлены", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }else{
            mAdapter.update(PublicConstants.OPTION_CLEAR);
            Toast.makeText(getApplicationContext(), "Протокол очищен", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    @Override
    public void sendDataToActivity(ArrayList<Player> arrayPlayers) {
        Log.i(TAG, "Данные из фрагмента: " + arrayPlayers.size());
        for(Player p : arrayPlayers){
            if(p.getPlayerView().getInGame() == 1){
                playerssDB.add(new Player(p.getIdPlayer(),p.getPlayerTeam(), p.getPlayerName(), p.getAmplua(), p.getBirhtday(),
                        p.getPlayerView().getNumber(), p.getPlayerView().getInGame(),p.getPlayerView().getGoal(), p.getPlayerView().getAssist(),
                        p.getPlayerView().getYellowCard(), p.getPlayerView().getRedCard()));
            }
        }
        Log.i(TAG, "Игроки в протоколе для БАЗЫ: \n" + playerssDB.toString());
        //отправка в базу данных
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
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy" );
    }

}
