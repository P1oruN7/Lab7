package users;

import utility.Hash;
import utility.ServerMain;

/**
 * Класс для проверки штук у пользователей
 */
public class UserCheck {
    public synchronized static boolean loginIsExist(String login) {
        boolean isExist = false;
        isExist = UsersCollection.searchByLogin(login) != null;
        return isExist;
    }

    public synchronized static boolean isThisRouteYours(String login, Long ObjectID) {
        return ServerMain.c.searchById(ObjectID).getCreatorLogin().equals(login); //странный метод какой-то это ок ваще не
    }

    public synchronized static boolean correctPassword(String login, String password) {
        boolean isPasswordCorrect = false;
        User u;
        u = UsersCollection.searchByLogin(login);
        isPasswordCorrect = u.getPassword().equals(password);
        return isPasswordCorrect;
    }
}
