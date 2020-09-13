package common.commands;

import common.*;
import utility.ServerMain;
import utility.ServerSender;

import java.net.SocketAddress;
import java.util.Collections;

/**
 * Команда "СОРТИРОВКА"
 */
public class Sort implements Command {
    private static final long serialVersionUID = 6529685098267757690L;

    /**
     * Метод для сортировки коллекции в ествественном порядке (по возрастанию id)
     */
    @Override
    public synchronized void execute(String S, SocketAddress clientAddress) {
        Collections.sort(ServerMain.c.Routes);
        ServerSender.send("\n\nКоллекция была отсортирована.\n\n", 0, clientAddress);    }

    @Override
    public String getInfo() {
        return "sort: отсортиовать коллекцию в ествественном порядке.";
    }

}
