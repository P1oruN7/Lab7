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
     * Метод поиска пользователя по логину
     *
     * @param login логин
     * @return элемент коллекции пользователей
     */
    public User searchByLogin(String login) {
        for (User u : Users) {
            if (u.getLogin().equals(login))
                return u;
        }
        return null;
    }

    /**
     * Метод для создания уникального id пользователя
     *
     * @return уникальный id (long)
     */
    //public long generateUniqueID() {
       // long id;
        //do {
          //  id = IDGenerator.generateNewID();
        //} while (this.searchById(id) != null); // тут надо будет сделать поиск по бд
        //return id;
    //}
}
