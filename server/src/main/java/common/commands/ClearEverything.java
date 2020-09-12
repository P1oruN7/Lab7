package common.commands;

import users.UsersCollection;
import utility.ServerMain;

public class ClearEverything {
    public static void execute( String par) {
        ServerMain.c.Routes.clear();
        UsersCollection.users.clear();
        System.out.println("Все данные успешно стёрты");
    }
    public String getInfo() {
        return "clear_everything : очистить коллекцию рутов и юзеров";
    }
}
