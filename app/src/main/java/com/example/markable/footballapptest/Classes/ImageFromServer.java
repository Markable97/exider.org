package com.example.markable.footballapptest.Classes;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ImageFromServer implements Parcelable, Serializable {

    String nameImage;
    Bitmap bitmapImageBig;
    transient Bitmap bitmapImageSmall;

    public ImageFromServer(String nameImage,Bitmap bitmapImageSmall ,Bitmap bitmapImageBig) {
        this.nameImage = nameImage;
        this.bitmapImageSmall = bitmapImageSmall;
        this.bitmapImageBig = bitmapImageBig;
    }
    public ImageFromServer(String nameImage, Bitmap bitmapImageBig){
        this.nameImage = nameImage;
        this.bitmapImageBig = bitmapImageBig;
    }
    public String getNameImage() {
        return nameImage;
    }

    public Bitmap getBitmapImageBig() {
        return bitmapImageBig;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameImage);
        dest.writeParcelable(bitmapImageBig, flags);
    }

    public static final Creator<ImageFromServer> CREATOR = new Creator<ImageFromServer>() {
        @Override
        public ImageFromServer createFromParcel(Parcel source) {
            String name = source.readString();
            Bitmap image = source.readParcelable(Bitmap.class.getClassLoader());
            return new ImageFromServer(name, image);
        }

        @Override
        public ImageFromServer[] newArray(int size) {
            return new ImageFromServer[size];
        }
    };
}
