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
     * Метод поиска пользователя по id
     *
     * @param id id
     * @return элемент коллекции пользователей
     */
    public User searchById(long id) {
        for (User u : Users) {
            if (u.getId().equals(id))
                return u;
        }
        return null;
    }
    /**
     * Метод для создания уникального id пользователя
     *
     * @return уникальный id (long)
     */
    public long generateUniqueID() {
        long id;
        do {
            id = IDGenerator.generateNewID();
        } while (this.searchById(id) != null); // тут надо будет сделать поиск по бд
        return id;
    }
}
