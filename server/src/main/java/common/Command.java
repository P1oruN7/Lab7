package common;

import java.io.Serializable;
import java.net.SocketAddress;

/**
 * Интерфейс Command
 */
public interface Command extends Serializable {
    /**
     * Экзекьюте
     *
     * @param par1 Входная строка
     */
    abstract public void execute(String par1, SocketAddress socketAddress);

    /**
     * Получить немножко информации о команде
     *
     * @return строчечка информации
     */
    abstract public String getInfo();
}

