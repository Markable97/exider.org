package com.example.markable.footballapptest.Classes;

import android.graphics.Bitmap;

import java.io.Serializable;

public class ImageFromServer implements Serializable {

    String nameImage;
    Bitmap bitmapImage;

    public ImageFromServer(String nameImage, Bitmap bitmapImage) {
        this.nameImage = nameImage;
        this.bitmapImage = bitmapImage;
    }

    public String getNameImage() {
        return nameImage;
    }

    public Bitmap getBitmapImage() {
        return bitmapImage;
    }
}
