package common.commands;

import common.*;
import utility.ServerMain;

import java.net.SocketAddress;

/**
 * Команда "Сохрани мою речь"
 */
public class Save {
    /**
     * Метод для сохранения коллекции в файл
     */

    public void execute(String S) {
        try {
            //saveCollection(ServerMain.c.getPath());
            System.out.println("Коллекция сохранилася.");
        } catch (Exception e) {
            System.out.println("Не удалось сохраниться.");
        }
    }

    public String getInfo() {
        return "save : сохранить коллекцию в файл";
    }
}