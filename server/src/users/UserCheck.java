package users;

import utility.Hash;
import utility.ServerMain;

public class UserCheck {
    public boolean loginIsExist(String login){
        boolean isExist = false;
        isExist = ServerMain.users.searchByLogin(login) != null;
        return isExist;
    }



    public boolean isThisRouteYours (String login, Long ObjectID){
        return ServerMain.c.searchById(ObjectID).getCreatorLogin().equals(login); //странный метод какой-то это ок ваще не
    }

    public boolean correctPassword (String login, String password){
        boolean isPasswordCorrect = false;
        User u = ServerMain.users.searchByLogin(login);
        if (u != null) isPasswordCorrect = u.getPassword().equals(Hash.encryptThisString(password));
        return isPasswordCorrect;
    }


}