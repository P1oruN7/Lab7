package users;


import java.util.ArrayList;

/**
 * Класс для коллекции пользователей
 */
public class UsersCollection {
    public static ArrayList<User> users;

    public UsersCollection() {
        this.users = new ArrayList<>();
    }


    /**
     * Метод поиска пользователя по login
     *
     * @return элемент коллекции пользователей
     */
    public synchronized static User searchByLogin(String login) {
        for (User u : users) {
            if (u.getLogin().equals(login))
                return u;
        }
        return null;
    }

    public synchronized static void seeUsers() {
        for (User u : users) {
            System.out.println("  " + u.getLogin());
        }
    }

}
