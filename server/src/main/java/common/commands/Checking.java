package common.commands;

import common.Command;
import users.User;
import users.UserCheck;
import users.UsersCollection;
import utility.Hash;
import utility.ServerSender;
import java.net.*;

/**
 * Метод проверки (нужен на серваке)
 */
public class Checking implements Command {
    private static final long serialVersionUID = 6529685098267757690L;

    @Override
    public synchronized void execute(String string, SocketAddress clientAddress) {
        Boolean b;
        string = string.trim();
        Character c = string.charAt(0);
        int first = Integer.parseInt(c.toString());
        string = string.substring(1);
        switch (first) {
            case (1):
                b = UserCheck.loginIsExist(string);
                ServerSender.send(b.toString(), 0, clientAddress);
                break;
            case (2):
                String[] array = string.split(" ");
                b = UserCheck.correctPassword(array[0], array[1]);
                ServerSender.send(b.toString(), 0, clientAddress);
                break;
            case (3):
                try {
                    String[] logPass = string.split(" ");
                    User user = new User(logPass[0], Hash.encryptThisString(logPass[1]));
                    UsersCollection.users.add(user);
                    b = true;
                } catch (Exception e) {
                    b = false;
                }
                ServerSender.send(b.toString(), 0, clientAddress);
                break;

            default:
                ServerSender.send("Ошибка", 0, clientAddress);
        }

    }

    @Override
    public String getInfo() {
        return "Команда для обработки входящих штук. Вообще она как бы скрыта и пользователь её не видит. Вот.";
    }
}
