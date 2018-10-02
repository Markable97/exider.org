package com.example.markable.footballapptest.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markable.footballapptest.Classes.NextMatches;
import com.example.markable.footballapptest.R;

import java.util.ArrayList;

public class AdapterForCalendar extends RecyclerView.Adapter<AdapterForCalendar.ViewHolder> {

    private ArrayList<NextMatches> list;

    public AdapterForCalendar(ArrayList<NextMatches> list) {
        this.list = list;
    }


    @Override
    public AdapterForCalendar.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_row_calendar,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterForCalendar.ViewHolder holder, int position) {
        NextMatches match = list.get(position);
        holder.tour.setText("Тур " + String.valueOf(match.getIdTour()));
        holder.nameHome.setText(match.getTeamHome());
        holder.nameVisit.setText(match.getTeamVisit());
        holder.date.setText(match.getDate());
        holder.stadium.setText(match.getNameStadium());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public void update(ArrayList<NextMatches> calendar){
        this.list = calendar;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tour;
        ImageView imageHome;
        ImageView imageVisit;
        TextView nameHome;
        TextView nameVisit;
        TextView date;
        TextView stadium;

        public ViewHolder(View itemView) {
            super(itemView);
            tour = (TextView) itemView.findViewById(R.id.cal_tour);
            imageHome = (ImageView) itemView.findViewById(R.id.cal_image_home);
            imageVisit = (ImageView) itemView.findViewById(R.id.cal_image_visit);
            nameHome = (TextView) itemView.findViewById(R.id.cal_name_home);
            nameVisit = (TextView) itemView.findViewById(R.id.cal_name_visit);
            date = (TextView) itemView.findViewById(R.id.cal_date);
            stadium = (TextView) itemView.findViewById(R.id.cal_stadium);
        }
    }
}
