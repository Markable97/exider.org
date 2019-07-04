package com.example.markable.footballapptest.Classes;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TestConnection {
    public boolean isConecctedToInternet() {

        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }
    public static boolean isPortOpen(final String ip, final int port) {

        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), 3000);
            socket.close();
            return true;
        }

        catch(ConnectException ce){
            ce.printStackTrace();
            return false;
        }

        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}



