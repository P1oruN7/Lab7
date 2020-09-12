package users;

import utility.Hash;
import utility.ServerMain;

public class UserCheck {
    public static boolean loginIsExist(String login){
        boolean isExist = false;
         isExist = UsersCollection.searchByLogin(login) != null;
        return isExist;
    }
    public static boolean isThisRouteYours (String login, Long ObjectID){
        return ServerMain.c.searchById(ObjectID).getCreatorLogin().equals(login); //странный метод какой-то это ок ваще не
    }

    public static boolean correctPassword (String login, String password){
        String hashedPass = Hash.encryptThisString(password);
        boolean isPasswordCorrect = false;
        User u;
        u = UsersCollection.searchByLogin(login);
       // System.out.println("u.getPassword()= " + u.getPassword() + "    "+"hashedPass=" + hashedPass + "    password=" + password);
        isPasswordCorrect = u.getPassword().equals( hashedPass);
        return isPasswordCorrect;
    }
}