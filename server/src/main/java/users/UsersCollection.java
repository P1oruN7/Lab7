package users;

import java.util.ArrayList;

public class UsersCollection {
    public static ArrayList<User> users;
    public UsersCollection(){
        this.users = new ArrayList<>();
    }


    /**
     * Метод поиска пользователя по login
     *
     * @return элемент коллекции пользователей
     */
    public static User searchByLogin (String login) {
        for (User u : users) {
            if (u.getLogin().equals(login))
                return u;
        }
        return null;
    }

    public static void seeUsers(){
        for (User u : users){
            System.out.println("  " + u.getLogin());
        }
    }

}
