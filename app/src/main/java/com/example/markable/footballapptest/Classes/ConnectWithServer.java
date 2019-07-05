package com.example.markable.footballapptest.Classes;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.sql.Struct;

public class ConnectWithServer {

    private static final String TAG = "ConnectWithServer";
    private String IP = PublicConstants.IP;
    private int PORT = PublicConstants.port;

    Socket socket = null;

    public ConnectWithServer() {

    }

    /**  * Открытие нового соединения. Если сокет уже открыт, то он закрывается.  *
     * * @throws Exception  *
     * Если не удалось открыть сокет  */
    public void openConnection() throws Exception {
        /* Освобождаем ресурсы */
        closeConnection();
        try {
            /*
             Создаем новый сокет. Указываем на каком компютере и порту запущен наш процесс,
                          который будет принамать наше соединение.         */
            SocketAddress address = new InetSocketAddress(IP, PORT);
            socket = new Socket();
            socket.connect(address, 5000);
            Log.i(TAG,  "Сокет создан");
        } catch (SocketTimeoutException ex){
            Log.i(TAG, "Закончено время");
            socket = null;
            throw new Exception("Невозможно создать сокет: "+ex.getMessage());
        } catch (IOException e) {
            Log.i(TAG,  "Сокет закрыт");
            socket = null;
            throw new Exception("Невозможно создать сокет: "+e.getMessage());
        }
    }

    public void onlySendDate(String message) throws Exception {
        if (socket == null||socket.isClosed() ){
            Log.i(TAG, "Невозмоэно отправить данные \n" );
            throw new Exception("Невозможно отправить данные. Сокет не создан или закрыт");
        }
        DataOutputStream out = null;
        try{
            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(message);
            Log.i(TAG,  "Данные отправлены \n" + message);
            /**Закрытие сокета**/
            closeConnection();
        }catch (IOException e){
            Log.i(TAG, "Невозможно отправить данные");
            out.close();
            throw new Exception("Невозможно отправить данные");
        }
    }

    public String responseFromServer(String messageForServer) throws Exception {
        if (socket == null||socket.isClosed() ){
            Log.v(TAG, "Невозмоэно отправить данные \n" );
            throw new Exception("Невозможно отправить данные. Сокет не создан или закрыт");
        }
        try{
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(messageForServer);
            Log.v(TAG,  "Данные отправлены \n" + messageForServer);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            String response = in.readUTF();
            Log.v(TAG,  "Данные от сервера \n" + response);
            closeConnection();
            return response;

        }catch (IOException e){
            Log.v(TAG, "Невозможно отправить данные");
            throw new Exception("Невозможно отправить данные");
        }

    }

    /**  * Метод для закрытия сокета, по которому мы общались.  */
    public void closeConnection() {
        /* Проверяем сокет. Если он не зарыт, то закрываем его и освобдождаем соединение.*/
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                Log.v(TAG, "Невозможно закрыть сокет: " + e.getMessage());
            } finally {
                socket = null;
            }
        }
        socket = null;
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        closeConnection();
    }
}
