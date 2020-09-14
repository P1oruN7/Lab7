package users;


/**
 * Класс пользователя
 */
public class User {
    private String login; //лог ин
    private String password; // пароль
    private String totemAnimal; //  тотемное животное
    private static final long serialVersionUID = 6529685098267757690L; // нужная штука чтобы всё работало


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
     * Другой конструктор пользователя
     *
     * @param login    имя
     * @param password пароль
     */
    public User(String login, String password) {
        this.setLogin(login);
        this.setPassword(password);
        this.totemAnimal = TotemAnimal.randomTotemAnimal();
    }

}
