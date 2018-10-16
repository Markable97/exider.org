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

        fragMatches = new FragmentForTeamMatches().newInstance();
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

        @Override
        protected String doInBackground(String... strings) {

            

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    @Override
    public void sendDataToActivity(ArrayList<Player> arrayPlayers) {
        this.arrayPlayers = arrayPlayers;
    }
}
