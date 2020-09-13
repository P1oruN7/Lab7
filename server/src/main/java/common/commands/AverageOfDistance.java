package common.commands;

import common.*;
import utility.ServerMain;
import utility.ServerSender;

import java.net.SocketAddress;


/**
 * Команда "СРЗНАЧ"
 */
public class AverageOfDistance implements Command {
    private static final long serialVersionUID = 6529685098267757690L;

    /**
     * Метод для вывода среднего значения поля distance для всех элементов коллекции
     */
    @Override
    public synchronized void execute(String s, SocketAddress clientAddress) {
        if (ServerMain.c.Routes.size() > 0) {
            Float sum = 0f;
            int countOfNull = 0;
            for (routes.Route r : ServerMain.c.Routes) {
                if (r.getDistance() != null)
                    sum += r.getDistance();
                else
                    countOfNull++;
            }
            if (ServerMain.c.Routes.size() - countOfNull > 0)
                ServerSender.send("\n \nСреднее значение distance: " + sum / (ServerMain.c.Routes.size() - countOfNull) + "\n \n", 0, clientAddress);

        } else
            ServerSender.send("\n \n Коллекция пуста как банка кофе, купленая в начале выполнения этой работы \n \n", 0, clientAddress);
    }

    @Override
    public String getInfo() {
        return "average_of_distance : вывести среднее значение поля distance для всех элементов коллекции.";
    }
}

