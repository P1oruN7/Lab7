package common.commands;

import common.Command;
import routes.Route;
import utility.ServerMain;
import utility.ServerSender;

import common.*;

import java.net.SocketAddress;
import java.util.Arrays;

/**
 * Команда "Выведи-ка мне поле distance всех элементов коллекции, да в обратном порядке!"
 */
public class PrintFieldDescendingDistance implements Command {
    private static final long serialVersionUID = 6529685098267757690L;

    /**
     * Метод для вывода поля distance всех элементов коллекции в обратном порядке
     */
    @Override
    public void execute(String S, SocketAddress clientAddress) {
        if (!ServerMain.c.Routes.isEmpty()) {
            float[] array = new float[ServerMain.c.Routes.size()]; // создаётся массив размером с коллекцию
            int i = 0;
            for (Route r : ServerMain.c.Routes) {
                if (r.getDistance() != null) array[i] = r.getDistance(); // в массив вносятся значения distance
                i++;
            }

            Arrays.sort(array); // массив сортируется в порядке возрастания
            Float f;
            for (i = ServerMain.c.Routes.size() - 1; i >= 0; i--) {
                f = array[i];
                ServerSender.send(f.toString(), 0, clientAddress); // массив выводится в обратном порядке
            }
        } else ServerSender.send("Коллекция пуста, в отличие от моего рабочего стола.", 0, clientAddress);
    }

    @Override
    public String getInfo() {
        return "print_field_descending_distance: вывести значение поля distance в порядке убывания.";
    }
}
