package com.example.markable.footballapptest.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markable.footballapptest.Classes.PrevMatches;
import com.example.markable.footballapptest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс адаптера наследуется от RecyclerView.Adapter с указанием класса, который будет хранить ссылки на виджеты элемента списка,
 * т.е. класса, имплементирующего ViewHolder. В нашем случае класс объявлен внутри класса адаптера.
 */

public class RecyclerViewForResults extends RecyclerView.Adapter<RecyclerViewForResults.ViewHolder> {

    private LayoutInflater inflater;
    private List<PrevMatches>  list;

    //конструктов для адаптера
    public RecyclerViewForResults(Context context, List<PrevMatches> list){
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewForResults.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycle_row_results, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewForResults.ViewHolder holder, int position) {
        PrevMatches match = list.get(position);
        holder.tour.setText("Тур" + String.valueOf(match.getIdTour()));
        holder.nameTeamHome.setText(match.getTeamHome());
        holder.nameTeamVisit.setText(match.getTeamVisit());
        holder.goalTeamHome.setText(String.valueOf(match.getGoalHome()));
        holder.goalTeamVisit.setText(String.valueOf(match.getGoalVisit()));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(ArrayList<PrevMatches> prevMatches){
        this.list = prevMatches;
        notifyDataSetChanged();
    }
    /**
     * Реализация класса ViewHolder, хранящего ссылки на виджеты.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tour;
        ImageView imageTeamHome;
        ImageView imageTeamVisit;
        TextView nameTeamHome;
        TextView nameTeamVisit;
        TextView goalTeamHome;
        TextView goalTeamVisit;

        ViewHolder(View itemView) {
            super(itemView);
            tour = itemView.findViewById(R.id.res_tour);
            imageTeamHome = itemView.findViewById(R.id.res_image_home);
            imageTeamVisit = itemView.findViewById(R.id.res_image_visit);
            nameTeamHome = itemView.findViewById(R.id.res_name_home);
            nameTeamVisit = itemView.findViewById(R.id.res_name_visit);
            goalTeamHome =  itemView.findViewById(R.id.res_goal_home);
            goalTeamVisit =  itemView.findViewById(R.id.res_goal_visit);
        }
    }
}
