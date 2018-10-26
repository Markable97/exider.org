package com.example.markable.footballapptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.markable.footballapptest.Classes.ImageFromServer;
import com.example.markable.footballapptest.Classes.PrevMatches;

public class MatchActivity extends AppCompatActivity {

    private static final String TAG = "MatchAcrivity";

    ImageFromServer imageHome;
    ImageFromServer imageVisit;
    PrevMatches matches;

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
