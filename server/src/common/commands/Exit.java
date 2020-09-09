package common.commands;

import common.*;

import java.net.SocketAddress;

/**
 * Команда "ВЫХОДА НЕТ"
 */
public class Exit {
    /**
     * Метод для прекращения работы программы
     */
    public void execute(String s) {
        if (s != null) {
            System.out.println("\nСервер завершает свою работу.");
            System.exit(0);
        }
        System.out.println("Клиент нас покинул. Продолжу сидеть в одиночестве...");
    }

    public String getInfo() {
        return "exit : завершить программу ";
    }
}
