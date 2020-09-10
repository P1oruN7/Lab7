package users;

import common.Command;
import utility.ClientMain;
import utility.ClientReceiver;
import utility.ClientSender;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс пользователя
 */
public class User {
    private String login; //лог ин
    private String password; // пароль
    private String totemAnimal; //  тотемное животное
    private static final long serialVersionUID = 6529685098267757690L; // нужная штука чтобы всё работало
    public static boolean mistake = false;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getTotemAnimal() {
        return totemAnimal;
    }

    public void setTotemAnimal(String totemAnimal) {
        this.totemAnimal = totemAnimal;
    }


    /**
     * Конструктор пользователя
     *
     * @param login       имя
     * @param password    пароль
     * @param totemAnimal тотемное животное
     */
    public User(String login, String password, String totemAnimal) {
        this.setLogin(login);
        this.setPassword(password);
        this.setTotemAnimal(totemAnimal);
    }


    /**
     * Авторизация (либо пользователь входит в свой аккаунт, либо создаёт оный)
     *
     * @return был ли пользователь авторизован
     * @throws IOException
     */

    public static boolean authorization() throws IOException {
        mistake = false;
        System.out.println("\n Напишите login если хотите войти. Напишите reg если хотите зарегистрироваться.");
        switch (utility.ClientMain.reader.readLine().trim().toLowerCase()) {
            case "login":
                return login();
            case "дщпшт":
                System.out.println("\nНе забудьте сменить раскладку.");
                return login();
            case "reg":
                return makeNewUser();
            case "куп":
                System.out.println("\nНе забудьте сменить раскладку.");
                return makeNewUser();
            default:
                System.out.println("\nНу просили же ввести login или reg. Ладно, давайте ещё раз попробуем.");
                return false;

        }
    }


    /**
     * Создание нового пользователя
     *
     * @return был ли создан новый пользователь
     * @throws IOException
     */
    public static boolean makeNewUser() throws IOException {
        String login;
        String password;
        while (true) {
            mistake = false;
            System.out.println("\nВведите имя пользователя: ");
            login = utility.ClientMain.reader.readLine().trim();
            if (!checkingLogin(login)) break;
            if (!mistake) System.out.println("\nДанное имя пользователя уже занято. Придумайте уникальное имя.");
        }
        while (true) {
            mistake = false;
            System.out.println("\nВведите пароль длиной от нуля до 20 символов");
            password = utility.ClientMain.reader.readLine().trim();
            if (password.length() == 0 || password == null) break;
            if (checkingNewPassword(password)) break;
            if (!mistake) System.out.println("\nПароль не соответствует критериям. Попробуйте сделать другой.");
        }
        if (registerInBase(login, password)) {
            ClientMain.setLogin(login);
            ClientMain.setPassword(password);
            //System.out.println("\nПользователь успешно зарегестрирован.");
            return true;
        } else {
            System.out.println("\nПользователь не был зарегестрирован. Попробуйте ещё раз.");
            mistake = true;
            return false;
        }
    }

    /**
     * Вход в аккаунт
     *
     * @return вошёл ли пользователь в аккаунт
     * @throws IOException
     */
    public static boolean login() throws IOException {
        String login;
        String password;
        while (true) {
            mistake = false;
            System.out.println("\nВведите имя пользователя: ");
            login = utility.ClientMain.reader.readLine().trim();
            if (login == "" || login == null) return false;
            if (checkingLogin(login)) break;
            if (!mistake) System.out.println("\nПользователя с такими именем не существует.");
        }
        while (true) {
            mistake = false;
            System.out.println("\nВведите пароль: ");
            password = utility.ClientMain.reader.readLine().trim();
            if ((password == "" || password == null) && thisUserHasNoPassword(login)) break;
            password = utility.Hash.encryptThisString(password);
            if (checkingPassword(login, password)) break;
            if (!mistake) System.out.println("\nНеверный пароль.");
            return false;
        }
        ClientMain.setLogin(login);
        ClientMain.setPassword(password);
        System.out.println("\nВход выполнен успешно.");
        return true;
    }

    /**
     * Проверка логина пользователя по базе
     *
     * @param login
     * @return есть ли пользователь с таким именем
     */
    public static boolean checkingLogin(String login) {
        Boolean b = false; //возвращаемое значение
        Map<Command, String> commandStringMap = new HashMap<>(); //мапа для отправки (одна!)
        common.commands.Checking check = new common.commands.Checking(); // создание экземпляра чек (надо)
        commandStringMap.put(check, "1" + login.trim()); //формирование мапы. 1 - код проверки логина
        ClientSender.sendWithoutLogPass(commandStringMap); //отправка (без логина и пароля)
        try {
            String s2 = ClientReceiver.receiveObject(); //попытка получить строку
            //System.out.println(s2);
            b = Boolean.parseBoolean(s2); //парс в булиан, чтобы вернуть да или нет
            //if (b) {System.out.println("\nПользователя с такими именем не существует.");}
            //else {System.out.println("Такой логин уже занят. Придумайте другой.");}
        } catch (Exception e) {
            System.out.println("Сервер не отвечает или занят,попробуйте ещё раз и убедитесь,что сервер работает.");
            mistake = true;
        }
        return b;
    }


    /**
     * Проверка создаваемого пароля на критерии (длина, пробелы, вот это всё)
     *
     * @param password строка с паролем
     * @return нормальный ли пароль
     */
    public static boolean checkingNewPassword(String password) {
        if (password.length() < 20) {
            if (!password.contains(" ")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Проверка соответвия логина и пароля
     *
     * @param login    логин
     * @param password пароль
     * @return соответсвует ли пароль логину
     */
    public static boolean checkingPassword(String login, String password) {
        Boolean b = false; //возвращаемое значение
        Map<Command, String> commandStringMap = new HashMap<>();  //мапа для отправки (одна!)
        common.commands.Checking check = new common.commands.Checking();// создание экземпляра чек (надо)
        commandStringMap.put(check, "2" + login.trim() + " " + password.trim()); //формирование мапы. 2 - код проверки логина+пароля
        ClientSender.sendWithoutLogPass(commandStringMap); //отправка (без логина и пароля)
        try {
            String s2 = ClientReceiver.receiveObject(); //попытка получить строку
            b = !Boolean.parseBoolean(s2); //парс в булиан, чтобы вернуть да или нет
            if (b) System.out.println("\nПароль верный");
            else System.out.println("Пароль неверный. Попробуйте ещё раз");
        } catch (Exception e) {
            System.out.println("Сервер не отвечает или занят,попробуйте ещё раз и убедитесь,что сервер работает.");
            mistake = true;
        }
        return b;
    }


    /**
     * Внесение в базу нового пользователя
     *
     * @param login    логин
     * @param password пароль
     * @return внесён ли пользователь в базу
     */
    public static boolean registerInBase(String login, String password) {
        Boolean b = false; //возвращаемое значение
        Map<Command, String> commandStringMap = new HashMap<>();//мапа для отправки (одна!)
        common.commands.Checking check = new common.commands.Checking();// создание экземпляра чек (надо)
        commandStringMap.put(check, "3" + login.trim() + " " + password.trim()); //формирование мапы. 3 - код регистрации в бд
        ClientSender.sendWithoutLogPass(commandStringMap); //отправка (без логина и пароля)
        try {
            String s2 = ClientReceiver.receiveObject(); //попытка получить строку
            b = Boolean.parseBoolean(s2); //парс в булиан, чтобы вернуть да или нет
            if (b) System.out.println("\nПользователь успешно зарегестрирован");
            else System.out.println("Во время регистрации произошла ошибка.");
        } catch (Exception e) {
            System.out.println("Сервер не отвечает или занят,попробуйте ещё раз и убедитесь,что сервер работает.");
            mistake = true;
        }
        return b;
    }

    /**
     * Проверка, является ли пользователь с данным логином обладателем пустого пароля
     *
     * @param login логин
     * @return пустой ли пароль
     */
    public static boolean thisUserHasNoPassword(String login) throws IOException {
        Boolean b = false;
        Map<Command, String> commandStringMap = new HashMap<>(); //мапа для отправки (одна!)
        common.commands.Checking check = new common.commands.Checking();// создание экземпляра чек (надо)
        commandStringMap.put(check, "4" + login.trim()); //формирование мапы. 4 - код проверки пустости пароля
        ClientSender.sendWithoutLogPass(commandStringMap); //отправка (без логина и пароля)
        try {
            String s2 = ClientReceiver.receiveObject(); //попытка получить строку
            b = Boolean.parseBoolean(s2); //парс в булиан, чтобы вернуть да или неты
        } catch (Exception e) {
            System.out.println("Сервер не отвечает или занят,попробуйте ещё раз и убедитесь,что сервер работает.");
            mistake = true;
        }
        return b;
    }
}
