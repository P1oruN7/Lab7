package routes;

import generators.DateGenerator;
import generators.IDGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDate;

/**
 * Класс-коллекция
 */
public class Collection implements Serializable {

    public static ArrayList<Route> Routes;
    private final LocalDate initializationDate;
    private String path;


    /**
     * Конструктор коллекции
     */
    public Collection() {
        this.Routes = new ArrayList<>();
        this.initializationDate = DateGenerator.generateCurrentDate();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    /**
     * Метод для получения даты
     * @return LocalDate
     */
    public LocalDate getInitializationDate() {
        return initializationDate;
    }


    /**
     * Метод поиска элемента по id
     *
     * @param id id
     * @return элемент коллекции
     */
    public static synchronized Route searchById(long id) {
        for (Route r : Routes) {
            if (r.getId().equals(id))
                return r;
        }
        return null;
    }

    /**
     * Метод для создания уникального id
     *
     * @return уникальный id (long)
     */
    public synchronized long generateUniqueID() {
        long id;
        do {
            id = IDGenerator.generateNewID();
        } while (this.searchById(id) != null);
        return id;
    }
}