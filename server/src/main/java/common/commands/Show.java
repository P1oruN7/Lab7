package common.commands;

import common.*;
import routes.Route;
import utility.ServerMain;
import utility.ServerSender;

import java.net.SocketAddress;

/**
 * Команда "ГЛЯДИ! "
 */
public class Show implements Command {
    private static final long serialVersionUID = 6529685098267757690L;

    /**
     * Метод для отображения всех элементов коллекции
     */
    @Override
    public synchronized void execute(String S, SocketAddress clientAddress) {
        if (ServerMain.c.Routes.size() == 0) {
            ServerSender.send("\n\nКоллекция пуста, милорд\n\n", 0, clientAddress);
        } else {
            String string = "\n";
            for (Route r : ServerMain.c.Routes) {
                string += "  " + r.toString() + "\n";
            }
            ServerSender.send(string, 0, clientAddress);

        }
    }

    @Override
    public String getInfo() {
        return "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
