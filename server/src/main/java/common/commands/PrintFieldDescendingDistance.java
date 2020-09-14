package common.commands;

import common.*;
import routes.Route;
import utility.ServerMain;
import utility.ServerSender;
import java.net.SocketAddress;

/**
 * Команда "Выведи-ка мне поле distance всех элементов коллекции, да в обратном порядке!"
 */
public class PrintFieldDescendingDistance implements Command {
    private static final long serialVersionUID = 6529685098267757690L;

    /**
     * Метод для вывода поля distance всех элементов коллекции в обратном порядке
     */
    @Override
    public synchronized void execute(String S, SocketAddress clientAddress) {
        if (!ServerMain.c.Routes.isEmpty()) {
            String[] q = {"\n"};
            ServerMain.c.Routes.stream()
                    .map(Route::getDistance)
                    .mapToDouble((t) -> {
                        if ((t) == null) (t) = Float.valueOf(0);
                        return (t);
                    })
                    .map((t) -> 0 - t)
                    .sorted()
                    .map((t) -> 0 - t)
                    .forEachOrdered(x -> q[0] += Double.toString(x) + "\n");
            ServerSender.send("Значения distance: " + q[0], 0, clientAddress);
        } else ServerSender.send("Коллекция пуста, в отличие от моего рабочего стола.", 0, clientAddress);
    }

    @Override
    public String getInfo() {
        return "print_field_descending_distance: вывести значение поля distance в порядке убывания.";
    }
}
