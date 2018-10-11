package com.example.markable.footballapptest.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.markable.footballapptest.R;

public class FragmentStatisticAssistant extends Fragment {

    public static FragmentStatisticAssistant  newInstance(){
        FragmentStatisticAssistant fragmnet = new FragmentStatisticAssistant();

        return fragmnet;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistic_assistant, container, false);

        return view;
    }

}
