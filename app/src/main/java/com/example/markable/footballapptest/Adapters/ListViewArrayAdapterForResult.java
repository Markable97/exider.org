package com.example.markable.footballapptest.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markable.footballapptest.Classes.PrevMatches;
import com.example.markable.footballapptest.R;

import java.util.List;

public class ListViewArrayAdapterForResult extends ArrayAdapter<PrevMatches> {

    private static final String TAG = "ArrayAdapter";
    private final List<PrevMatches> list;
    private final Activity context;

    public ListViewArrayAdapterForResult(Activity context, List<PrevMatches> list){
        super(context, R.layout.recycle_row_results, list);
        Log.i(TAG, "ListViewArrayAdapterForResult: Вызов конструктора");
        this.context = context;
        this.list = list;
    }

    static class ViewHolder{
        ImageView imageTeamHome;
        ImageView imageTeamVisit;
        TextView nameTeamHome;
        TextView nameTeamVisit;
        TextView goalTeamHome;
        TextView goalTeamVisit;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        Log.i(TAG, "getView: Начало");
        // ViewHolder буферизирует оценку различных полей шаблона элемента
        ViewHolder holder;
        // Очищает сущетсвующий шаблон, если параметр задан
        // Работает только если базовый шаблон для всех классов один и тот же
        View rowView = convertView;
        if (rowView == null) {
            Log.i(TAG, "getView: Если объект пустой");
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.recycle_row_results, null, true);
            holder = new ViewHolder();
            holder.imageTeamHome = (ImageView) rowView.findViewById(R.id.imageTeamHome);
            holder.imageTeamVisit = (ImageView) rowView.findViewById(R.id.imageTeamVisit);
            holder.nameTeamHome = (TextView) rowView.findViewById(R.id.nameTeamHome);
            holder.nameTeamVisit = (TextView) rowView.findViewById(R.id.nameTeamVisit);
            holder.goalTeamHome = (TextView) rowView.findViewById(R.id.goalTeamHome);
            holder.goalTeamVisit = (TextView) rowView.findViewById(R.id.goalTeamVisit);
            rowView.setTag(holder);
        }else{
            Log.i(TAG, "getView: Объект заполнен");
            holder = (ViewHolder) rowView.getTag();
        }
        Log.i(TAG, "getView: Заполнение информацией");
        holder.nameTeamHome.setText(list.get(position).getTeamHome());
        holder.nameTeamVisit.setText(list.get(position).getTeamVisit());
        holder.goalTeamHome.setText(String.valueOf(list.get(position).getGoalHome()));
        holder.goalTeamVisit.setText(String.valueOf(list.get(position).getGoalVisit()));
        return rowView;
    }
}
