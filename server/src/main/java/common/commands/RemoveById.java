package common.commands;

import common.Command;
import readers.Checker;
import routes.Route;
import utility.ServerMain;
import utility.ServerSender;

import common.*;
import readers.*;

import java.net.SocketAddress;

/**
 * Команда "УДОЛИ!"
 */
public class RemoveById implements Command {
    private static final long serialVersionUID = 6529685098267757690L;

    /**
     * метод удаления элемента коллекции по его id
     */
    @Override
    public void execute(String s, SocketAddress clientAddress) {
        if (s == null | s.equals("")) {
            System.out.println("кажется вы забыли ввести айди");
            return;
        }
        long removeId = Checker.longChecker(s);
        Route r = ServerMain.c.searchById(removeId);
        if (r == null) {
            System.out.println("похоже элемента с таким айди не существует");
            ServerSender.send("Элемент успешно удалён из коллекции. Вот.", 0, clientAddress);
            return;
        }
        ServerMain.c.Routes.remove(ServerMain.c.Routes.indexOf(r));
    }

    @Override
    public String getInfo() {
        return "remove_by_id id : удалить элемент из коллекции по его id";
    }

}
