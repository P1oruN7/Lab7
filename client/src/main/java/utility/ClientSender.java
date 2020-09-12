package utility;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.Map;
/**
 * Отдаватель
 */
public class ClientSender {
    /**
     * Отправить
     * @param o объектик для отправочки
     */
    public static void send(Object o) {
        try {
            Map<Integer, Object> mapCommand = new HashMap<Integer, Object>();
            Object [] array = {o, ClientMain.getLogin(), ClientMain.getPassword()};
            DatagramChannel datagramChannel = DatagramChannel.open();
            datagramChannel.bind(null);
            SocketAddress serverAddress = new InetSocketAddress(ClientMain.address, ClientMain.port);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            //mapCommand.put(ClientReceiver.clientport, o); // не было
            mapCommand.put(ClientReceiver.clientport, array); //было
            objectOutputStream.writeObject(mapCommand);
            objectOutputStream.flush();
            objectOutputStream.close();
            byte[] buff = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();

            datagramChannel.configureBlocking(false);
            datagramChannel.send(ByteBuffer.wrap(buff), serverAddress);
            datagramChannel.close();
        } catch (IOException e) {
            System.out.println("Ошибка данных.");
        }
    }

    public static void sendWithoutLogPass(Object o) {
        try {
            Map<Integer, Object> map = new HashMap<Integer, Object>();
            DatagramChannel datagramChannel = DatagramChannel.open();
            datagramChannel.bind(null);
            SocketAddress serverAddress = new InetSocketAddress(ClientMain.address, ClientMain.port);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            map.put(ClientReceiver.clientport, o);
            objectOutputStream.writeObject(map);
            objectOutputStream.flush();
            objectOutputStream.close();
            byte[] buff = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            datagramChannel.configureBlocking(false);
            datagramChannel.send(ByteBuffer.wrap(buff), serverAddress);
            datagramChannel.close();
        } catch (IOException e) {
            System.out.println("Ошибка данных.");
        }
    }


}
