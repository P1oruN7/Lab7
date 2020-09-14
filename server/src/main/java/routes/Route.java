package routes;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Класс, хранимый в Collection
 */
public class Route implements Comparable<Route>, Serializable {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; //Поле может быть null
    private Location to; //Поле не может быть null
    private Float distance; //Поле может быть null, Значение поля должно быть больше 1
    private String creatorLogin; //Поле не может быть null. Значение id создателя объекта

    public Route() {
    }

    public Route(Long id, String name, Coordinates coordinates, LocalDate creationDate, Location from, Location to, Float distance, String creatorLogin) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.creatorLogin = creatorLogin;
    }

    @Override
    public String toString() {
        return "Route(" +
                "id = " + id +
                ", name = " + name +
                ", coordinates = " + coordinates +
                ", creationDate = " + creationDate +
                ", from = " + from +
                ", to = " + to +
                ", distance = " + distance +
                ", creator (login) = " + creatorLogin +
                ')';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Location getFrom() {
        return from;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public Location getTo() {
        return to;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    public String getCreatorLogin() {
        return creatorLogin;
    }

    public void setCreatorLogin(String creatorLogin) {
        this.creatorLogin = creatorLogin;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Route r) {
        if (this.getId().equals(r.getId())) {    //сравнение на одинаковость  id
            return 0;
        } else if (this.getName().toLowerCase().equals(r.getName().toLowerCase())) { //сравнение на одинаковость имён
            if (this.getId() > r.getId()) return 1;  // при одинаковых именах сравнение по ID
            else return -1; // при одинаковых именах сравнение по ID
        } else return this.getName().toLowerCase().compareTo(r.getName().toLowerCase()); // сранение по имени

    }
}