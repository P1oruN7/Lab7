package common.commands;

import common.*;
import readers.*;
import routes.Coordinates;
import routes.Location;
import routes.Route;
import utility.*;

import java.net.SocketAddress;

/**
 * Команда  "ПРАВКИ!"
 */
public class Update implements Command {
    static boolean changedName = false;  // показывает, было ли изменено имя элемента
    static boolean changedCoordinates = false; // показывает, было ли изменено поле coordinates
    static boolean changedFrom = false; //показывает, было ли изменено поле from
    static boolean changedTo = false; // показывает, было ли изменено поле to
    static boolean changedDist = false; // показывает, было ли изменено поле distance
    private static final long serialVersionUID = 6529685098267757690L;

    /**
     * Метод для изменения значений элемента по id
     */
    @Override
    public void execute(String s, SocketAddress clientAddress) {
        s = s.trim();
        Long id = Checker.longChecker(s);
        Route r = ServerMain.c.searchById(id);
        if (r == null) {
            ServerSender.send("похоже элемента с таким айди не существует", 0, clientAddress);
            return;
        }

        int index = ServerMain.c.Routes.indexOf(r);
        ServerSender.send("Состояние элемента сейчас: " + ServerMain.c.Routes.get(index).toString(), 0, clientAddress);
        Route route = new Route();
        route.setCreationDate(ServerMain.c.Routes.get(index).getCreationDate());
        Object [] array = (Object[]) ServerReceiver.receive();
        byte[] string =  (byte[]) array [0];
        String s2 = new String(string); //вот таккая ерундень
        String[] arrayOfStrings = s2.split(" ");
        if (!arrayOfStrings[10].equals(r.getCreatorLogin())) {
            ServerSender.send("Вы не имеете доступа к редактированию этого элемента, поскольку вы не являетесь его создателем. (простите пожалуйста, но не мы придумываем правила)", 0, clientAddress);
            return;
        }
        route.setId(id);
        route.setName(arrayOfStrings[0]);
        route.setCoordinates(new Coordinates(Integer.parseInt(arrayOfStrings[1]), Float.parseFloat(arrayOfStrings[2])));
        if (!arrayOfStrings[3].equals("null"))
            route.setFrom(new Location(Long.parseLong(arrayOfStrings[3]), Double.parseDouble(arrayOfStrings[4]), arrayOfStrings[5]));
        route.setTo(new Location(Long.parseLong(arrayOfStrings[6]), Double.parseDouble(arrayOfStrings[7]), arrayOfStrings[8]));
        if (!arrayOfStrings[9].equals("null")) route.setDistance(Float.parseFloat(arrayOfStrings[9]));
        RemoveById remove_byId = new RemoveById();
        remove_byId.execute(s, clientAddress);
        ServerMain.c.Routes.add(route);
        ServerSender.send("\n" + "Вы достигли успеха в замене элемента по айди!", 0, clientAddress);
    }

    @Override
    public String getInfo() {
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному.";
    }
}
