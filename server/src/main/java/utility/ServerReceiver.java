package utility;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Получатель
 */
public class ServerReceiver {
  // public static InetSocketAddress socketAddress;
 // public static SocketAddress socketAddress;
    /**
     * Получить
     *
     * @return массивчик байтиков
     */
    public static Object receive() {
        SocketAddress clientAddress = null;
        try {
            DatagramChannel datagramChannel = CreateServer.datagramChannel;
            ByteBuffer byteBuffer = ByteBuffer.allocate(1000000);
            byte[] bytes;
            while (true) {
                // socketAddress = (InetSocketAddress) datagramChannel.receive(byteBuffer);
                 SocketAddress socketAddress = datagramChannel.receive(byteBuffer); //
               //  ServerMain.clientAddress = socketAddress;
                 clientAddress = socketAddress;
                    if (socketAddress != null) {
                        byteBuffer.flip();
                        int limit = byteBuffer.limit();
                        bytes = new byte[limit];
                        byteBuffer.get(bytes, 0, limit);
                        byteBuffer.clear();
                        Object [] array = {bytes, clientAddress};
                        //return bytes, clientAddress;
                        return array;
                    }
                }
        } catch (IOException e) {
            System.out.println("123");
        }
        Object [] array = {new byte[0], clientAddress};
        return array;
    }
}

