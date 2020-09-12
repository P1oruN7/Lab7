package routes;

import java.io.Serializable;

/**
 * Поле класса Route
 */
public class Coordinates implements Serializable {

    /**
     * Конструктор
     *
     * @param x координата X
     * @param y координата Y
     */
    public Coordinates(int x, float y) {
        this.x = x;
        this.y = y;
    }

    private int x; //Значение поля должно быть больше -836
    private float y; //Максимальное значение поля: 840, Поле не может быть null

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates(" +
                "x = " + x
                + ", y = " + y
                + ')';
    }
}