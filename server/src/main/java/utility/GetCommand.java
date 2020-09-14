package utility;

import common.Command;
import users.UserCheck;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;


/**
 * Класс для получения и обработки команд
 */
public class GetCommand extends Thread {
    public Object o;
    public SocketAddress clientAddress;

    public GetCommand(Object o, SocketAddress clientAddress) {
        this.o = o;
        this.clientAddress = clientAddress;
    }

    @Override
    public void run() {
        Map<Command, String> commandStringMap;
        try {
            Object[] objects = (Object[]) o;
            boolean correctPass = UserCheck.correctPassword((String) objects[1], (String) objects[2]);
            if (correctPass) commandStringMap = (Map<Command, String>) objects[0];
            else {
                ServerSender.send("Пароль в базе не совпадает с паролем при входе. Попробуйте выйти и пройти авторизацию заново", 0, clientAddress);   //отправить сообщение об ошибке
                return;
            }
        } catch (NullPointerException e) {
            ServerSender.send("Данный пользователь не был найден. Возможно, данные были стёрты.", 0, clientAddress);
            return;
        } catch (Exception e) {
            commandStringMap = (Map<Command, String>) o;
        }

        System.out.println("\nВыполняю команду " + commandStringMap.entrySet().iterator().next().getKey().getClass().getName());
        CommandExecutor commandExecutor = new CommandExecutor(commandStringMap.entrySet().iterator().next().getKey(), commandStringMap.entrySet().iterator().next().getValue(), clientAddress);
        ForkJoinPool fork = (ForkJoinPool) Executors.newWorkStealingPool();
        fork.submit(commandExecutor);
    }
}
