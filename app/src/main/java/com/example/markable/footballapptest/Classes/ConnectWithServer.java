package com.example.markable.footballapptest.Classes;

import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.sql.Struct;
import java.util.ArrayList;

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
            socket.connect(address, 3000);
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
            //closeConnection();
            return response;

        }catch (IOException e){
            Log.v(TAG, "Невозможно отправить данные");
            throw new Exception("Невозможно отправить данные");
        }

    }

    public ArrayList<String> responseFromServerArray(String messageForServer, int n) throws Exception{

        if(socket == null || socket.isClosed()){
            Log.v(TAG, "Невозмоэно отправить данные \n" );
            throw new Exception("Невозможно отправить данные. Сокет не создан или закрыт");
        }
        ArrayList<String> arrayJsonsString = new ArrayList<>();
        String response;
        switch (n){
            case 3:
                try {
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    out.writeUTF(messageForServer);
                    Log.v(TAG,  "Данные отправлены \n" + messageForServer);
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    response = in.readUTF();//турнирная таблица в виде JSON
                    Log.i(TAG, "Турнирная таблица в виде JSON'\n" + response);
                    if(!response.isEmpty()){
                        arrayJsonsString.add(response);
                    }
                    response = in.readUTF();//Предыдущие матчи (Результаты)
                    Log.i(TAG, "Результаты в формате JSON \n" + response);
                    if(!response.isEmpty()){
                        arrayJsonsString.add(response);
                    }
                    response = in.readUTF();
                    Log.i(TAG, "Каленжарь в формате JSON \n" + response);
                    if(!response.isEmpty()){
                        arrayJsonsString.add(response);
                    }
                    //closeConnection();
                    return arrayJsonsString;


                }catch (IOException e){
                    Log.v(TAG, "Невозможно отправить или поулчить данные");
                    throw new Exception("Невозможно отправить или получить данные " +  e.getMessage());
                }

            case 2:
                try {
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    out.writeUTF(messageForServer);
                    Log.v(TAG,  "Данные отправлены \n" + messageForServer);
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    response = in.readUTF();//турнирная таблица в виде JSON
                    Log.i(TAG, "JSON 1'\n" + response);
                    if(!response.isEmpty()){
                        arrayJsonsString.add(response);
                    }
                    response = in.readUTF();//Предыдущие матчи (Результаты)
                    Log.i(TAG, "JSON 2 \n" + response);
                    if(!response.isEmpty()){
                        arrayJsonsString.add(response);
                    }
                    //closeConnection();
                    return arrayJsonsString;


                }catch (IOException e){
                    Log.v(TAG, "Невозможно отправить или поулчить данные");
                    throw new Exception("Невозможно отправить или получить данные " +  e.getMessage());
                }
        }

        return null;
    }

    public ArrayList<ImageFromServer> fileFromServer () throws Exception{
        if(socket == null || socket.isClosed()){
            Log.v(TAG, "Невозмоэно отправить данные \n" );
            throw new Exception("Невозможно отправить данные. Сокет не создан или закрыт");
        }
        try {
            ArrayList<ImageFromServer> imageArray = new ArrayList<>();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            int countFiles = in.readInt(); //число файлов с сервер
            Log.i(TAG, "Кол-во файлов " + countFiles);
            byte[] byteArrayBig;
            if (countFiles > 0){
                for(int i = 0; i < countFiles; i++){
                    String nameImageFromServer = in.readUTF();
                    Log.i(TAG, "Название картинки = " + nameImageFromServer);
                    int countBytesBig = in.readInt();
                    Log.i(TAG, "Кол-во байтов большой картинки" + countBytesBig);
                    byteArrayBig = new byte[countBytesBig];
                    in.readFully(byteArrayBig);
                    Log.i(TAG, "Размер массива большой картинки байтов " + byteArrayBig.length);
                    imageArray.add(new ImageFromServer(nameImageFromServer,
                            BitmapFactory.decodeByteArray(byteArrayBig, 0, byteArrayBig.length) ));
                }
                Log.i(TAG, " ImageFromServer = " + imageArray.size());
            }
            //closeConnection();
            if(imageArray.size() > 0 ){
                Log.i(TAG, "кол-во файлов от сервара = " + imageArray.size());
                return imageArray;
            }else{
                Log.i(TAG, "Файлы не пришли");
                return null;
                //throw new Exception("Файлы не пришли");
            }
        }catch (Exception e){
            Log.i(TAG, "Связь с сервером потеряна");
            throw new Exception("Связь с сервером потеряна"+ e.getMessage());
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
