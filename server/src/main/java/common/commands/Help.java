package common.commands;

import common.Command;
import common.Invoker;
import java.net.SocketAddress;

/**
 * Команда "ПОМОЩЬ"
 */
public class Help implements Command {
    public Help() {
        Invoker.regist("help", this);
    }

    /**
     * Вывод описания всех команд
     */
    @Override
    public void execute(String s, SocketAddress clientAddress) {
    }

    @Override
    public String getInfo() {
        return "help : вывести список всех команд и кратенько рассказать, что они делают";
    }
}
