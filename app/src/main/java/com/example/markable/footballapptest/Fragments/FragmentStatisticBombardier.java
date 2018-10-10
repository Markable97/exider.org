package com.example.markable.footballapptest.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.example.markable.footballapptest.R;

public class FragmentStatisticBombardier extends Fragment {

    TableLayout table;

    public static FragmentStatisticBombardier newInstance(){
        FragmentStatisticBombardier fragment = new FragmentStatisticBombardier();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistic_bombardier, container, false);
        table = view.findViewById(R.id.tableForBombardier);

        return view;
    }

}
