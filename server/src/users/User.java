package users;

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
    public User (String login, String password, URLConnection connection, String totemAnimal){
        this.setLogin(login);
        this.setPassword(password);
        this.setConnection(connection);
        this.setTotemAnimal(totemAnimal);
    }

}
