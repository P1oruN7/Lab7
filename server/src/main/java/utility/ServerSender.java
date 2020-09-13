package utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Отдаватель
 */
public class ServerSender implements Runnable {
    private String message;
    private Integer needAnswer;
    private SocketAddress clientAddress;

    public ServerSender (String message, Integer needAnswer,SocketAddress clientAddress){
        this.message = message;
        this.needAnswer = needAnswer;
        this.clientAddress = clientAddress;
    }

    /**
     * Отправить
     *
     * @param message    сообщенька
     * @param needAnswer нужет ли ответ от клиента
     */
    public static void send(String message, Integer needAnswer,SocketAddress clientAddress) {
        ServerSender serverSender = new ServerSender(message, needAnswer,clientAddress);
        ExecutorService service = Executors.newFixedThreadPool(3);
        service.submit(serverSender);
        service.shutdown();
    }

    @Override
    public void run(){
        try {
            //Параметр needAnswer: 0 - ответ не нужен, 1 - ждём ответа
            Map<String, Integer> answer = new HashMap<>();
            answer.put(message, needAnswer);
            Object o = answer;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(o);
            answer.clear();
            objectOutputStream.flush();
            byteArrayOutputStream.close();
            objectOutputStream.close();
            byte[] buff = byteArrayOutputStream.toByteArray();
            DatagramSocket datagramSocket = new DatagramSocket();
            // ServerMain.clientAdderss = datagramSocket.getInetAddress(); //
            //  ServerMain.clientAdderss =  CreateServer.datagramChannel.getRemoteAddress();
            //   DatagramPacket dp = new DatagramPacket(buff, buff.length, clientAddress);
            DatagramPacket dp = new DatagramPacket(buff, buff.length, InetAddress.getLocalHost(), CreateServer.currentClientPort); //Изначально было вот это
            datagramSocket.send(dp);
            datagramSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}