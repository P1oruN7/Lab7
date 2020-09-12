package common.commands;

import static sql.Connector.saving;

/**
 * Команда "Сохрани мою речь"
 */
public class Save {
    /**
     * Метод для сохранения коллекции в файл
     */

    public void execute(String S) {
        //try {
            //saveCollection(ServerMain.c.getPath());
            saving();
            System.out.println("Коллекция сохранилася.");
        //} catch (Exception e) {
           // System.out.println("Не удалось сохраниться.");
       // }
    }

    public String getInfo() {
        return "save : сохранить коллекцию в файл";
    }
}