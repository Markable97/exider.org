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

import com.example.markable.footballapptest.R;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class FragmentForTable extends Fragment {

    private static final String TAG = "FragTest";

    private String fromActivity = null;

    TextView textView;

    public static FragmentForTable newInstance(String data){
        FragmentForTable fragment = new FragmentForTable();
        Bundle args = new Bundle();
        args.putString("division", data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fromActivity = getArguments().getString("division","");
        Log.i(TAG, "OnCreate: Получение строки из Bundle");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_table, container, false);
        textView = (TextView) view.findViewById(R.id.textView_test);
        textView.setText("Чисто проверить! " + fromActivity );
        Log.i(TAG, "OnCreateView: Загрузка окна фрагмента " + fromActivity);
        new ServerConnect().execute();

        return view;
    }

    public class ServerConnect extends AsyncTask<String, Void, String>{

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
               /* Type t = new TypeToken<ArrayList<Team>>(){}.getType();
                newTeams = gson.fromJson(fromServer, t);

                for(int i = 0; i < newTeams.size(); i++){

                    result += newTeams.get(i).getId() + " " + newTeams.get(i).getName() + " " + newTeams.get(i).getDate() + " " +
                            newTeams.get(i).getIdDivision() + "\n";

                    //Log.i(TAG, newTeams.get(i).getId() + " " + newTeams.get(i).getName() + " " + newTeams.get(i).getDate() + " " +
                      //      newTeams.get(i).getIdDivision());
                }
                Log.i(TAG, result);*/
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
    }
}
