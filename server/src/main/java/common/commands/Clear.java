package common.commands;

import common.*;
import routes.Route;
import utility.ServerMain;
import utility.ServerSender;
import java.net.SocketAddress;
import java.util.Iterator;

/**
 * Команда "ЧИСТИЛЬЩИК "
 */
public class Clear implements Command {
    private static final long serialVersionUID = 6529685098267757690L;

    /**
     * Метод для очистки коллекции, подаваемой на вход
     */
    @Override
    public synchronized void execute(String login, SocketAddress clientAddress) {
        Iterator<Route> iterator = ServerMain.c.Routes.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getCreatorLogin().equals(login)) iterator.remove();
        }


        ServerSender.send("\n \nКоллекция была очищена от ваших элементов, как картошечка для супчика \n \n", 0, clientAddress);
    }


    @Override
    public String getInfo() {
        return "clear : очистить коллекцию";
    }
}
