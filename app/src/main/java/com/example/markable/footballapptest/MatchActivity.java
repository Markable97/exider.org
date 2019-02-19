package com.example.markable.footballapptest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markable.footballapptest.Classes.ImageFromServer;
import com.example.markable.footballapptest.Classes.Player;
import com.example.markable.footballapptest.Classes.PrevMatches;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class MatchActivity extends AppCompatActivity {

    //final String IP = "10.0.2.2";
    final String IP = "192.168.0.105";

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

    private ArrayList<Player> playersInMatch = new ArrayList<>();

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

        Log.i(TAG, "onCreate: id_match" + matches.getIdMatch());

        new MainServerConnect().execute(String.valueOf(matches.getIdMatch()));
    }

    public class MainServerConnect extends AsyncTask<String, Void, String> {

        String  query = "",
                fromServer;

        @Override
        protected String doInBackground(String... strings) {

            for(String s : strings){
                query = "{\"messageLogic\":\"player\",\"id_division\":"+ s + "}";
                //query = "{\"id_division\":" + s + ",\"id_tour\":2}";
            }

            Socket socket;
            Gson gson = new Gson();

            try {
                socket = new Socket(IP, 55555);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                out.writeUTF(query);
                fromServer = in.readUTF();
                Log.i(TAG, "Данные с сервера в виду JSON = " + fromServer);
                playersInMatch.clear();
                playersInMatch = gson.fromJson(fromServer, new TypeToken<ArrayList<Player>>(){}.getType());
                Log.i(TAG, "Массив = " + playersInMatch.get(2));

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
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
