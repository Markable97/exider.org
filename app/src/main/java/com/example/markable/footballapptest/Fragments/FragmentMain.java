package com.example.markable.footballapptest.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.markable.footballapptest.Adapters.SampleFragmentPageAdapter;
import com.example.markable.footballapptest.Classes.TournamentTable;
import com.example.markable.footballapptest.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class FragmentMain extends Fragment {

    private static final String TAG = "FragMain";

    View view;

    private String prevResults = "", table = "";

    private ArrayList<Bitmap> imageBitmap = new ArrayList<>();
    private ArrayList<TournamentTable> tournamentTable = new ArrayList<>();

    SampleFragmentPageAdapter mAdapter;

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "OnCreate: ");
    }

    public class ServerConnectTest extends AsyncTask<String, Void, String>{

        //String query = "{\"id_division\":1,\"id_tour\":2}";
        String query = "";
        String fromServer = null, fromServerResultsPrevMatches = null ;
        String ipAdres = "192.168.0.105";
        //String ipAdres = "10.0.2.2";


        @Override
        protected String doInBackground(String... strings) {

            for(String s : strings){
                query = "{\"id_division\":" + s + ",\"id_tour\":2}";
            }

            Log.i(TAG, "Поток запущен");
            Socket socket;
            Gson gson = new Gson();
            try {
                socket = new Socket(ipAdres, 55555);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                //DataInputStream inResultsPrev = new DataInputStream((socket.getInputStream()));
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                Log.i(TAG, "doInBackground: ServerTestConnect");
                out.writeUTF(query);

                fromServer = in.readUTF();
                table = fromServer;
                Log.i(TAG, "Данные с сервера в виду JSON = " + fromServer);
                tournamentTable.clear();
                tournamentTable = gson.fromJson(table, new TypeToken<ArrayList<TournamentTable>>(){}.getType());
                fromServerResultsPrevMatches = in.readUTF();
                prevResults = fromServerResultsPrevMatches;
                Log.i(TAG,"[2] Данные с сервера в виде JSON = " + fromServerResultsPrevMatches);

                int countFiles = in.readInt();
                imageBitmap.clear();
                Log.i(TAG, "doInBackground ServerTest: Кол-во файлов " + countFiles);
                byte[] byteArray;
                for(int i = 0; i < countFiles; i++){
                    int countBytes = in.readInt();
                    Log.i(TAG, "doInBackground: кол-во байтов пришло = " + countBytes );
                    byteArray = new byte[countBytes];
                    in.readFully(byteArray);
                    //int countFromServer = in.read(byteArray, 0, countBytes);
                    Log.i(TAG, "doInBackground: размер массива байтов " + byteArray.length);
                    //imageBitmap.add(BitmapFactory.decodeByteArray(byteArray, 0, countFromServer));
                    tournamentTable.get(i).setImage(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
                }
                //Log.i(TAG, "doInBackground: Bitmap = " + imageBitmap.size());

                out.writeUTF("close");
                out.close();
                in.close();
               // inResultsPrev.close();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //передача адаптеру
            mAdapter = new SampleFragmentPageAdapter(getChildFragmentManager(), getContext(), tournamentTable, prevResults);
            viewPager.setAdapter(mAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }
    }


    public void update (String idDivision){
        Log.i(TAG, "Interface: Передаче Pager-у");
        new ServerConnectTestDouble().execute(idDivision);
       // mAdapter.update(table, prevResults);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        Log.i(TAG, "OnCreateView: Загрузка главного фрагмента");

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.sliding_tabs);


        new ServerConnectTest().execute("1");
       // update("1");
        /*while (table.equals("")){
        }
        if (!table.equals("")){
            mAdapter = new SampleFragmentPageAdapter(getChildFragmentManager(), getContext(), table, prevResults, imageBitmap);
            Log.i(TAG, "Зашел в if. Поля основного класса\n " + "Таблица ="  + table + "\n Результаты =" + prevResults);
            ViewPager viewPager = view.findViewById(R.id.viewPager);
            //viewPager.setAdapter(new SampleFragmentPageAdapter(getChildFragmentManager(), getContext(), table, prevResults));
            viewPager.setAdapter(mAdapter);
            TabLayout tabLayout = view.findViewById(R.id.sliding_tabs);
            tabLayout.setupWithViewPager(viewPager);
        }*/

        return view;
    }

    public class ServerConnectTestDouble extends AsyncTask<String, Void, String>{

        //String query = "{\"id_division\":1,\"id_tour\":2}";
        String query = "";
        String fromServer = null, fromServerResultsPrevMatches = null ;
        String ipAdres = "192.168.0.105";
        //String ipAdres = "10.0.2.2";


        @Override
        protected String doInBackground(String... strings) {

            for(String s : strings){
                query = "{\"id_division\":" + s + ",\"id_tour\":2}";
            }

            Log.i(TAG, "Поток запущен");
            Socket socket;
            Gson gson = new Gson();
            try {
                socket = new Socket(ipAdres, 55555);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                //DataInputStream inResultsPrev = new DataInputStream((socket.getInputStream()));
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                out.writeUTF(query);

                fromServer = in.readUTF();
                table = fromServer;
                Log.i(TAG, "Данные с сервера в виду JSON = " + fromServer);
                tournamentTable.clear();
                tournamentTable = gson.fromJson(table, new TypeToken<ArrayList<TournamentTable>>(){}.getType());
                fromServerResultsPrevMatches = in.readUTF();
                prevResults = fromServerResultsPrevMatches;
                Log.i(TAG,"[2] Данные с сервера в виде JSON = " + fromServerResultsPrevMatches);

                int countFiles = in.readInt();
                imageBitmap.clear();
                Log.i(TAG, "doInBackground ServerTest: Кол-во файлов " + countFiles);
                for(int i = 0; i < countFiles; i++){
                    int countBytes = in.readInt();
                    Log.i(TAG, "doInBackground: кол-во байтов = " + countBytes );
                    byte[] byteArray = new byte[countBytes];
                    //int countFromServer = in.read(byteArray, 0, countBytes);
                    in.readFully(byteArray);
                    Log.i(TAG, "doInBackground: размер массива байтов " + byteArray.length);
                    //imageBitmap.add(BitmapFactory.decodeByteArray(byteArray, 0, countFromServer));
                    tournamentTable.get(i).setImage(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
                }
               // Log.i(TAG, "doInBackground: Bitmap = " + imageBitmap.size());

                out.writeUTF("close");
                out.close();
                in.close();
                //inResultsPrev.close();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //передача адаптеру
            mAdapter.update(tournamentTable, prevResults);
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
