package common.commands;

import static sql.Connector.saving;

/**
 * Команда "Сохрани мою речь"
 */
public class Save {
    /**
     * Метод для сохранения коллекции в файл
     */

    public synchronized void execute(String S) {
            saving();
            System.out.println("Коллекция сохранилася.");
    }

    public String getInfo() {
        return "save : сохранить коллекцию в файл";
    }
}