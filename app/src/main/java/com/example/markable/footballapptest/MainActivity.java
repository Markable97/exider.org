package com.example.markable.footballapptest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.markable.footballapptest.Classes.ImageFromServer;
import com.example.markable.footballapptest.Classes.NextMatches;
import com.example.markable.footballapptest.Classes.PrevMatches;
import com.example.markable.footballapptest.Classes.TournamentTable;
import com.example.markable.footballapptest.Fragments.FragmentMain;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //final String IP = "10.0.2.2";
    final String IP = "192.168.0.105";

    private static final String TAG = "MainAct";
    FragmentMain fragmentMain;

    private ArrayList<ImageFromServer> imageArray = new ArrayList<>();
    private ArrayList<TournamentTable> tournamentTable = new ArrayList<>();
    private ArrayList<PrevMatches> prevResultsMatch = new ArrayList<>();
    private ArrayList<NextMatches> nextResultsMatch = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MainServerConnect().execute("1");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //Fragment fragment = null;
        //Class fragmentClass = null;

        String dataForFragment = null;

        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            //fragmentClass = FragmentForTable.class;
            dataForFragment = "1";
        } else if (id == R.id.nav_gallery) {
            dataForFragment = "2";
        } else if (id == R.id.nav_slideshow) {
            dataForFragment = "3";
        } else if (id == R.id.nav_manage) {
            dataForFragment = "4";
        } else if (id == R.id.nav_share) {
            dataForFragment = "5";
        } else if (id == R.id.nav_send) {
            dataForFragment = "6";
        }


        fragmentMain.update(dataForFragment);

        /*try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }*/

        /*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        FragmentForTable fragForDiv = new FragmentForTable().newInstance(dataForFragment);
        fragmentTransaction.replace(R.id.container, fragForDiv).commit();*/
        item.setChecked(true);
        setTitle(item.getTitle());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class MainServerConnect extends AsyncTask<String, Void, String>{

        //String query = "{\"id_division\":1,\"id_tour\":2}";
        String query = "";
        String fromServer = null, fromServerResultsPrevMatches = null, fromServerCalendar = null ;
        //String ipAdres = "192.168.0.104";
        //String ipAdres = "92.38.241.107";
        //String ipAdres = "10.0.2.2"


        @Override
        protected String doInBackground(String... strings) {

            for(String s : strings){
                query = "{\"messageLogic\":\"division\",\"id_division\":"+ s + "}";
                //query = "{\"id_division\":" + s + ",\"id_tour\":2}";
            }

            Log.i(TAG, "Поток запущен");
            Socket socket;
            Gson gson = new Gson();
            try {
                socket = new Socket(IP, 55555);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                //DataInputStream inResultsPrev = new DataInputStream((socket.getInputStream()));
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                out.writeUTF(query);

                fromServer = in.readUTF();
                Log.i(TAG, "Данные с сервера в виду JSON = " + fromServer);
                tournamentTable.clear();
                tournamentTable = gson.fromJson(fromServer, new TypeToken<ArrayList<TournamentTable>>(){}.getType());
                for(int i = 0; i<tournamentTable.size(); i++){
                    Log.i(TAG, "doInBackground: " + tournamentTable.get(i).toString());
                }
                fromServerResultsPrevMatches = in.readUTF();
                prevResultsMatch.clear();
                prevResultsMatch = gson.fromJson(fromServerResultsPrevMatches, new TypeToken<ArrayList<PrevMatches>>(){}.getType());
                Log.i(TAG,"[2] Данные с сервера в виде JSON = " + fromServerResultsPrevMatches);
                for(int i = 0; i<prevResultsMatch.size(); i++){
                    Log.i(TAG, "doInBackground: " + prevResultsMatch.get(i).toString());
                }
                fromServerCalendar = in.readUTF();
                nextResultsMatch.clear();
                nextResultsMatch = gson.fromJson(fromServerCalendar, new TypeToken<ArrayList<NextMatches>>(){}.getType());
                Log.i(TAG, "[3] Данные с сервера в виде JSON = " + fromServerCalendar);
                Log.i(TAG, nextResultsMatch.toString());
                imageArray.clear();
                int countFiles = in.readInt();
                byte[] byteArrayBig;
                Log.i(TAG, "doInBackground ServerTest: Кол-во файлов " + countFiles);
                for(int i = 0; i < countFiles; i++){
                    String nameImageFromServer = in.readUTF();
                    Log.i(TAG, "doInBackground: название картинки = " + nameImageFromServer);
                    /*int countBytes = in.readInt();
                    Log.i(TAG, "doInBackground: кол-во байтов = " + countBytes );
                    byte[] byteArray = new byte[countBytes];
                    //int countFromServer = in.read(byteArray, 0, countBytes);
                    in.readFully(byteArray);
                    Log.i(TAG, "doInBackground: размер массива байтов " + byteArray.length);*/
                    int countBytesBig = in.readInt();
                    Log.i(TAG, "doInBackground: кол-во байтов большой картинки" + countBytesBig);
                    byteArrayBig = new byte[countBytesBig];
                    in.readFully(byteArrayBig);
                    Log.i(TAG, "doInBackground: размер массива большой картинки байтов " + byteArrayBig.length);
                    imageArray.add(new ImageFromServer(nameImageFromServer,/*
                            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length),*/
                            byteArrayBig));
                }
                Log.i(TAG, "doInBackground: ImageFromServer = " + imageArray.size());

                out.writeUTF("{\"messageLogic\":\"close\"}");
                out.close();
                in.close();
                //inResultsPrev.close();
                socket.close();

/* я крокодил, крокожу и буду крокодить */

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            fragmentMain = new FragmentMain();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameContainer, fragmentMain).commit();
        }

    }

    public ArrayList<NextMatches> getNextResultsMatch() {
        return nextResultsMatch;
    }

    public ArrayList<TournamentTable> getTournamentTable() {
        return tournamentTable;
    }

    public ArrayList<PrevMatches> getPrevResultsMatch() {
        return prevResultsMatch;
    }

    public ArrayList<ImageFromServer> getImageArray() {
        return imageArray;
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
