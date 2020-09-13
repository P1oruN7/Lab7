package utility;

import common.Command;

import java.net.SocketAddress;

public class CommandExecutor implements Runnable {
    private Command command;
    private SocketAddress clientAddress;
    private String string;
    public CommandExecutor (Command command, String string, SocketAddress clientAddress){
        this.command = command;
        this.string = string;
        this.clientAddress = clientAddress;
    }
    @Override
    public void run(){
        command.execute(string, clientAddress);
    }
}
