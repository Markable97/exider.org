package com.example.markable.footballapptest;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.markable.footballapptest.Classes.ImageFromServer;
import com.example.markable.footballapptest.Fragments.FragmentForTeamMatches;
import com.example.markable.footballapptest.Fragments.FragmentForTeamStatistic;

public class TeamActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private ImageFromServer image;

    TextView nameTeam;
    ImageView imageTeam;
    RadioButton rb_statistic, rb_matches;
    RadioGroup radioGroup;
    FrameLayout container;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_content);

        Bundle args = getIntent().getExtras();
        if(args!=null){
            image = args.getParcelable("dateForActivity");
        }

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

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container_frag_team, new FragmentForTeamStatistic() ).commit();

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_statisticPlayers:
                FragmentForTeamStatistic fragStatistic = new FragmentForTeamStatistic();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_frag_team, fragStatistic);
                fragmentTransaction.commit();
                break;
            case R.id.rb_allMatches:
                FragmentForTeamMatches fragMatches = new FragmentForTeamMatches();
                FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.container_frag_team, fragMatches).commit();
                break;
        }
    }
}
