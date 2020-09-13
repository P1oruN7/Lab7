package generators;

import routes.Route;
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
        int num = 0;
        boolean isIdExist = true;

        while (isIdExist){;
            num++;
            Route r = ServerMain.c.searchById(num);
            if (r == null) isIdExist = false;
        }

        return num;
    }

}
