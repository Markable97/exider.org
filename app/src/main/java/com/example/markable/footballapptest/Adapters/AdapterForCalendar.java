package com.example.markable.footballapptest.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markable.footballapptest.Classes.ImageFromServer;
import com.example.markable.footballapptest.Classes.NextMatches;
import com.example.markable.footballapptest.R;

import java.util.ArrayList;

public class AdapterForCalendar extends RecyclerView.Adapter<AdapterForCalendar.ViewHolder> {

    private ArrayList<NextMatches> list;
    private ArrayList<ImageFromServer> listImage;

    public AdapterForCalendar(ArrayList<NextMatches> list, ArrayList<ImageFromServer> listImage) {
        this.list = list;
        this.listImage = listImage;
    }


    @Override
    public AdapterForCalendar.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_row_calendar,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterForCalendar.ViewHolder holder, int position) {
        NextMatches match = list.get(position);
        //ImageFromServer image = listImage.get(position);
        System.out.println();
        holder.tour.setText("Тур " + String.valueOf(match.getIdTour()));
        byte[] decodedBytes = Base64.decode(match.getImageHome(), Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray (decodedBytes, 0, decodedBytes.length);
        try {
            holder.imageHome.setImageBitmap(decodedBitmap);
        }catch (Exception e){
            System.out.println(match.getTeamHome() + "\n" + match.getImageHome());
        }
        decodedBytes = Base64.decode(match.getImageGuest(), Base64.DEFAULT);
        decodedBitmap = BitmapFactory.decodeByteArray (decodedBytes, 0, decodedBytes.length);
        holder.imageVisit.setImageBitmap(decodedBitmap);
        /*for(int i = 0; i < list.size(); i++){
            for(int j = 0; j < listImage.size(); j++){
                if(match.getTeamHome().equalsIgnoreCase(listImage.get(j).getNameImage())){
                    holder.imageHome.setImageBitmap(listImage.get(j).getBitmapImageBig());
                } else if (match.getTeamVisit().equalsIgnoreCase(listImage.get(j).getNameImage())){
                    holder.imageVisit.setImageBitmap(listImage.get(j).getBitmapImageBig());
                }
            }
        }*/
        holder.nameHome.setText(match.getTeamHome());
        holder.nameVisit.setText(match.getTeamVisit());
        holder.date.setText(match.getDate());
        holder.stadium.setText(match.getNameStadium());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public void update(ArrayList<NextMatches> calendar, ArrayList<ImageFromServer> image){
        this.list = calendar;
        this.listImage = image;
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
