package com.example.markable.footballapptest.Fragments;

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

public class FragmentForCalendar extends Fragment {

    private static final String TAG = "FragCal";

    private String fromActivity;

    public static FragmentForCalendar newInstance(String data){
        FragmentForCalendar fragment = new FragmentForCalendar();

        Bundle args = new Bundle();
        args.putString("calendar", data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fromActivity = getArguments().getString("calendar", "");
        Log.i(TAG, "OnCreate: Получение строки из Bundle");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_calendar, container, false);

        TextView textView = view.findViewById(R.id.textView_calendar);
        textView.setText(fromActivity);

        Log.i(TAG, "OnCreateView: Загрузка окна фрагмента " + fromActivity);
        return view;
    }
}
