package common.commands;

import common.*;
import readers.*;
import routes.Route;
import utility.ServerMain;
import utility.ServerSender;
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
    public synchronized void execute(String s, SocketAddress clientAddress) {
        try {
            String[] array = s.split(" ");

            if (array[0] == null | array[0].trim().equals("") | array.length == 1) {
                System.out.println("кажется вы забыли ввести айди");
                return;
            }
            long removeId = Checker.longChecker(array[0]);
            Route r = ServerMain.c.searchById(removeId);
            if (r == null) {
                ServerSender.send("похоже элемента с таким айди не существует", 0, clientAddress);
                return;
            }
            if (!array[1].equals(r.getCreatorLogin())) {
                ServerSender.send("У вас нет прав на удаление данного элемента", 0, clientAddress);
                return;
            }
            ServerMain.c.Routes.remove(ServerMain.c.Routes.indexOf(r));
            ServerSender.send("Элемент успешно удалён из коллекции. Такие дела.", 0, clientAddress);
        } catch (Exception e) {
            System.out.println("Произошла ошибка при удалении объекта.");
            ServerSender.send("Произошла ошибка при удалении объекта.", 0, clientAddress);
        }
    }

    @Override
    public String getInfo() {
        return "remove_by_id id : удалить элемент из коллекции по его id";
    }

}
