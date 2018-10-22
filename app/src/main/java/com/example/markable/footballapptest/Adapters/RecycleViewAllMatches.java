package com.example.markable.footballapptest.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markable.footballapptest.Classes.AllMatchesForTeam;
import com.example.markable.footballapptest.Classes.ImageFromServer;
import com.example.markable.footballapptest.R;

import java.util.ArrayList;

public class RecycleViewAllMatches extends RecyclerView.Adapter<RecycleViewAllMatches.ViewHolder> {

    private ArrayList<AllMatchesForTeam> list;
    private ArrayList<ImageFromServer> listImage;

    public RecycleViewAllMatches(ArrayList<AllMatchesForTeam> list, ArrayList<ImageFromServer> listImage) {
        this.list = list;
        this.listImage = listImage;
    }

    @Override
    public RecycleViewAllMatches.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_row_results, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecycleViewAllMatches.ViewHolder holder, int position) {
        AllMatchesForTeam allMatches = list.get(position);
        holder.tour.setText("Тур" + String.valueOf(allMatches.getMatches().getIdTour()));
        //здесь будут картинки
        for(int i = 0; i < list.size(); i++){
            for(int j = 0; j < listImage.size(); j++){
                if(allMatches.getMatches().getTeamHome().equalsIgnoreCase(listImage.get(j).getNameImage())){
                    holder.imageTeamHome.setImageBitmap(listImage.get(j).getBitmapImageBig());
                } else if (allMatches.getMatches().getTeamVisit().equalsIgnoreCase(listImage.get(j).getNameImage())){
                    holder.imageTeamVisit.setImageBitmap(listImage.get(j).getBitmapImageBig());
                }
            }
        }
        holder.nameTeamHome.setText(allMatches.getMatches().getTeamHome());
        holder.nameTeamVisit.setText(allMatches.getMatches().getTeamVisit());
        holder.goalTeamHome.setText(String.valueOf(allMatches.getMatches().getGoalHome()));
        holder.goalTeamVisit.setText(String.valueOf(allMatches.getMatches().getGoalVisit()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

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
