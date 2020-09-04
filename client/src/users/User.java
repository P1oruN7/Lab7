package users;

import java.io.IOException;
import java.net.URLConnection;

/**
 * Класс пользователя
 */
public class User {
    private String login; //лог ин
    private String password; // пароль
    private URLConnection connection; //??? хз так ли оно, но это соединение
    private String totemAnimal; //  тотемное животное
    private static final long serialVersionUID = 6529685098267757690L; // нужная штука чтобы всё работало

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword (String password){
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public URLConnection getConnection() {
        return connection;
    }

    public void setConnection(URLConnection connection) {
        this.connection = connection;
    }

    public String getTotemAnimal() {
        return totemAnimal;
    }

    public void setTotemAnimal(String totemAnimal) {
        this.totemAnimal = totemAnimal;
    }


    /**
     * Конструктор пользователя
     * @param login имя
     * @param password пароль
     * @param connection коннекшн
     * @param totemAnimal тотемное животное
     */
    public User(String login, String password, URLConnection connection, String totemAnimal){
        this.setLogin(login);
        this.setPassword(password);
        this.setConnection(connection);
        this.setTotemAnimal(totemAnimal);
    }


    /**
     * Авторизация (либо пользователь входит в свой аккаунт, либо создаёт оный)
     * @return был ли пользователь авторизован
     * @throws IOException
     */

    public static boolean authorization () throws IOException {
        System.out.println("\n Напишите login если хотите войти. Напишите reg если хотите зарегистрироваться.");
        switch (utility.ClientMain.reader.readLine().trim().toLowerCase()){
            case "login":
                return login();
            case "reg":
                return makeNewUser();
            default:
                System.out.println("\nНу просили же ввести login или reg. Ладно, давайте ещё раз попробуем.");
                return false;

        }
    }


    /**
     * Создание нового пользователя
     * @return был ли создан новый пользователь
     * @throws IOException
     */
    public static boolean makeNewUser () throws IOException {
        String login;
        String password;
        while (true) {
            System.out.println("\nВведите имя пользователя: ");
            login = utility.ClientMain.reader.readLine().trim();
            if (!checkingLogin(login)) break;
            System.out.println("\nДанное имя пользователя уже занято. Придумайте уникальное имя.");
        }
        while (true) {
            System.out.println("\nВведите пароль длиной от нуля до 20 символов");
            password = utility.ClientMain.reader.readLine().trim();
            if (password.length() == 0 || password == null) break;
            if (checkingNewPassword(password)) break;
            System.out.println("\nПароль не соответствует критериям. Попробуйте сделать другой.");
        }
        if (registerInBase(login, password) ) {
            System.out.println("\nПользователь успешно зарегестрирован.");
            return true;
        } else {
            System.out.println("\nПользователь не был зарегестрирован. Попробуйте ещё раз.");
            return false;
        }
    }

    /**
     * Вход в аккаунт
     * @return вошёл ли пользователь в аккаунт
     * @throws IOException
     */
    public static boolean login () throws IOException {
        String login;
        String password;
        while (true) {
            System.out.println("\nВведите имя пользователя: ");
            login = utility.ClientMain.reader.readLine().trim();
            if (login == "" || login == null) return false;
            if (checkingLogin(login)) break;
            System.out.println("\nПользователя с такими именем не существует.");
        }
        while (true) {
            System.out.println("\nВведите пароль: ");
            password = utility.ClientMain.reader.readLine().trim();
            if ( (password == "" || password == null) && thisUserHasNoPassword(login) ) break;
            if (checkingPassword(login, password)) break;
            System.out.println("\nНеверный пароль.");
            return false;
        }
        System.out.println("\nВход выполнен успешно.");
        return true;
    }

    /**
     * Проверка логина пользователя по базе
     * @param login
     * @return есть ли пользователь с таким именем
     */
    public static boolean checkingLogin(String login){

    }


    /**
     * Проверка создаваемого пароля на критерии (длина, пробелы, вот это всё)
     * @param password строка с паролем
     * @return нормальный ли пароль
     */
    public static boolean checkingNewPassword (String password){
        if (password.length()<20){
            if(!password.contains(" ")){
                return true;
            }
        }
        return false;
    }

    /**
     * Проверка соответвия логина и пароля
     * @param login логин
     * @param password пароль
     * @return соответсвует ли пароль логину
     */
    public static boolean checkingPassword (String login, String password){

    }


    /**
     * Внесение в базу нового пользователя
     * @param login логин
     * @param password пароль
     * @return внесён ли пользователь в базу
     */
    public static boolean registerInBase(String login, String password){

    }

    /**
     * Проверка, является ли пользователь с данным логином обладателем пустого пароля
     * @param login логин
     * @return пустой ли пароль
     */
    public static boolean thisUserHasNoPassword(String login) {

    }
}
