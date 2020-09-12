package common.commands;

import common.Command;
import users.TotemAnimal;
import users.UsersCollection;

import java.net.SocketAddress;

public class GetTotemAnimal implements Command {
    private static final long serialVersionUID = 6529685098267757690L;

    @Override
    public void execute(String login, SocketAddress socketAddress) {
    String totemAnimal = UsersCollection.searchByLogin(login).getTotemAnimal();
        utility.ServerSender.send("\n                  Your totem animal is " + totemAnimal + ".  ;)\n\n"
        +TotemAnimal.getImage(totemAnimal) + "\n\n"
        +"             "+ TotemAnimal.getInfo(totemAnimal), 0, socketAddress);
    }

    @Override
    public String getInfo() {
        return "my_totem_animal: узнать своё тотемное животное.";
    }
}
