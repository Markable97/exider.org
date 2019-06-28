package com.example.markable.footballapptest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.markable.footballapptest.Adapters.RecyclerViewAddResults;
import com.example.markable.footballapptest.Classes.PrevMatches;

import java.util.ArrayList;
import java.util.List;

public class AddResultsActivity extends Activity {

    RecyclerView recyclerView;
    RecyclerViewAddResults adapter;

    private List<PrevMatches> matches = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_results);

        addInformation();


        recyclerView = (RecyclerView) findViewById(R.id.listForAddResults);
        adapter = new RecyclerViewAddResults(getApplicationContext(), matches);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        //recyclerView.setLayoutManager(mLayoutManager);
       // recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        //заглушка - заполнение данными

    }

    private void addInformation() {
        PrevMatches match = new PrevMatches(7, "Морс", "Гравити");
        matches.add(match);
        match = new PrevMatches(7, "Профит", "Усадьба Банная");
        matches.add(match);
        match = new PrevMatches(7, "Авангард", "Титан");
        matches.add(match);
        match = new PrevMatches(7, "Торпедо", "Зубр");
        matches.add(match);
        match = new PrevMatches(7, "СКА", "Лефортово");
        matches.add(match);
        match = new PrevMatches(7, "Селтик", "ЛФК Империя");
        matches.add(match);

    }
}
