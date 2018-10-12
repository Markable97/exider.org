package com.example.markable.footballapptest.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.markable.footballapptest.Adapters.FragmentPageAdapterForStatistic;
import com.example.markable.footballapptest.R;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class FragmentForTeamStatistic extends Fragment {


    private static final String TAG = "FragStatistic";

    ViewPager viewPager;
    TabLayout tabLayout;

    FragmentPageAdapterForStatistic mAdapter;

    String teamName;

    public static FragmentForTeamStatistic newInstance(String name){
        Log.i(TAG, "NewInstance: " + name);
        FragmentForTeamStatistic fragment = new FragmentForTeamStatistic();
        Bundle args = new Bundle();
        args.putString("nameTeam", name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String fromBun = getArguments().getString("nameTeam","хуй тебе а не данные");
        Log.i(TAG, "onCreate: " + fromBun);
        new ServerConnect().execute(fromBun);


    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_statistic, container, false);



        viewPager = view.findViewById(R.id.viewPager_team_statistic);
        tabLayout = view.findViewById(R.id.statistic_tabs);
        mAdapter = new FragmentPageAdapterForStatistic(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    public class ServerConnect extends AsyncTask<String, Void, String>{

        String queryClose = "{\"messageLogic\":\"close\"}";
        String query = "";
        final String IP = "192.168.0.103";
        String fromServer = null;

        @Override
        protected String doInBackground(String... strings) {
            Log.i(TAG, "doInBackground: поток запущен");
            for(String s : strings){
                query = "{\"messageLogic\":\"team\",\"id_team\":"+ s + "}";
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

                out.writeUTF(queryClose);
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
}
