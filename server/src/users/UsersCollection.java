package users;

import generators.IDGenerator;
import routes.Route;

import java.util.ArrayList;

public class UsersCollection {
    public static ArrayList<User> Users;
    public UsersCollection(){
        this.Users = new ArrayList<>();
    }


    /**
     * Метод поиска пользователя по login
     *
     * @param String login
     * @return элемент коллекции пользователей
     */
    public User п(String login) {
        for (User u : Users) {
            if (u.getLogin().equals(login))
                return u;
        }
        return null;
    }

}
