package routes;

import java.io.Serializable;

/**
 * Поле класса Route
 */
public class Location implements Serializable {
    private Long x; //Поле не может быть null
    private Double y; //Поле не может быть null
    private String name; //Поле не может быть null

    /**
     * Конструктор
     *
     * @param x    Координата x
     * @param y    Координата y
     * @param name Имя
     */
    public Location(Long x, Double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Location(" +
                "x = " + x +
                ", y = " + y +
                ", name = " + name +
                ')';
    }
}