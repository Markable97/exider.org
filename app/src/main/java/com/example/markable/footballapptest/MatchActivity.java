package com.example.markable.footballapptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markable.footballapptest.Classes.ImageFromServer;
import com.example.markable.footballapptest.Classes.PrevMatches;

public class MatchActivity extends AppCompatActivity {

    private static final String TAG = "MatchAcrivity";

    ImageFromServer imageHome;
    ImageFromServer imageVisit;
    PrevMatches matches;

    TextView division;
    TextView tour;
    TextView count;
    TextView nameTeamHome;
    TextView nameTeamVisit;
    ImageView imageTeamHome;
    ImageView imageTeamVisit;


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
