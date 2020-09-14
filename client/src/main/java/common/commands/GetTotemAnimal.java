package common.commands;

import common.Command;
import common.Invoker;

/**
 * Команда получения тотменого животного
 */
public class GetTotemAnimal implements Command {
    private static final long serialVersionUID = 6529685098267757690L;
    public GetTotemAnimal() {
        Invoker.regist("my_totem_animal", this);
    }
    @Override
    public void execute(String login) {

    }

    @Override
    public String getInfo() {
        return "my_totem_animal: узнать своё тотемное животное.";
    }
}
