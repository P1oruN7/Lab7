package utility;

import common.commands.*;
import routes.Collection;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import readers.ConsoleSourceReader;
import sun.misc.Signal;
import users.UsersCollection;
import static sql.Connector.saving;

/**
 * Главненький
 */
public class ServerMain {
    public static Collection c = null;
    public static Integer port;
    public static SocketAddress clientAddress;
    public static final String URL = "jdbc:postgresql://pg:5432/studs";

    /**
     * psvm
     *
     * @param args аргументики
     */
    public static void main(String[] args) {

        Signal.handle(new Signal("INT"), sig -> {
            try {
                saving();
                System.out.println("\nЗавершение программы c сохранением");
            } catch (Exception e) {
                System.out.println("\nЛучше бы exit вызвали");
            }
            System.out.println("\n\n" +
                    "........|......\n" +
                    ".......o......\n" +
                    "....../()\\.....\n" +
                    ".......||......\n" +
                    "...............\n" +
                    "......|=......" +
                    "\n\n");
            System.exit(0);
        });

        UsersCollection.users = new ArrayList<>(); ///
        Add add = new Add();
        AverageOfDistance average = new AverageOfDistance();
        Clear clear = new Clear();
        Exit exit = new Exit();
        Info info = new Info();
        MaxByDistance max_byDistance = new MaxByDistance();
        GetTotemAnimal getTotemAnimal = new GetTotemAnimal();
        PrintFieldDescendingDistance print_fieldDescendingDistance = new PrintFieldDescendingDistance();
        RemoveById remove_byId = new RemoveById();
        Show show = new Show();
        Reorder reorder = new Reorder();
        common.commands.Save save = new common.commands.Save();
        Sort sort = new Sort();
        Update update = new Update();
        ConsoleSourceReader bufferReader = new ConsoleSourceReader();


        c = new Collection();
        sql.Connector.loading();
        if (UsersCollection.searchByLogin("admin") == null) {
            users.User admin = new users.User("admin", "58b41e4d2aa978f18bf332d4218092bedbec76199eddff465d84ef79", "admin");
            UsersCollection.users.add(admin);
        }

        boolean serverCreated = false;
        while (!serverCreated) {
            System.out.print("Введите порт:  ");

            try {
                port = Integer.parseInt(bufferReader.getLine());
            } catch (NumberFormatException e) {
                System.out.println("Формат неправильный");
                continue;
            }
            serverCreated = CreateServer.create();
        }
        System.out.println("Советик: введите help, чтобы увидеть доступные команды.");
        InputString inputString = new InputString();
        inputString.start();
        while (true) {
            GetCommand();
        }
    }

    public static void GetCommand() {
        SocketAddress clientAddress = null;
        try {
            System.out.println("\nЖду команду от клиента.");
            Object[] received = (Object[]) ServerReceiver.receive();
            clientAddress = (SocketAddress) received[1];
            byte[] bytes = (byte[]) received[0];
            Object o = ByteToObject.Cast(bytes);
            ExecutorService cache = Executors.newCachedThreadPool();
            GetCommand getCommand = new GetCommand(o, clientAddress);
            cache.submit(getCommand);

        } catch (ClassCastException e) {
            ServerSender.send("\nСообщение от Сервера:\"Возникли небольшие технические шоколадки с вашим подключением,но сейчас всё по кайфу,ожидаю команд.\"\n", 0, clientAddress);
        }
    }
}