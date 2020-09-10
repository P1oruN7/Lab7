package utility;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Map;
/**
 * Получатель
 */
public class ClientReceiver {
    public static DatagramSocket sock;
    public static int clientport;
    public static Map<String, Integer> answer;

    /**
     * Получить массивчик байтиков
     */
    public static void receive(){
        byte[] buffer = new byte[1000000];
        try {
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            sock.setSoTimeout(5000);
            sock.receive(reply);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(reply.getData());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object obj = objectInputStream.readObject();
            byteArrayInputStream.close();
            objectInputStream.close();
            answer = (Map<String, Integer>) obj;
            if (answer.entrySet().iterator().next().getValue() == 0) {
                System.out.println("Ответ с сервера: " + answer.entrySet().iterator().next().getKey());
            } else if (answer.entrySet().iterator().next().getValue() == 1) {
                System.out.println("Ответ с сервера: " + answer.entrySet().iterator().next().getKey());
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String s = reader.readLine();
                ClientSender.send(s);
                ClientReceiver.receive();
            }
        } catch (SocketException e) {
            System.out.println("Невозможно подключиться к серверу.");
        } catch (SocketTimeoutException e) {
            System.out.println("Возможно сервер занят или выключен,попробуйте ещё раз.");
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода.");
        } catch (ClassNotFoundException e) {
            System.out.println("Невозможно преобразовать данные, присланные сервером");
        }
    }




    public static String receiveObject(){
        byte[] buffer = new byte[1000000];
        try {
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            sock.setSoTimeout(5000);
            sock.receive(reply);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(reply.getData());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object obj = objectInputStream.readObject();
            byteArrayInputStream.close();
            objectInputStream.close();
            answer = (Map<String, Integer>) obj;

            return answer.entrySet().iterator().next().getKey();

        } catch (SocketException e) {
            System.out.println("Невозможно подключиться к серверу.");
        } catch (SocketTimeoutException e) {
            System.out.println("Возможно сервер занят или выключен,попробуйте ещё раз.");
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода.");
        } catch (ClassNotFoundException e) {
            System.out.println("Невозможно преобразовать данные, присланные сервером");
        }
        return null;
    }


//    public static byte[] receiveObject (){
//        try {
//            sock.setSoTimeout(5000);
//            ByteBuffer byteBuffer = ByteBuffer.allocate(1000000);
//            byte[] bytes;
//            SocketAddress sa = new InetSocketAddress(ClientMain.address, ClientMain.port);
//            DatagramChannel dc = DatagramChannel.open();
//            dc.configureBlocking(false);
//            dc.bind(sa);
//            while (true) {
//                SocketAddress socketAddress = dc.receive(byteBuffer);
//                if (socketAddress != null) {
//                    byteBuffer.flip();
//                    int limit = byteBuffer.limit();
//                    bytes = new byte[limit];
//                    byteBuffer.get(bytes, 0, limit);
//                    byteBuffer.clear();
//                    return bytes;
//                }
//            }
//        }catch (SocketTimeoutException e) {
//            System.out.println("Возможно сервер занят или выключен,попробуйте ещё раз.");
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("1232wertyuiop");
//        }
//        return new byte[0];
//    }
}