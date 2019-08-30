package com.example.markable.footballapptest.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.markable.footballapptest.R;

public class FragmentProtocolTeam  extends Fragment {

    private static final String TAG = FragmentProtocolTeam.class.getSimpleName();

    public static final String KEY = "protocol_name";

    TextView tv_teamName;

    String teamName;

    public static FragmentProtocolTeam newInstance(String name){
        FragmentProtocolTeam fragment = new FragmentProtocolTeam();
        Bundle args = new Bundle();
        args.putString(KEY, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            teamName = getArguments().getString(KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_protocol_team, container,false);
        tv_teamName = view.findViewById(R.id.protocol_team);
        tv_teamName.setText(teamName);
        return view;
    }
}
