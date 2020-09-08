package common.commands;

import common.Command;
import users.User;
import users.UserCheck;
import users.UsersCollection;
import utility.Hash;

import java.net.*;

/**
 * Метод проверки (нужен на серваке)
 */
public class Checking implements Command {
    @Override
    public void execute(String string) {
        boolean b;
        string = string.trim();
        char first = string.charAt(0);
        string = string.substring(1);
        switch (first){
            case (1):
                 b = UsersCollection.searchByLogin(string) != null;
                    //каким-то магическим образом отправляет b тому клиенту, от которого пришла команда
                break;
            case (2):
                String [] array = string.split(" ");
                b = UserCheck.correctPassword(array[0], array[1]);
                //каким-то магическим образом отправляет b тому клиенту, от которого пришла команда
                break;
            case (3):
                String [] logPass = string.split(" ");
                URLConnection connection  ;//я хз что тут писать честно(
                User user = new User(logPass[0], Hash.encryptThisString(logPass[1]), connection, users.TotemAnimal.randomTotemAnimal());
                UsersCollection.users.add(user);
                b = true;
                //каким-то магическим образом отправляет b тому клиенту, от которого пришла команда
                break;
            case (4):
                if (string.trim().equals("") | string == null) b = true;
                //каким-то магическим образом отправляет b тому клиенту, от которого пришла команда
                break;
            default:
                //Отправить сообщение об ошибке

        }

    }

    @Override
    public String getInfo() {
        return "Команда для обработки входящих штук. Вообще она как бы скрыта и пользователь её не видит. Вот.";
    }
}