package com.example.markable.footballapptest.Classes;

import android.os.Bundle;
import android.os.Message;

public class PublicConstants {
    public static final String IP = "192.168.0.103";
    //public static final String IP = "10.0.2.2";
    public static final int port = 55555;

    public static Message getMessage(String str, String key){
        Bundle messageBundle = new Bundle();
        messageBundle.putString(key, str);

        Message message = new Message();
        message.setData(messageBundle);
        return message;
    }
}
