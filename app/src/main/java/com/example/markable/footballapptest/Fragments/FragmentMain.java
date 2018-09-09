package com.example.markable.footballapptest.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.markable.footballapptest.Adapters.SampleFragmentPageAdapter;
import com.example.markable.footballapptest.R;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class FragmentMain extends Fragment {

    private static final String TAG = "FragMain";

    private String prevResults = "", table = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public class ServerConnectTest extends AsyncTask<String, Void, String>{

        String query = "{\"id_division\":1,\"id_tour\":2}";
        String fromServer = null, fromServerResultsPrevMatches = null ;
        String ipAdres = "192.168.0.100";


        @Override
        protected String doInBackground(String... strings) {

            Log.i(TAG, "Поток запущен");
            Socket socket;
            Gson gson = new Gson();
            try {
                socket = new Socket(ipAdres, 55555);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataInputStream inResultsPrev = new DataInputStream((socket.getInputStream()));
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                out.writeUTF(query);

                fromServer = in.readUTF();
                table = fromServer;
                Log.i(TAG, "Данные с сервера в виду JSON = " + fromServer);
                fromServerResultsPrevMatches = inResultsPrev.readUTF();
                prevResults = fromServerResultsPrevMatches;
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Log.i(TAG, "OnCreateView: Загрузка главного фрагмента");

        new ServerConnectTest().execute();
        int i = 0;
        while (table.equals("")){
            i++;
        }
        Log.i(TAG, String.valueOf(i));
        if (!table.equals("")){
            Log.i(TAG, "Зашел в if. Поля основного класса = " + table + prevResults);
            ViewPager viewPager = view.findViewById(R.id.viewPager);
            viewPager.setAdapter(new SampleFragmentPageAdapter(getChildFragmentManager(), getContext(), table, prevResults));
            TabLayout tabLayout = view.findViewById(R.id.sliding_tabs);
            tabLayout.setupWithViewPager(viewPager);
        }

        return view;
    }
}
