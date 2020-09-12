package utility;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * Байтики в объектик
 */
public class ByteToObject {

    /**
     * Перевод байтив в объект
     *
     * @param buf массив байтиков
     */
    public static Object Cast(byte[] buf) {
        Object obj = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Map<Integer, Object> map = (Map<Integer, Object>) objectInputStream.readObject();
            if (CreateServer.serverIsAvailable) {
                CreateServer.currentClientPort = map.entrySet().iterator().next().getKey();
                obj = map.entrySet().iterator().next().getValue();
            } else if (CreateServer.currentClientPort == map.entrySet().iterator().next().getKey()) {
                obj = map.entrySet().iterator().next().getValue();
            } else {
                Object [] array = (Object[]) ServerReceiver.receive();
                return ByteToObject.Cast((byte[]) array [0]);
            }
            byteArrayInputStream.close();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
