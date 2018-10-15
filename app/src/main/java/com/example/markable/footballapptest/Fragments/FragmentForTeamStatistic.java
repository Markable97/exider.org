package com.example.markable.footballapptest.Fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.markable.footballapptest.Adapters.FragmentPageAdapterForStatistic;
import com.example.markable.footballapptest.Classes.Player;
import com.example.markable.footballapptest.R;
import com.example.markable.footballapptest.ReturnFromFragForAct;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;

public class FragmentForTeamStatistic extends Fragment {


    private static final String TAG = "FragStatistic";

    ReturnFromFragForAct fromFragForAct;

    ViewPager viewPager;
    TabLayout tabLayout;

    boolean flag = false;

    FragmentPageAdapterForStatistic mAdapter;

    ArrayList<Player> arrayPlayers = new ArrayList<>();
    String teamName;

    public static FragmentForTeamStatistic newInstance(String name){
        Log.i(TAG, "NewInstance: " + name);
        FragmentForTeamStatistic fragment = new FragmentForTeamStatistic();
        Bundle args = new Bundle();
        args.putString("nameTeam", name);
        fragment.setArguments(args);
        return fragment;
    }

    public static FragmentForTeamStatistic newInstance(ArrayList<Player> player){
        Log.i(TAG, "NewInstance2: " + player.toString());
        FragmentForTeamStatistic fragment = new FragmentForTeamStatistic();
        Bundle args = new Bundle();
        args.putSerializable("nameTeamArray", player);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ReturnFromFragForAct){
            fromFragForAct = (ReturnFromFragForAct) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String fromBun = getArguments().getString("nameTeam","хуй тебе а не данные");
        Log.i(TAG, "onCreate: " + fromBun);
        //Log.i(TAG, "onCreate: " + arrayPlayers.size());
        if(getArguments().getSerializable("nameTeamArray" ) == null){
            Log.i(TAG, "onCreate: длина массива равна 0");
            new ServerConnect().execute(fromBun);
        }else {
            arrayPlayers = (ArrayList<Player>) getArguments().getSerializable("nameTeamArray");
            Log.i(TAG, "onCreate: Длина массива = " + arrayPlayers.size() );
            flag = true;
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_statistic, container, false);

        Log.i(TAG, "onCreateView: ");

        viewPager = view.findViewById(R.id.viewPager_team_statistic);
        tabLayout = view.findViewById(R.id.statistic_tabs);
        if(flag){
            fromFragForAct.sendDataToActivity(arrayPlayers);
            mAdapter = new FragmentPageAdapterForStatistic(getChildFragmentManager(), arrayPlayers);
            viewPager.setAdapter(mAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }

        return view;
    }

    public class ServerConnect extends AsyncTask<String, Void, String>{

        String queryClose = "{\"messageLogic\":\"close\"}";
        String query = "";
        final String IP = "192.168.0.105";
        String fromServer = null;

        @Override
        protected String doInBackground(String... strings) {
            Log.i(TAG, "doInBackground: поток запущен");
            for(String s : strings){
                query = "{\"messageLogic\":\"team\",\"id_team\":\""+ s + "\"}";
                //query = "{\"id_division\":" + s + ",\"id_tour\":2}";
            }
            Socket socket;
            Gson gson = new Gson();

            try {
                socket = new Socket(IP, 55555);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                //DataInputStream inResultsPrev = new DataInputStream((socket.getInputStream()));
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                out.writeUTF(query);

                fromServer = in.readUTF();
                Log.i(TAG, "doInBackground: Данные от сервера" + fromServer);
                Type t = new TypeToken<ArrayList<Player>>(){}.getType();
                arrayPlayers = gson.fromJson(fromServer, t);
                Log.i(TAG, "doInBackground: \n" + arrayPlayers.toString());

                int countImage = in.readInt();
                Log.i(TAG, "doInBackground: кол-во фоток от сервера = " + countImage);
                byte[] byteArray;
                for(int i = 0; i < countImage; i++){
                    int countBytes = in.readInt();
                    byteArray = new byte[countBytes];
                    in.readFully(byteArray);
                    arrayPlayers.get(i).setPlayerImage(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
                }

                out.writeUTF(queryClose);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            fromFragForAct.sendDataToActivity(arrayPlayers);
            mAdapter = new FragmentPageAdapterForStatistic(getChildFragmentManager(), arrayPlayers);
            viewPager.setAdapter(mAdapter);
            tabLayout.setupWithViewPager(viewPager);
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
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach");
    }

}
