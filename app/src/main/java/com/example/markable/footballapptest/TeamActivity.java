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

import com.example.markable.footballapptest.Classes.AllMatchesForTeam;
import com.example.markable.footballapptest.Classes.ImageFromServer;
import com.example.markable.footballapptest.Classes.Player;
import com.example.markable.footballapptest.Fragments.FragmentForTeamMatches;
import com.example.markable.footballapptest.Fragments.FragmentForTeamStatistic;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;

public class TeamActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ReturnFromFragForAct {

    private static final String TAG = "TeamAcrivity";

    private ImageFromServer image;

    TextView nameTeam;
    ImageView imageTeam;
    RadioButton rb_statistic, rb_matches;
    RadioGroup radioGroup;
    FrameLayout container;

    ArrayList<Player> arrayPlayers = new ArrayList<>();
    ArrayList<AllMatchesForTeam> arrayAllMatches = new ArrayList<>();

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

        fragStatistic = new FragmentForTeamStatistic().newInstance(image.getNameImage());
        fragMatches = new FragmentForTeamMatches();


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_frag_team, fragStatistic ).commit();

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_statisticPlayers:
                Log.i(TAG, "onCheckedChanged: Длина массива" + arrayPlayers.size());
                if(arrayPlayers.size()!=0){
                    fragStatistic = new FragmentForTeamStatistic().newInstance(arrayPlayers);
                }
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_frag_team, fragStatistic);
                fragmentTransaction.commit();
                break;
            case R.id.rb_allMatches:

                FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.container_frag_team, fragMatches).commit();
                break;
        }
    }

    public class ServerConnect extends AsyncTask<String, Void, String >{

        String queryClose = "{\"messageLogic\":\"close\"}";
        String query = "";
        final String IP = "192.168.0.105";
        String fromServer = null;

        @Override
        protected String doInBackground(String... strings) {
            Log.i(TAG, "doInBackground: начало потока!!!!!!!!!!!!!!!!!!!!!");
            for(String s : strings){
                query = "{\"messageLogic\":\"matches\",\"id_team\":\""+ s + "\"}";
            }

            Socket socket;
            Gson gson = new Gson();

            try {
                socket = new Socket(IP, 55555);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                out.writeUTF(query);

                fromServer = in.readUTF();
                Log.i(TAG, "doInBackground: from server = " + fromServer);
                Type t = new TypeToken<ArrayList<AllMatchesForTeam>>(){}.getType();
                arrayAllMatches = gson.fromJson(fromServer, t);
                Log.i(TAG, "doInBackground: all matches = " + arrayAllMatches.toString());

                out.writeUTF(queryClose);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            fragMatches = new FragmentForTeamMatches().newInstance(arrayAllMatches);
        }
    }

    @Override
    public void sendDataToActivity(ArrayList<Player> arrayPlayers) {
        this.arrayPlayers = arrayPlayers;
    }
}
