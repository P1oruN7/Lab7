package common.commands;

import users.UsersCollection;
import utility.ServerMain;

/**
 * Команда для стирания всех-превсех данных из бд
 */
public class ClearEverything {
    public synchronized static void execute(String par) {
        ServerMain.c.Routes.clear();
        UsersCollection.users.clear();
        System.out.println("Все данные успешно стёрты");
    }

    public String getInfo() {
        return "clear_everything : очистить коллекцию рутов и юзеров";
    }
}
