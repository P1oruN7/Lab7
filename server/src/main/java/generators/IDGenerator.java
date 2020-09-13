package generators;

import utility.ServerMain;

/**
 * Генератор id
 */
public class IDGenerator {
    /**
     * Метод для создания id
     *
     * @return id (Long)
     */
    public static long generateNewID() {
        return ServerMain.c.Routes.size()+1;
    }

}
