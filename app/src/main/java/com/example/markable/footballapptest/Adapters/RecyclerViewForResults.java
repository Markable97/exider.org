package com.example.markable.footballapptest.Adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markable.footballapptest.Classes.ImageFromServer;
import com.example.markable.footballapptest.Classes.PrevMatches;
import com.example.markable.footballapptest.MainActivity;
import com.example.markable.footballapptest.MatchActivity;
import com.example.markable.footballapptest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс адаптера наследуется от RecyclerView.Adapter с указанием класса, который будет хранить ссылки на виджеты элемента списка,
 * т.е. класса, имплементирующего ViewHolder. В нашем случае класс объявлен внутри класса адаптера.
 */

public class RecyclerViewForResults extends RecyclerView.Adapter<RecyclerViewForResults.ViewHolder> {

    private static final String TAG = "AdapterResults";

    private List<PrevMatches>  list;
    private ArrayList<ImageFromServer> listImage;
    private Context context;
    ImageFromServer imageHome;
    ImageFromServer imageVisit;

    //конструктов для адаптера
    public RecyclerViewForResults(Context context, List<PrevMatches> list, ArrayList<ImageFromServer> listImage){
        this.list = list;
        this.listImage = listImage;
        this.context = context;
    }

    @Override
    public RecyclerViewForResults.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_row_results, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewForResults.ViewHolder holder, int position) {
        final PrevMatches match = list.get(position);

        holder.tour.setText("Тур " + String.valueOf(match.getIdTour()));
        for(int i = 0; i < list.size(); i++){
            for(int j = 0; j < listImage.size(); j++){
                if(match.getTeamHome().equalsIgnoreCase(listImage.get(j).getNameImage())){
                    holder.imageTeamHome.setImageBitmap(listImage.get(j).getBitmapImageBig());
                } else if (match.getTeamVisit().equalsIgnoreCase(listImage.get(j).getNameImage())){
                    holder.imageTeamVisit.setImageBitmap(listImage.get(j).getBitmapImageBig());
                }
            }
        }
        holder.nameTeamHome.setText(match.getTeamHome());
        holder.nameTeamVisit.setText(match.getTeamVisit());
        holder.goalTeamHome.setText(String.valueOf(match.getGoalHome()));
        holder.goalTeamVisit.setText(String.valueOf(match.getGoalVisit()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MatchActivity.class);
                for(int i = 0; i < listImage.size(); i++){
                    if(match.getTeamHome().equals(listImage.get(i).getNameImage())){
                        imageHome = listImage.get(i);
                    }
                    if(match.getTeamVisit().equals(listImage.get(i).getNameImage())){
                        imageVisit = listImage.get(i);
                    }
                }
                Log.i(TAG, "onClick: " + imageHome.getNameImage() + imageVisit.getNameImage());
                intent.putExtra("information", match);
                intent.putExtra("imageHome", (Parcelable) imageHome);
                intent.putExtra("imageVisit", (Parcelable) imageVisit);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(ArrayList<PrevMatches> prevMatches, ArrayList<ImageFromServer> image){
        this.list = prevMatches;
        this.listImage = image;
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
