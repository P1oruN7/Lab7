package utility;

import common.commands.*;
import common.*;
import sun.misc.Signal;
import users.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Главненький
 */
public class ClientMain {

    public static boolean work = true; // переменная, отвечающая за выход из программы. Как только она станет false, программа завершается
    public static BufferedReader reader = null;
    public static int port;
    public static InetAddress address;
    private static String login;
    private static String password;


    /**
     * psvm
     *
     * @param args аргументики
     */
    public static void main(String[] args) {

        Signal.handle(new Signal("INT"), sig -> {
            System.out.println("\n\n" +
                    "........|......\n" +
                    ".......o......\n" +
                    "....../()\\.....\n" +
                    ".......||......\n" +
                    "...............\n" +
                    "......|=......\n\n\n");
            System.exit(0);
        });

        Add add = new Add();
        AverageOfDistance average = new AverageOfDistance();
        Clear clear = new Clear();
        ExecuteScript execute_script = new ExecuteScript();
        Exit exit = new Exit();
        GetTotemAnimal my_totem_animal = new GetTotemAnimal();
        Help help = new Help();
        Draw draw = new Draw();
        History history = new History();
        Info info = new Info();
        MaxByDistance max_byDistance = new MaxByDistance();
        PrintFieldDescendingDistance print_fieldDescendingDistance = new PrintFieldDescendingDistance();
        RemoveById remove_byd = new RemoveById();
        Show show = new Show();
        Reorder reorder = new Reorder();
        Sort sort = new Sort();
        Update update = new Update();

        reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Невозможно найти сервер с таким адресом");
            System.exit(0);
        }


        boolean hasPort = false;
        while (!hasPort) {
            System.out.print("Введите порт:  ");
            try {
                port = Integer.parseInt(reader.readLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Формат неправильный");
                continue;
            } catch (IOException e) {
                System.out.println("Ошибка ввода");
                continue;
            }
            hasPort = true;
        }


        try {
            DatagramSocket ds = new DatagramSocket();
            ClientReceiver.sock = ds;
            ClientReceiver.clientport = ds.getLocalPort();
        } catch (SocketException e) {
            System.out.println("Ошибка подключения. Завершение программы.");
            System.exit(0);

        }
        boolean b = false;
        while (!b) {
            try {
                b = User.authorization();
            } catch (IOException e) {
                System.out.println("Произошла ошибка. Завершение программы.");
                System.exit(0);
            }
        }


        while (work) {
            Map<Command, String> commandparamMap = null;
            System.out.print("Введите команду:  ");
            try {
                String s = reader.readLine();
                commandparamMap = Invoker.execute(s);
            } catch (IOException e) {
                System.out.println("Ошибка ввода");
                continue;
            }
            if (commandparamMap != null) {
                ClientSender.send(commandparamMap);
                try {
                    ClientReceiver.receive();
                } catch (Exception e) {
                    System.out.println("Сервер не отвечает или занят,попробуйте ещё раз и убедитесь,что сервер работает.");
                }
            }
        }
    }


    public static String getLogin() {
        return login;
    }

    public static String getPassword() {
        return password;
    }

    public static void setLogin(String login) {
        ClientMain.login = login;
    }

    public static void setPassword(String password) {
        ClientMain.password = password;
    }
}


