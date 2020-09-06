package users;

import utility.ServerMain;

public class UserCheck {

    //тут можно проверить существование такого персонажа

    public boolean loginCheck (String login, Long ObjectID){
        return ServerMain.c.searchById(ObjectID).getCreatorLogin().equals(login); //странный метод какой-то это ок ваще не
    }

    public boolean correctPassword (String login, String password){
        boolean isPasswordCorrect = false;
            //тут дешифрация должна быть так что потом давай
        return isPasswordCorrect;
    }


}
