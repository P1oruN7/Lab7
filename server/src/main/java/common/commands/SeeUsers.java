package common.commands;

import users.UsersCollection;

/**
 * Команда для просмотра списка пользователей
 */
public class SeeUsers {
    public synchronized static void execute(String S) {
        UsersCollection.seeUsers();
    }

    public String getInfo() {
        return "see_users : посмотреть список всех юзеров ";
    }


}
