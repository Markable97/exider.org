package com.example.markable.footballapptest.Classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ImageFromServer implements Parcelable, Serializable {

    String nameImage;
    transient Bitmap bitmapImageBig;


    public ImageFromServer(String nameImage,Bitmap bitmapImageSmall ,Bitmap bitmapImageBig) {
        this.nameImage = nameImage;
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

    private void writeObject(ObjectOutputStream oos) throws IOException{
        //Сериализуем все объекты кроме теъ, которые помечены transient
        oos.defaultWriteObject();
        //Теперь которые помечены
        if(bitmapImageBig != null){
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            boolean success = bitmapImageBig.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
            if(success){
                oos.writeObject(byteStream.toByteArray());
            }
        }
    }
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException{
       //Дессеоиализация. Сначала обычные объекты (без transient)
        ois.defaultReadObject();
        // Объект который сериализовали в ручную
        byte[] image = (byte[]) ois.readObject();
        if(image != null && image.length > 0){
            bitmapImageBig = BitmapFactory.decodeByteArray(image, 0, image.length);
        }
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
