package com.example.markable.footballapptest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.markable.footballapptest.Adapters.FragmentPageAdapterProtocol;
import com.example.markable.footballapptest.Classes.NextMatches;

import org.w3c.dom.Text;

public class AddProtocolActivity extends AppCompatActivity {

    private static final String TAG = AddProtocolActivity.class.getSimpleName();

    NextMatches matchProtocol;
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
