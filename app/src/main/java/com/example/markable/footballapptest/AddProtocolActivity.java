package com.example.markable.footballapptest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.markable.footballapptest.Adapters.FragmentPageAdapterProtocol;
import com.example.markable.footballapptest.Classes.ConnectWithServer;
import com.example.markable.footballapptest.Classes.MessageToJson;
import com.example.markable.footballapptest.Classes.NextMatches;
import com.example.markable.footballapptest.Classes.Player;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AddProtocolActivity extends AppCompatActivity {

    private static final String TAG = AddProtocolActivity.class.getSimpleName();

    ConnectWithServer connect = new ConnectWithServer();
    Gson gson = new Gson();

    NextMatches matchProtocol;
    ArrayList<Player> playerHome = new ArrayList<>();
    ArrayList<Player> playerVisit = new ArrayList<>();

    TextView tv_division, tv_tour, tv_teamHome, tv_teamGuest;
    EditText ed_goalHome, ed_goalGuest;

    String[] teamNames = new String[2];

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_result);
        tv_division = (TextView)findViewById(R.id.protocol_division);
        tv_tour = (TextView)findViewById(R.id.protocol_tour);
        tv_teamHome = (TextView) findViewById(R.id.protocol_teamHome);
        tv_teamGuest = (TextView) findViewById(R.id.protocol_teamGuest);
        ed_goalHome = (EditText) findViewById(R.id.protocol_goalHome);
        ed_goalGuest = (EditText) findViewById(R.id.protocol_goalGuest);
        Bundle args = getIntent().getExtras();
        if(args != null){
            matchProtocol = (NextMatches) args.getSerializable("protocol");
            Log.i(TAG, "Матч для протокола: " + matchProtocol.toString());
            tv_division.setText(matchProtocol.getNameDivision());
            tv_tour.setText("Тур - " + matchProtocol.getIdTour());
            tv_teamHome.setText(matchProtocol.getTeamHome());
            tv_teamGuest.setText(matchProtocol.getTeamVisit());
            teamNames[0] = matchProtocol.getTeamHome();
            teamNames[1] = matchProtocol.getTeamVisit();

        }
        viewPager = (ViewPager) findViewById(R.id.protocol_viewPage);
        viewPager.setAdapter(new FragmentPageAdapterProtocol(getSupportFragmentManager(), AddProtocolActivity.this, teamNames));
        tabLayout = (TabLayout) findViewById(R.id.protocol_tabs);
        tabLayout.setupWithViewPager(viewPager);

        new MainServerConnect().execute(teamNames[0], teamNames[1]);
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
                    break;
                case "bad":
                    Log.i(TAG, "BAD");
                    break;
            }
        }
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
