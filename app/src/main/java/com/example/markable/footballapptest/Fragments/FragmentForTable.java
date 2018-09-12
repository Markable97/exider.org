package com.example.markable.footballapptest.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.markable.footballapptest.Classes.TournamentTable;
import com.example.markable.footballapptest.R;
import com.example.markable.footballapptest.UpdateFragListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class FragmentForTable extends Fragment implements UpdateFragListener{

    private static final String TAG = "FragTest";

    private String fromActivity = null;

    Bundle args = new Bundle();

    Gson gson = new Gson();

    private ArrayList<TournamentTable> newTournamentTable = new ArrayList<>();

    TextView textView;

    public static FragmentForTable newInstance(){
        Log.i(TAG, "NewInstance: ");
        FragmentForTable fragment = new FragmentForTable();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fromActivity = getArguments().getString("division","");
        Log.i(TAG, "OnCreate:");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_table, container, false);
        Log.i(TAG, "OnCreateView: переменная для JSON " + fromActivity);
        Log.i(TAG, "OnCreateView: Загрузка окна фрагмента ");

        textView = (TextView) view.findViewById(R.id.textView_test);

        if(fromActivity != null){
            updateTable(fromActivity);
        }else {
            textView.setText("Чисто проверить! ");
        }


        //textView.setText(fromActivity);
        /*if(savedInstanceState == null){
            fromActivity =  "[{\"divisionName\":\"Высший дивизион\",\"teamName\":\"Титан\",\"games\":2,\"point\":6,\"wins\":2,\"draws\":0,\"losses\":0}]";
        }

        newTournamentTable = gson.fromJson(fromActivity, new TypeToken<ArrayList<TournamentTable>>(){}.getType());
        String results = "";
        for(int i = 0; i < newTournamentTable.size(); i++){
            results += newTournamentTable.get(i).getDivisionName() + " " +
                    newTournamentTable.get(i).getTeamName() + " " + newTournamentTable.get(i).getGames()
                    + " " + newTournamentTable.get(i).getPoint() + " " + newTournamentTable.get(i).getWins()
                    + " " + newTournamentTable.get(i).getDraws() + " " + newTournamentTable.get(i).getLosses() + "\n";
        }
        textView.setText(results);*/


        //new ServerConnect().execute();

        return view;
    }

   /* public class ServerConnect extends AsyncTask<String, Void, String>{

        String query = "{\"id_division\":1,\"id_tour\":2}";
        String fromServer = null, fromServerResultsPrevMatches = null ;
        String ipAdres = "192.168.0.100";


        private String result = "";

        @Override
        protected String doInBackground(String... strings) {

            Log.i(TAG, "Поток запущен");
            Log.i(TAG, "Данные из активности = " + fromActivity);
            Socket socket;
            Gson gson = new Gson();
            try {
                socket = new Socket(ipAdres, 55555);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataInputStream inResultsPrev = new DataInputStream((socket.getInputStream()));
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                out.writeUTF(query);

                fromServer = in.readUTF();
                Log.i(TAG, "Данные с сервера в виду JSON = " + fromServer);
                fromServerResultsPrevMatches = inResultsPrev.readUTF();
                Log.i(TAG,"[2] Данные с сервера в виде JSON = " + fromServerResultsPrevMatches);
                out.close();
                in.close();
                inResultsPrev.close();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            textView.setText(result);
        }
    }*/

    @Override
    public void update(String divTable, String prevResults) {
        Log.i(TAG, "Interface: Сработал пустой метод");
        /*this.fromActivity = divTable;
        Log.i(TAG, "Interface: " + fromActivity);
        newTournamentTable.clear();
        newTournamentTable = gson.fromJson(fromActivity, new TypeToken<ArrayList<TournamentTable>>(){}.getType());
        String results = "";
        for(int i = 0; i < newTournamentTable.size(); i++){
            results += newTournamentTable.get(i).getDivisionName() + " " +
                    newTournamentTable.get(i).getTeamName() + " " + newTournamentTable.get(i).getGames()
                    + " " + newTournamentTable.get(i).getPoint() + " " + newTournamentTable.get(i).getWins()
                    + " " + newTournamentTable.get(i).getDraws() + " " + newTournamentTable.get(i).getLosses() + "\n";
        }
        textView.setText(results);*/
    }

    @Override
    public void updateTable(String table) {
        this.fromActivity = table;
        Log.i(TAG, "Interface длы Table: " + fromActivity);
        newTournamentTable.clear();
        newTournamentTable = gson.fromJson(fromActivity, new TypeToken<ArrayList<TournamentTable>>(){}.getType());
        String results = "";
        for(int i = 0; i < newTournamentTable.size(); i++){
            results += newTournamentTable.get(i).getDivisionName() + " " +
                    newTournamentTable.get(i).getTeamName() + " " + newTournamentTable.get(i).getGames()
                    + " " + newTournamentTable.get(i).getPoint() + " " + newTournamentTable.get(i).getWins()
                    + " " + newTournamentTable.get(i).getDraws() + " " + newTournamentTable.get(i).getLosses() + "\n";
        }
        Log.i(TAG,"Interface длы Table: results =" + results);
        textView.setText(results);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState: " + fromActivity);
        outState.putString("division",fromActivity);
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
    public void onDestroyView() {
        super.onDestroyView();

        args.putString("division", fromActivity);

        Log.i(TAG, "onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach");
    }


}
